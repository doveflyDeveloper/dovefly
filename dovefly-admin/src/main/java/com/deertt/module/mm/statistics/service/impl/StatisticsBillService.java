package com.deertt.module.mm.statistics.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.goods.service.IGoodsWService;
import com.deertt.module.mm.statistics.dao.IStatisticsBillDao;
import com.deertt.module.mm.statistics.service.IStatisticsBillService;
import com.deertt.module.mm.statistics.service.IStatisticsDetailService;
import com.deertt.module.mm.statistics.util.IStatisticsBillConstants;
import com.deertt.module.mm.statistics.vo.StatisticsBillVo;
import com.deertt.module.mm.statistics.vo.StatisticsDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class StatisticsBillService extends DvBaseService<IStatisticsBillDao, StatisticsBillVo, Integer> implements IStatisticsBillService, IStatisticsBillConstants {
	
	@Autowired
	protected IStatisticsDetailService detailService;
	
	@Autowired
	protected IGoodsWService goodsWService;
	
	@Transactional
	public int insertFull(StatisticsBillVo vo) {
		BigDecimal origin_amount = BigDecimal.ZERO;
		BigDecimal purchase_amount = BigDecimal.ZERO;
		BigDecimal purchase_back_amount = BigDecimal.ZERO;
		BigDecimal order_amount = BigDecimal.ZERO;
		BigDecimal order_back_amount = BigDecimal.ZERO;
		BigDecimal out_amount = BigDecimal.ZERO;
		BigDecimal stock_amount = BigDecimal.ZERO;
		BigDecimal check_amount = BigDecimal.ZERO;
		BigDecimal dif_amount = BigDecimal.ZERO;
		BigDecimal final_amount = BigDecimal.ZERO;
		
		if (vo.getDetails() != null) {
			for (StatisticsDetailVo d : vo.getDetails()) {
				origin_amount = origin_amount.add(d.getOrigin_amount());
				purchase_amount = purchase_amount.add(d.getPurchase_amount());
				purchase_back_amount = purchase_back_amount.add(d.getPurchase_back_amount());
				order_amount = order_amount.add(d.getOrder_amount());
				order_back_amount = order_back_amount.add(d.getOrder_back_amount());
				out_amount = out_amount.add(d.getOut_amount());
				stock_amount = stock_amount.add(d.getStock_amount());
				check_amount = check_amount.add(d.getCheck_amount());
				dif_amount = dif_amount.add(d.getDif_amount());
				final_amount = final_amount.add(d.getFinal_amount());
			}
		}
		vo.setOrigin_amount(origin_amount);
		vo.setPurchase_amount(purchase_amount);
		vo.setPurchase_back_amount(purchase_back_amount);
		vo.setOrder_amount(order_amount);
		vo.setOrder_back_amount(order_back_amount);
		vo.setOut_amount(out_amount);
		vo.setStock_amount(stock_amount);
		vo.setCheck_amount(check_amount);
		vo.setDif_amount(dif_amount);
		vo.setFinal_amount(final_amount);
		int sum = super.insert(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}
	
	@Transactional
	public int updateFull(StatisticsBillVo vo) {
		BigDecimal origin_amount = BigDecimal.ZERO;
		BigDecimal purchase_amount = BigDecimal.ZERO;
		BigDecimal purchase_back_amount = BigDecimal.ZERO;
		BigDecimal order_amount = BigDecimal.ZERO;
		BigDecimal order_back_amount = BigDecimal.ZERO;
		BigDecimal out_amount = BigDecimal.ZERO;
		BigDecimal stock_amount = BigDecimal.ZERO;
		BigDecimal check_amount = BigDecimal.ZERO;
		BigDecimal dif_amount = BigDecimal.ZERO;
		BigDecimal final_amount = BigDecimal.ZERO;
		
		if (vo.getDetails() != null) {
			for (StatisticsDetailVo d : vo.getDetails()) {
				origin_amount = origin_amount.add(d.getOrigin_amount());
				purchase_amount = purchase_amount.add(d.getPurchase_amount());
				purchase_back_amount = purchase_back_amount.add(d.getPurchase_back_amount());
				order_amount = order_amount.add(d.getOrder_amount());
				order_back_amount = order_back_amount.add(d.getOrder_back_amount());
				out_amount = out_amount.add(d.getOut_amount());
				stock_amount = stock_amount.add(d.getStock_amount());
				check_amount = check_amount.add(d.getCheck_amount());
				dif_amount = dif_amount.add(d.getDif_amount());
				final_amount = final_amount.add(d.getFinal_amount());
			}
		}
		vo.setOrigin_amount(origin_amount);
		vo.setPurchase_amount(purchase_amount);
		vo.setPurchase_back_amount(purchase_back_amount);
		vo.setOrder_amount(order_amount);
		vo.setOrder_back_amount(order_back_amount);
		vo.setOut_amount(out_amount);
		vo.setStock_amount(stock_amount);
		vo.setCheck_amount(check_amount);
		vo.setDif_amount(dif_amount);
		vo.setFinal_amount(final_amount);
		int sum = super.update(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}
	
	public StatisticsBillVo findFull(Integer id) {
		StatisticsBillVo vo = super.find(id);
		vo.setDetails(detailService.queryByCondition("bill_id = " + id, null));
		return vo;
	}
	
}
