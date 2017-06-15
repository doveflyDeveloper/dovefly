package com.deertt.module.mm.check.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.check.dao.IStockCheckBillDao;
import com.deertt.module.mm.check.service.IStockCheckBillService;
import com.deertt.module.mm.check.service.IStockCheckDetailService;
import com.deertt.module.mm.check.util.IStockCheckBillConstants;
import com.deertt.module.mm.check.vo.StockCheckBillVo;
import com.deertt.module.mm.check.vo.StockCheckDetailVo;
import com.deertt.module.mm.goods.service.IGoodsWService;
import com.deertt.module.mm.statistics.service.IStatisticsBillService;
import com.deertt.module.mm.statistics.vo.StatisticsBillVo;
import com.deertt.module.mm.statistics.vo.StatisticsDetailVo;
import com.deertt.utils.helper.date.DvDateHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class StockCheckBillService extends DvBaseService<IStockCheckBillDao, StockCheckBillVo, Integer> implements IStockCheckBillService, IStockCheckBillConstants {
	
	@Autowired
	protected IStockCheckDetailService detailService;
	
	@Autowired
	protected IGoodsWService goodsWService;
	
	@Autowired
	private IStatisticsBillService statisticsBillService;
	
	@Transactional
	public int insertFull(StockCheckBillVo vo) {
		BigDecimal stock_amount = BigDecimal.ZERO;
		BigDecimal check_amount = BigDecimal.ZERO;
		BigDecimal dif_amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (StockCheckDetailVo d : vo.getDetails()) {
				stock_amount = stock_amount.add(d.getStock_amount());
				check_amount = check_amount.add(d.getCheck_amount());
				dif_amount = dif_amount.add(d.getDif_amount());
			}
			vo.setStock_amount(stock_amount);
			vo.setCheck_amount(check_amount);
			vo.setDif_amount(dif_amount);
		}
		int sum = super.insert(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}

	@Transactional
	public int updateFull(StockCheckBillVo vo) {
		BigDecimal stock_amount = BigDecimal.ZERO;
		BigDecimal check_amount = BigDecimal.ZERO;
		BigDecimal dif_amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (StockCheckDetailVo d : vo.getDetails()) {
				stock_amount = stock_amount.add(d.getStock_amount());
				check_amount = check_amount.add(d.getCheck_amount());
				dif_amount = dif_amount.add(d.getDif_amount());
			}
			vo.setStock_amount(stock_amount);
			vo.setCheck_amount(check_amount);
			vo.setDif_amount(dif_amount);
		}
		int sum = super.update(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}

	@Override
	public StockCheckBillVo findFull(Integer id) {
		StockCheckBillVo vo = super.find(id);
		vo.setDetails(detailService.queryByCondition("bill_id = " + id, null));
		return vo;
	}
	
	@Transactional
	public void generateStatisticsBill(Integer stock_check_bill_id) {
		StockCheckBillVo checkBillVo = this.findFull(stock_check_bill_id);
		if (StockCheckBillVo.STATUS_CONFIRM.equals(checkBillVo.getStatus())) {
			throw new BusinessException("库存盘点单不可重复确认！");
		}
		checkBillVo.setStatus(StockCheckBillVo.STATUS_CONFIRM);
		this.update(checkBillVo);
		
		Integer warehouse_id = checkBillVo.getWarehouse_id();
		Integer check_bill_id = checkBillVo.getId();
		Integer statistics_bill_id = null;
//		String from_date = DvDateHelper.getMonthFirstDate(new Date(System.currentTimeMillis() - 1000L*60*60*24*25));//上月初，（往前推25天）
//		String to_date = DvDateHelper.getMonthLastDate(new Date(System.currentTimeMillis() - 1000L*60*60*24*25));//上月末
		
		StatisticsBillVo lastBill = statisticsBillService.queryByCondition("warehouse_id = " + warehouse_id + " and status = '1'", "id desc", 0, 1).get(0);
		String from_date = DvDateHelper.formatDate(lastBill.getBill_date(), "yyyy-MM-dd");//上次统计日期
		String to_date = DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy-MM-dd");//上月末
		

		StatisticsBillVo sBill = new StatisticsBillVo();
		sBill.setBill_code(statisticsBillService.generateBillCode(StatisticsBillVo.BILL_CODE_PREFIX));
		sBill.setBill_date(DvDateHelper.getSysDate());
		sBill.setBill_time(DvDateHelper.getSysTimestamp());
		sBill.setCity_id(checkBillVo.getCity_id());
		sBill.setCity_name(checkBillVo.getCity_name());
		sBill.setWarehouse_id(checkBillVo.getWarehouse_id());
		sBill.setWarehouse_name(checkBillVo.getWarehouse_name());
		sBill.setStatus("0");
		
		List<StatisticsDetailVo> details = new ArrayList<StatisticsDetailVo>(checkBillVo.getDetails().size());
		for (StockCheckDetailVo c : checkBillVo.getDetails()) {
			StatisticsDetailVo d = new StatisticsDetailVo();
			d.setGoods_id(c.getGoods_id());
			d.setGoods_name(c.getGoods_name());
			d.setGoods_image(c.getGoods_image());
			
			d.setOrigin_quantity(BigDecimal.ZERO);
			d.setOrigin_amount(BigDecimal.ZERO);
			
			d.setPurchase_quantity(BigDecimal.ZERO);
			d.setPurchase_amount(BigDecimal.ZERO);
			
			d.setPurchase_back_quantity(BigDecimal.ZERO);
			d.setPurchase_back_amount(BigDecimal.ZERO);
			
			d.setOrder_quantity(BigDecimal.ZERO);
			d.setOrder_amount(BigDecimal.ZERO);
	
			d.setOrder_back_quantity(BigDecimal.ZERO);
			d.setOrder_back_amount(BigDecimal.ZERO);
	
			d.setOut_quantity(BigDecimal.ZERO);
			d.setOut_amount(BigDecimal.ZERO);
	
			d.setStock_quantity(BigDecimal.ZERO);
			d.setStock_amount(BigDecimal.ZERO);
	
			d.setCheck_quantity(BigDecimal.ZERO);
			d.setCheck_amount(BigDecimal.ZERO);
	
			d.setDif_quantity(BigDecimal.ZERO);
			d.setDif_amount(BigDecimal.ZERO);
			
			d.setFinal_quantity(BigDecimal.ZERO);
			d.setFinal_amount(BigDecimal.ZERO);
	
			details.add(d);
		}
		
		sBill.setDetails(details);
		statisticsBillService.insertFull(sBill);
		
		statistics_bill_id = sBill.getId();
		
		this.update("call confirmStockCheckBill(" + warehouse_id + ", " + ", " + check_bill_id + ", " + statistics_bill_id + ", '" + from_date + "', '" + to_date + "')");
		
	}
}
