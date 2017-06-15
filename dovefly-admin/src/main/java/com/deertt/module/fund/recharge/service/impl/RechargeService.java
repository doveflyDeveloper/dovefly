package com.deertt.module.fund.recharge.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.recharge.dao.IRechargeDao;
import com.deertt.module.fund.recharge.service.IRechargeService;
import com.deertt.module.fund.recharge.util.IRechargeConstants;
import com.deertt.module.fund.recharge.vo.RechargeVo;
import com.deertt.module.sys.market.service.IMarketService;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.warehouse.service.IWarehouseService;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class RechargeService extends DvBaseService<IRechargeDao, RechargeVo, Integer> implements IRechargeService, IRechargeConstants {
	
	@Autowired
	private IWarehouseService warehouseService;
	
	@Autowired
	private IShopService shopService;
	
	@Autowired
	private IMarketService marketService;
	
	public RechargeVo findByCode(String bill_code) {
		return getDao().findByCondition("bill_code = '" + bill_code + "'");
	}
	
	public int paySuccessCallBack(Integer id, String pay_type, String pay_code, BigDecimal pay_amount, Timestamp pay_time) {
		int sum = 0;
		RechargeVo bean = this.find(id);
		if (RechargeVo.STATUS_UNPAY.equals(bean.getStatus()) && RechargeVo.PAY_STATUS_NO.equals(bean.getPay_status())) {
			bean.setPay_type(pay_type);
			bean.setPay_code(pay_code);
			bean.setPay_amount(pay_amount);
			bean.setPay_time(pay_time);
			bean.setPay_status(RechargeVo.PAY_STATUS_SUCCESS);
			sum = this.update(bean);
			
			if (RechargeVo.STORE_TYPE_W.equals(bean.getStore_type())) {
				warehouseService.addBalance_amount(bean.getStore_id(), bean.getBill_code(), bean.getPay_code(), bean.getPay_amount(), bean.getBrief(), "");
			} else if (RechargeVo.STORE_TYPE_S.equals(bean.getStore_type())) {
				shopService.addBalance_amount(bean.getStore_id(), bean.getBill_code(), bean.getPay_code(), bean.getPay_amount(), bean.getBrief(), "");
			} else if (RechargeVo.STORE_TYPE_M.equals(bean.getStore_type())) {
				marketService.addBalance_amount(bean.getStore_id(), bean.getBill_code(), bean.getPay_code(), bean.getPay_amount(), bean.getBrief(), "");
			}
		}
		return sum;
	}
}
