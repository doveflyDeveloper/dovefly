package com.deertt.module.purchase.back.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.goods.service.IGoodsWService;
import com.deertt.module.purchase.back.dao.IPurchaseBackBillDao;
import com.deertt.module.purchase.back.service.IPurchaseBackBillService;
import com.deertt.module.purchase.back.service.IPurchaseBackDetailService;
import com.deertt.module.purchase.back.util.IPurchaseBackBillConstants;
import com.deertt.module.purchase.back.vo.PurchaseBackBillVo;
import com.deertt.module.purchase.back.vo.PurchaseBackDetailVo;
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
public class PurchaseBackBillService extends DvBaseService<IPurchaseBackBillDao, PurchaseBackBillVo, Integer> implements IPurchaseBackBillService, IPurchaseBackBillConstants {
	
	@Autowired
	private IPurchaseBackDetailService detailService;
	
	@Autowired
	private IGoodsWService goodsWService;
	
    @Transactional
    public int saveFull(PurchaseBackBillVo vo) {
        if (vo.isNew()) {
            return this.insertFull(vo);
        } else {
            return this.updateFull(vo);
        }
    }
	
	@Transactional
	public int insertFull(PurchaseBackBillVo vo) {
		BigDecimal amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (PurchaseBackDetailVo d : vo.getDetails()) {
				amount = amount.add(d.getSub_total());
			}
			vo.setAmount(amount);
		}
		int sum = super.insert(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}

	@Transactional
	public PurchaseBackBillVo findFull(Integer id) {
		PurchaseBackBillVo vo = super.find(id);
		vo.setDetails(detailService.queryByCondition("bill_id = " + id, null));
		return vo;
	}

	@Transactional
	public int updateFull(PurchaseBackBillVo vo) {
		BigDecimal amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (PurchaseBackDetailVo d : vo.getDetails()) {
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
	public int confirm(Integer id) {
		PurchaseBackBillVo vo = this.findFull(id);
		if (!PurchaseBackBillVo.STATUS_UNCONFIRM.equals(vo.getStatus())) {
			throw new BusinessException("不可重复确认操作！");
		}
		
		if (vo.getDetails() != null) {
			for (PurchaseBackDetailVo detail : vo.getDetails()) {
				goodsWService.reduceStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		
		vo.setBill_date(new java.sql.Date(System.currentTimeMillis()));
		vo.setBill_time(DvDateHelper.getSysTimestamp());
		vo.setStatus(PurchaseBackBillVo.STATUS_CONFIRM);
		return this.update(vo);
	}
	
	@Transactional
	public int turnback(Integer id) {
		PurchaseBackBillVo vo = this.findFull(id);
		if (!PurchaseBackBillVo.STATUS_CONFIRM.equals(vo.getStatus())) {
			throw new BusinessException("不可重复撤回操作！");
		}
		
		if (vo.getDetails() != null) {
			for (PurchaseBackDetailVo detail : vo.getDetails()) {
				goodsWService.addStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		vo.setStatus(PurchaseBackBillVo.STATUS_UNCONFIRM);
		return this.update(vo);
	}
	
	public PurchaseBackBillVo findByCode(String bill_code) {
		return getDao().findByCondition("bill_code = '" + bill_code + "'");
	}
	
	public List<Map<String, Object>> reportGroupByBillDate(String from_date, String to_date) {
		return (List<Map<String, Object>>) getDao().queryForMaps(SQL_REPORT_GROUP_BY_BILL_DATE, new Object[] {from_date, to_date});
	}
	
	public List<Map<String, Object>> reportGroupByWarehouse(Integer warehouse_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_WAREHOUSE;
		if (warehouse_id == null) {
			sql = sql.replace("AND warehouse_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (warehouse_id != null) params.add(warehouse_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> reportGroupByGoods(Integer warehouse_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUPY_BY_GOODS;
		if (warehouse_id == null) {
			sql = sql.replace("AND p.warehouse_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (warehouse_id != null) params.add(warehouse_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);

		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> queryListDetails(String queryCondition) {
		String sql = SQL_LIST_DETAILS;
		if (StringUtils.isNotEmpty(queryCondition)) {
			sql = sql + " AND p." + queryCondition.replaceAll("and ", "AND p.");
		}
		
		sql += " ORDER BY p.id desc";
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql);
	}

}
