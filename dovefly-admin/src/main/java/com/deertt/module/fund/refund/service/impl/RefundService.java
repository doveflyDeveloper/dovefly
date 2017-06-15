package com.deertt.module.fund.refund.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.refund.dao.IRefundDao;
import com.deertt.module.fund.refund.service.IRefundService;
import com.deertt.module.fund.refund.util.IRefundConstants;
import com.deertt.module.fund.refund.vo.RefundVo;
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
public class RefundService extends DvBaseService<IRefundDao, RefundVo, Integer> implements IRefundService, IRefundConstants {
	
	@Transactional
	public int addORefund(Integer seller_id, String seller_name, Integer buyer_id, String buyer_name, String refer_bill_code, String refund_to, BigDecimal refund_amount) {
		return addRefund(RefundVo.BILL_TYPE_O, seller_id, seller_name, buyer_id, buyer_name, refer_bill_code, refund_to, refund_amount);
	}

	@Transactional
	public int addTRefund(Integer seller_id, String seller_name, Integer buyer_id, String buyer_name, String refer_bill_code, String refund_to, BigDecimal refund_amount) {
		return addRefund(RefundVo.BILL_TYPE_T, seller_id, seller_name, buyer_id, buyer_name, refer_bill_code, refund_to, refund_amount);
	}

	@Transactional
	public int addMRefund(Integer seller_id, String seller_name, Integer buyer_id, String buyer_name, String refer_bill_code, String refund_to, BigDecimal refund_amount) {
		return addRefund(RefundVo.BILL_TYPE_M, seller_id, seller_name, buyer_id, buyer_name, refer_bill_code, refund_to, refund_amount);
	}
	
	@Transactional
	public int addRefund(String bill_type, Integer seller_id, String seller_name, Integer buyer_id, String buyer_name, String refer_bill_code, String refund_to, BigDecimal refund_amount) {
		RefundVo vo = new RefundVo();
		
		vo.setBill_type(bill_type);
		vo.setSeller_id(seller_id);
		vo.setSeller_name(seller_name);
		vo.setBuyer_id(buyer_id);
		vo.setBuyer_name(buyer_name);
		vo.setRefer_bill_code(refer_bill_code);
		vo.setBill_code(this.generateBillCode(RefundVo.BILL_CODE_PREFIX));
		
		vo.setRefund_time(DvDateHelper.getSysTimestamp());
		vo.setRefund_amount(refund_amount);
		vo.setRefund_to(refund_to);
		vo.setBrief("用户申请退款" + vo.getRefund_amount() + "元");
		vo.setPay_type("");
		vo.setPay_code("");
		vo.setPay_amount(BigDecimal.ZERO);
		vo.setPay_time(null);
		vo.setPay_status(RefundVo.PAY_STATUS_UNPAY);
		vo.setPay_msg("");
		
		vo.setStatus(RefundVo.STATUS_UNDEAL);
		vo.setCreate_at(DvDateHelper.getSysTimestamp());
		
		return this.insert(vo);
	}
	
	public RefundVo findByCode(String bill_code) {
		return getDao().findByCondition("bill_code = '" + bill_code + "'");
	}
	
	public RefundVo findByReferBillCode(String refer_bill_code) {
		return getDao().findByCondition("refer_bill_code = '" + refer_bill_code + "'");
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
