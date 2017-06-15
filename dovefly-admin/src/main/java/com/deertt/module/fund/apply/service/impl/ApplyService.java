package com.deertt.module.fund.apply.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.apply.dao.IApplyDao;
import com.deertt.module.fund.apply.service.IApplyService;
import com.deertt.module.fund.apply.util.IApplyConstants;
import com.deertt.module.fund.apply.vo.ApplyVo;
import com.deertt.module.sys.market.service.IMarketService;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
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
public class ApplyService extends DvBaseService<IApplyDao, ApplyVo, Integer> implements IApplyService, IApplyConstants {
	
	@Autowired
	private IWarehouseService warehouseService;
	
	@Autowired
	private IShopService shopService;
	
	@Autowired
	private IMarketService marketService;
	
	@Autowired
	protected INotificationService notificationService;
	
	@Transactional
	public int addWApply(Integer store_id, String store_name, Integer user_id, String user_name, String receive_account, String receive_real_name, String apply_to, BigDecimal apply_amount) {
		return addApply(ApplyVo.STORE_TYPE_W, store_id, store_name, user_id, user_name, receive_account, receive_real_name, apply_to, apply_amount);
	}
	
	@Transactional
	public int addSApply(Integer store_id, String store_name, Integer user_id, String user_name, String receive_account, String receive_real_name, String apply_to, BigDecimal apply_amount) {
		return addApply(ApplyVo.STORE_TYPE_S, store_id, store_name, user_id, user_name, receive_account, receive_real_name, apply_to, apply_amount);
	}
	
	@Transactional
	public int addMApply(Integer store_id, String store_name, Integer user_id, String user_name, String receive_account, String receive_real_name, String apply_to, BigDecimal apply_amount) {
		return addApply(ApplyVo.STORE_TYPE_M, store_id, store_name, user_id, user_name, receive_account, receive_real_name, apply_to, apply_amount);
	}
	
	@Transactional
	public int addApply(String store_type, Integer store_id, String store_name, Integer user_id, String user_name, String receive_account, String receive_real_name, String apply_to, BigDecimal apply_amount) {
		ApplyVo vo = new ApplyVo();
		vo.setStore_type(store_type);
		vo.setStore_id(store_id);
		vo.setStore_name(store_name);
		vo.setUser_id(user_id);
		vo.setUser_name(user_name);
		vo.setBill_code(this.generateBillCode(ApplyVo.BILL_CODE_PREFIX));
		vo.setApply_time(DvDateHelper.getSysTimestamp());
		vo.setApply_to(apply_to);
		vo.setApply_amount(apply_amount);
		vo.setReceive_account(receive_account);
		vo.setReceive_real_name(receive_real_name);
		vo.setBrief("用户申请提现" + vo.getApply_amount() + "元");
		vo.setPay_amount(BigDecimal.ZERO);
		vo.setPay_status(ApplyVo.PAY_STATUS_UNPAY);
		vo.setStatus(ApplyVo.STATUS_UNDEAL);
		vo.setCreate_at(DvDateHelper.getSysTimestamp());
		
		if (ApplyVo.STORE_TYPE_W.equals(store_type)) {
			warehouseService.lockBalance_amount(store_id, vo.getBill_code(), apply_amount);
		} else if (ApplyVo.STORE_TYPE_S.equals(store_type)) {
			shopService.lockBalance_amount(store_id, vo.getBill_code(), apply_amount);
		} else if (ApplyVo.STORE_TYPE_M.equals(store_type)) {
			marketService.lockBalance_amount(store_id, vo.getBill_code(), apply_amount);
		}
		return this.insert(vo);
	}
	
	public ApplyVo findByCode(String bill_code) {
		return getDao().findByCondition("bill_code = '" + bill_code + "'");
	}
	
	@Transactional
	public int deny(Integer id, String reason) {
		ApplyVo bean = this.find(id);
		if (!ApplyVo.STATUS_UNDEAL.equals(bean.getStatus())) {
			throw new BusinessException("申请订单已处理或正在处理，不能重复处理！");
		}
		
		if (ApplyVo.STORE_TYPE_W.equals(bean.getStore_type())) {
			warehouseService.turnbackLockedAmount(bean.getStore_id(), bean.getBill_code(), bean.getApply_amount());
		} else if (ApplyVo.STORE_TYPE_S.equals(bean.getStore_type())) {
			shopService.turnbackLockedAmount(bean.getStore_id(), bean.getBill_code(), bean.getApply_amount());
		} else if (ApplyVo.STORE_TYPE_M.equals(bean.getStore_type())) {
			marketService.turnbackLockedAmount(bean.getStore_id(), bean.getBill_code(), bean.getApply_amount());
		}
		
		String message = "提现申请处理失败，原因：" + reason;
		notificationService.addWNotification(bean.getUser_id(), IApplyConstants.TABLE_NAME + "-" + bean.getId() + "-" + "deny", message);

		bean.setStatus(ApplyVo.STATUS_DENYED);
		return this.update(bean);
	}
	
	@Transactional
	public int paying(String bill_code, String pay_type, String pay_code, BigDecimal pay_amount, Timestamp pay_time) {
		ApplyVo bean = this.findByCode(bill_code);
		if (!ApplyVo.STATUS_DEALING.equals(bean.getStatus())) {
			throw new BusinessException("申请订单已处理或正在处理，不能重复处理！");
		}
		bean.setPay_type(pay_type);
		bean.setPay_code(pay_code);
		bean.setPay_amount(pay_amount);
		bean.setPay_time(pay_time);
		bean.setPay_status(ApplyVo.PAY_STATUS_DEALING);
		bean.setStatus(ApplyVo.STATUS_DEALING);
		return this.update(bean);
	}
	
	@Transactional
	public int paySuccessCallBack(String bill_code) {
		ApplyVo bean = this.findByCode(bill_code);
		if (!ApplyVo.STATUS_DEALING.equals(bean.getStatus())) {
			throw new BusinessException("订单不处于处理中状态，付款回调失败！");
		}
		
		if (ApplyVo.STORE_TYPE_W.equals(bean.getStore_type())) {
			warehouseService.useLockedAmount(bean.getStore_id(), bean.getBill_code(), bean.getApply_amount());
		} else if (ApplyVo.STORE_TYPE_S.equals(bean.getStore_type())) {
			shopService.useLockedAmount(bean.getStore_id(), bean.getBill_code(), bean.getApply_amount());
		} else if (ApplyVo.STORE_TYPE_M.equals(bean.getStore_type())) {
			marketService.useLockedAmount(bean.getStore_id(), bean.getBill_code(), bean.getApply_amount());
		}
		
		String message = "提现支付成功！金额：" + bean.getApply_amount() + "元";
		notificationService.addWNotification(bean.getUser_id(), IApplyConstants.TABLE_NAME + "-" + bean.getId() + "-" + "pass", message);

		bean.setPay_status(ApplyVo.PAY_STATUS_SUCCESS);
		bean.setStatus(ApplyVo.STATUS_SUCCESS);
		return this.update(bean);
	}
	
	@Transactional
	public int payFailCallBack(String bill_code, String pay_msg) {
		ApplyVo bean = this.findByCode(bill_code);
		if (!ApplyVo.STATUS_DEALING.equals(bean.getStatus())) {
			throw new BusinessException("订单不处于处理中状态，付款回调失败！");
		}
		
		if (ApplyVo.STORE_TYPE_W.equals(bean.getStore_type())) {
			warehouseService.turnbackLockedAmount(bean.getStore_id(), bean.getBill_code(), bean.getApply_amount());
		} else if (ApplyVo.STORE_TYPE_S.equals(bean.getStore_type())) {
			shopService.turnbackLockedAmount(bean.getStore_id(), bean.getBill_code(), bean.getApply_amount());
		} else if (ApplyVo.STORE_TYPE_M.equals(bean.getStore_type())) {
			marketService.turnbackLockedAmount(bean.getStore_id(), bean.getBill_code(), bean.getApply_amount());
		}
		
		String message = "提现支付失败，原因：" + pay_msg;
		notificationService.addWNotification(bean.getUser_id(), IApplyConstants.TABLE_NAME + "-" + bean.getId() + "-" + "deny", message);
		bean.setPay_msg(pay_msg);
		bean.setPay_status(ApplyVo.PAY_STATUS_FAIL);
		bean.setStatus(ApplyVo.STATUS_FAIL);
		return this.update(bean);
	}
	
	@Transactional
	public List<Map<String, Object>> reportCountGroupByStatus(String queryCondition) {
		String sql = SQL_REPORT_GROUP_BY_STATUS;
		if (StringUtils.isNotEmpty(queryCondition)) {
			sql = sql.replace("#WHERE#", "WHERE " + queryCondition);
		} else {
			sql = sql.replace("#WHERE#", "");
		}
		return (List<Map<String, Object>>) getDao().queryForMaps(sql);
	}
	
}
