package com.deertt.module.purchase.out.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.goods.service.IGoodsWService;
import com.deertt.module.purchase.out.dao.IPurchaseOutBillDao;
import com.deertt.module.purchase.out.service.IPurchaseOutBillService;
import com.deertt.module.purchase.out.service.IPurchaseOutDetailService;
import com.deertt.module.purchase.out.util.IPurchaseOutBillConstants;
import com.deertt.module.purchase.out.vo.PurchaseOutBillVo;
import com.deertt.module.purchase.out.vo.PurchaseOutDetailVo;
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
public class PurchaseOutBillService extends DvBaseService<IPurchaseOutBillDao, PurchaseOutBillVo, Integer> implements IPurchaseOutBillService, IPurchaseOutBillConstants {

	@Autowired
	private IPurchaseOutDetailService detailService;
	
	@Autowired
	private IGoodsWService goodsWService;
	
    @Transactional
    public int saveFull(PurchaseOutBillVo vo) {
        if (vo.isNew()) {
            return this.insertFull(vo);
        } else {
            return this.updateFull(vo);
        }
    }
	
	@Transactional
	public int insertFull(PurchaseOutBillVo vo) {
		BigDecimal amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (PurchaseOutDetailVo d : vo.getDetails()) {
				amount = amount.add(d.getSub_total());
			}
			vo.setAmount(amount);
		}
		int sum = super.insert(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}

	@Transactional
	public PurchaseOutBillVo findFull(Integer id) {
		PurchaseOutBillVo vo = super.find(id);
		vo.setDetails(detailService.queryByCondition("bill_id = " + id, null));
		return vo;
	}

	@Transactional
	public int updateFull(PurchaseOutBillVo vo) {
		BigDecimal amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (PurchaseOutDetailVo d : vo.getDetails()) {
				amount = amount.add(d.getSub_total());
			}
			vo.setAmount(amount);
		}
		int sum = super.update(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}
	
	@Transactional
	public int deleteFull(Integer id) {
		detailService.deleteByBill(id);
		return super.delete(id);
	}
	
    @Transactional
    public int deleteFull(Integer[] ids) {
        int sum = 0;
        for (Integer id : ids) {
            detailService.deleteByBill(id);
            sum += super.delete(id);
        }
        return sum;
    }
	
	@Transactional
	public int checkout(Integer id) {
		PurchaseOutBillVo vo = this.findFull(id);
		if (!PurchaseOutBillVo.STATUS_UNCHECKOUT.equals(vo.getStatus())) {
			throw new BusinessException("仅支持已未确认出库单的出库确认操作！");
		}
		
		if (vo.getDetails() != null) {
			for (PurchaseOutDetailVo detail : vo.getDetails()) {
				goodsWService.reduceStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		vo.setBill_date(new java.sql.Date(System.currentTimeMillis()));
		vo.setBill_time(DvDateHelper.getSysTimestamp());
		vo.setStatus(PurchaseOutBillVo.STATUS_CHECKOUT);
		return this.update(vo);
	}
	
	@Transactional
	public int turnback(Integer id) {
		PurchaseOutBillVo vo = this.findFull(id);
		if (!PurchaseOutBillVo.STATUS_CHECKOUT.equals(vo.getStatus())) {
			throw new BusinessException("不可重复撤回操作！");
		}
		
		if (vo.getDetails() != null) {
			for (PurchaseOutDetailVo detail : vo.getDetails()) {
				goodsWService.addStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		vo.setStatus(PurchaseOutBillVo.STATUS_UNCHECKOUT);
		return this.update(vo);
	}
	
	public PurchaseOutBillVo findByCode(String bill_code) {
		return getDao().findByCondition("bill_code = '" + bill_code + "'");
	}
	
	public List<Map<String, Object>> queryListDetails(String queryCondition) {
		String sql = SQL_LIST_DETAILS;
		if (StringUtils.isNotEmpty(queryCondition)) {
			sql = sql + " AND o." + queryCondition.replaceAll("and ", "AND o.");
		}
		
		sql += " ORDER BY o.id desc";
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql);
	}
}
