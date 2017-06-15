package com.deertt.module.purchase.bill.service.impl;

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
import com.deertt.module.purchase.bill.dao.IPurchaseBillDao;
import com.deertt.module.purchase.bill.service.IPurchaseBillService;
import com.deertt.module.purchase.bill.service.IPurchaseDetailService;
import com.deertt.module.purchase.bill.util.IPurchaseBillConstants;
import com.deertt.module.purchase.bill.vo.PurchaseBillVo;
import com.deertt.module.purchase.bill.vo.PurchaseDetailVo;
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
public class PurchaseBillService extends DvBaseService<IPurchaseBillDao, PurchaseBillVo, Integer> implements IPurchaseBillService, IPurchaseBillConstants {
	
	@Autowired
	private IPurchaseDetailService detailService;
	
	@Autowired
	private IGoodsWService goodsWService;
	
    @Transactional
    public int saveFull(PurchaseBillVo vo) {
        if (vo.isNew()) {
            return this.insertFull(vo);
        } else {
            return this.updateFull(vo);
        }
    }
	
	@Transactional
	public int insertFull(PurchaseBillVo vo) {
		BigDecimal amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (PurchaseDetailVo d : vo.getDetails()) {
				amount = amount.add(d.getSub_total());
			}
			vo.setAmount(amount);
		}
		int sum = super.insert(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}

	@Transactional
	public PurchaseBillVo findFull(Integer id) {
		PurchaseBillVo vo = super.find(id);
		vo.setDetails(detailService.queryByCondition("bill_id = " + id, null));
		return vo;
	}

	@Transactional
	public int updateFull(PurchaseBillVo vo) {
		BigDecimal amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (PurchaseDetailVo d : vo.getDetails()) {
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
	public int checkin(Integer id) {
		PurchaseBillVo vo = this.findFull(id);
		if (!PurchaseBillVo.STATUS_UNCHECKIN.equals(vo.getStatus())) {
			throw new BusinessException("仅支持未入库采购订单的入库操作！");
		}
		
		if (vo.getDetails() != null) {
			for (PurchaseDetailVo detail : vo.getDetails()) {
				goodsWService.addStockSum(detail.getGoods_id(), detail.getQuantity(), detail.getUnit_price(), null, vo.getBill_code());
			}
		}
		vo.setBill_date(new java.sql.Date(System.currentTimeMillis()));
		vo.setBill_time(DvDateHelper.getSysTimestamp());
		vo.setStatus(PurchaseBillVo.STATUS_CHECKIN);
		return this.update(vo);
	}
	
	@Transactional
	public int turnback(Integer id) {
		PurchaseBillVo vo = this.findFull(id);
		if (!PurchaseBillVo.STATUS_CHECKIN.equals(vo.getStatus())) {
			throw new BusinessException("仅支持已入库采购订单的撤回操作！");
		}

		if (vo.getDetails() != null) {
			for (PurchaseDetailVo detail : vo.getDetails()) {
				goodsWService.reduceStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		vo.setStatus(PurchaseBillVo.STATUS_UNCHECKIN);
		return this.update(vo);
	}
	
	public PurchaseBillVo findByCode(String bill_code) {
		return getDao().findByCondition("bill_code = '" + bill_code + "'");
	}
	
	public List<Map<String, Object>> reportGroupByBillDate(String from_date, String to_date) {
		return (List<Map<String, Object>>) getDao().queryForMaps(SQL_REPORT_GROUP_BY_BILL_DATE, new Object[] {from_date, to_date});
	}
	
	public List<Map<String, Object>> reportGroupByCity(Integer city_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_CITY;
		if (city_id == null) {
			sql = sql.replace("AND city_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
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
