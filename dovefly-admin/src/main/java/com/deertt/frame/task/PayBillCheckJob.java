package com.deertt.frame.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.frame.base.util.CheckBillTypeUtils;
import com.deertt.module.fund.apply.service.IApplyService;
import com.deertt.module.fund.apply.util.IApplyConstants;
import com.deertt.module.fund.apply.vo.ApplyVo;
import com.deertt.module.fund.recharge.service.IRechargeService;
import com.deertt.module.fund.recharge.util.IRechargeConstants;
import com.deertt.module.fund.recharge.vo.RechargeVo;
import com.deertt.module.order.bill.service.IOrderBillService;
import com.deertt.module.order.bill.util.IOrderBillConstants;
import com.deertt.module.order.bill.vo.OrderBillVo;
import com.deertt.module.pay.alipaybill.service.IAlipayBillService;
import com.deertt.module.pay.alipaybill.util.IAlipayBillConstants;
import com.deertt.module.pay.alipaybill.vo.AlipayBillVo;
import com.deertt.module.pay.checkbill.service.ICheckBillService;
import com.deertt.module.pay.checkbill.vo.CheckBillVo;
import com.deertt.module.pay.wxbill.service.IWxBillService;
import com.deertt.module.pay.wxbill.util.IWxBillConstants;
import com.deertt.module.pay.wxbill.vo.WxBillVo;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.trade.bill.service.ITradeBillService;
import com.deertt.module.trade.bill.util.ITradeBillConstants;
import com.deertt.module.trade.bill.vo.TradeBillVo;
import com.deertt.utils.helper.date.DvDateHelper;

/**
 * 微信支付、支付宝支付对账单与本地订单检查（每天上午两点执行）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class PayBillCheckJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private ITradeBillService tradeService;

	@Autowired
	private IOrderBillService orderBillService;

	@Autowired
	private IApplyService applyService;
	
	@Autowired
	private IRechargeService rechargeService;
	
	@Autowired
	private IWxBillService wxBillService;
	
	@Autowired
	protected IAlipayBillService alipayBillService;
	
	@Autowired
	protected ICheckBillService checkBillService;
	
	@Autowired
	private INotificationService notificationService;
	
	// 下载微信、支付宝对账单（每天上午两点执行，）
	//微信对账单每天九点开始生成前一天的对账单，建议十点之后下载
    @Scheduled(cron = "15 5 2 * * ?")
	public void loadPayBill() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		List<CheckBillVo> checkList = new ArrayList<CheckBillVo>();
		String nofifyMsg = "";
		
		List<WxBillVo> wxList = wxBillService.queryByCondition("check_status = '0'", null, 0, 100);
		while (wxList != null && wxList.size() > 0) {
			for (WxBillVo wxBillVo : wxList) {
				wxBillVo.setCheck_status("1");
				String bill_code = wxBillVo.getBz_order();
				
				CheckBillVo checkBillVo = new CheckBillVo();
				checkBillVo.setPay_table_name(IWxBillConstants.TABLE_NAME);
				checkBillVo.setPay_bill_id(wxBillVo.getId());
				checkBillVo.setCheck_time(DvDateHelper.getSysTimestamp());
					
				if (CheckBillTypeUtils.isTradeBill(bill_code)) {
					TradeBillVo tradeBill = tradeService.findByCode(bill_code);
					if (tradeBill != null) {
						checkBillVo.setBill_type(TradeBillVo.BILL_CODE_PREFIX);
						checkBillVo.setBill_table_name(ITradeBillConstants.TABLE_NAME);
						checkBillVo.setBill_id(tradeBill.getId());
						checkBillVo.setBill_code(tradeBill.getBill_code());
						
						if (tradeBill.getPay_amount() == null) tradeBill.setPay_amount(BigDecimal.ZERO);
						if (tradeBill.getRefund_amount() == null) tradeBill.setRefund_amount(BigDecimal.ZERO);
						
						String check_msg = "";
						if ("SUCCESS".equals(wxBillVo.getTrade_status())) {
							if (!TradeBillVo.PAY_TYPE_WXPAY.equals(tradeBill.getPay_type())) {
								check_msg += "支付类型不符；";
							}
							if (!TradeBillVo.PAY_STATUS_SUCCESS.equals(tradeBill.getPay_status())) {
								check_msg += "支付状态不符；";
							}
							if (new BigDecimal(wxBillVo.getTotal_money()).compareTo(tradeBill.getPay_amount()) != 0) {
								check_msg += "支付金额不符；";
							}
						} else if ("REFUND".equals(wxBillVo.getTrade_status())) {
							if (TradeBillVo.REFUND_STATUS_SUCCESS.equals(tradeBill.getRefund_status())) {
								check_msg += "退款状态不符；";
							}
							if (new BigDecimal(wxBillVo.getRefund_money()).compareTo(tradeBill.getRefund_amount()) != 0) {
								check_msg += "退款金额不符；";
							}
						}
						checkBillVo.setCheck_msg(check_msg);
						checkBillVo.setCheck_status(StringUtils.isEmpty(check_msg) ? "1" : "0");
						if ("0".equals(checkBillVo.getCheck_status())) {
							nofifyMsg += checkBillVo.getBill_code() + ",";
						}
					}
				} else if (CheckBillTypeUtils.isOrderBill(bill_code)) {
					OrderBillVo orderBill = orderBillService.findByCode(bill_code);
					if (orderBill != null) {
						
						checkBillVo.setBill_type(OrderBillVo.BILL_CODE_PREFIX);
						checkBillVo.setBill_table_name(IOrderBillConstants.TABLE_NAME);
						checkBillVo.setBill_id(orderBill.getId());
						checkBillVo.setBill_code(orderBill.getBill_code());
						
						if (orderBill.getPay_amount() == null) orderBill.setPay_amount(BigDecimal.ZERO);
						if (orderBill.getRefund_amount() == null) orderBill.setRefund_amount(BigDecimal.ZERO);
						
						String check_msg = "";
						if ("SUCCESS".equals(wxBillVo.getTrade_status())) {
							if (!OrderBillVo.PAY_TYPE_WXPAY.equals(orderBill.getPay_type())) {
								check_msg += "支付类型不符；";
							}
							if (!OrderBillVo.PAY_STATUS_SUCCESS.equals(orderBill.getPay_status())) {
								check_msg += "支付状态不符；";
							}
							if (new BigDecimal(wxBillVo.getTotal_money()).compareTo(orderBill.getPay_amount()) != 0) {
								check_msg += "支付金额不符；";
							}
						} else if ("REFUND".equals(wxBillVo.getTrade_status())) {
							if (OrderBillVo.REFUND_STATUS_SUCCESS.equals(orderBill.getRefund_status())) {
								check_msg += "退款状态不符；";
							}
							if (new BigDecimal(wxBillVo.getRefund_money()).compareTo(orderBill.getRefund_amount()) != 0) {
								check_msg += "退款金额不符；";
							}
						}
						checkBillVo.setCheck_msg(check_msg);
						checkBillVo.setCheck_status(StringUtils.isEmpty(check_msg) ? "1" : "0");
						if ("0".equals(checkBillVo.getCheck_status())) {
							nofifyMsg += checkBillVo.getBill_code() + ",";
						}
					}
					
				} else if (CheckBillTypeUtils.isApplyBill(bill_code)) {
					//转账	代扣款-普通账户转账
					ApplyVo apply = applyService.findByCode(bill_code);
					if (apply != null) {
						checkBillVo.setBill_type(ApplyVo.BILL_CODE_PREFIX);
						checkBillVo.setBill_table_name(IApplyConstants.TABLE_NAME);
						checkBillVo.setBill_id(apply.getId());
						checkBillVo.setBill_code(apply.getBill_code());
						
						String check_msg = "";
						if (!ApplyVo.PAY_STATUS_SUCCESS.equals(apply.getPay_status())) {
							check_msg += "支付状态不符；";
						}
						checkBillVo.setCheck_msg(check_msg);
						checkBillVo.setCheck_status(StringUtils.isEmpty(check_msg) ? "1" : "0");
						if ("0".equals(checkBillVo.getCheck_status())) {
							nofifyMsg += checkBillVo.getBill_code() + ",";
						}
					}
				} else if (CheckBillTypeUtils.isRechargeBill(bill_code)) {
					RechargeVo recharge = rechargeService.findByCode(bill_code);
					if (recharge != null) {
						checkBillVo.setBill_type(RechargeVo.BILL_CODE_PREFIX);
						checkBillVo.setBill_table_name(IRechargeConstants.TABLE_NAME);
						checkBillVo.setBill_id(recharge.getId());
						checkBillVo.setBill_code(recharge.getBill_code());
						
						if (recharge.getPay_amount() == null) recharge.setPay_amount(BigDecimal.ZERO);
						
						String check_msg = "";
						if (!RechargeVo.PAY_TYPE_WXPAY.equals(recharge.getPay_type())) {
							check_msg += "支付类型不符；";
						}
						if (!RechargeVo.PAY_STATUS_SUCCESS.equals(recharge.getPay_status())) {
							check_msg += "支付状态不符；";
						}
						if (new BigDecimal(wxBillVo.getTotal_money()).compareTo(recharge.getPay_amount()) != 0) {
							check_msg += "支付金额不符；";
						}
						
						checkBillVo.setCheck_msg(check_msg);
						checkBillVo.setCheck_status(StringUtils.isEmpty(check_msg) ? "1" : "0");
						if ("0".equals(checkBillVo.getCheck_status())) {
							nofifyMsg += checkBillVo.getBill_code() + ",";
						}
					}
				}
				
				checkList.add(checkBillVo);
			}
			checkBillService.insert(checkList.toArray(new CheckBillVo[0]));
			wxBillService.update(wxList.toArray(new WxBillVo[0]));
			
			//下一轮
			checkList.clear();
			wxList = wxBillService.queryByCondition("check_status = '0'", null, 0, 100);

		}
		
		//-----------------------------------------------------------------------------
		
		List<AlipayBillVo> alipayList = alipayBillService.queryByCondition("check_status = '0'", null, 0, 100);
		while (alipayList != null && alipayList.size() > 0) {
			for (AlipayBillVo alipayBillVo : alipayList) {
				alipayBillVo.setCheck_status("1");
				String bill_code = alipayBillVo.getMerchant_out_order_no();
				
				if (StringUtils.isEmpty(bill_code) || "收费".equals(alipayBillVo.getTrans_code_msg()) || "提现".equals(alipayBillVo.getTrans_code_msg())) {
					continue;
				}
				
				CheckBillVo checkBillVo = new CheckBillVo();
				checkBillVo.setPay_table_name(IAlipayBillConstants.TABLE_NAME);
				checkBillVo.setPay_bill_id(alipayBillVo.getId());
				checkBillVo.setCheck_time(DvDateHelper.getSysTimestamp());
					
				if (CheckBillTypeUtils.isTradeBill(bill_code)) {
					TradeBillVo tradeBill = tradeService.findByCode(bill_code);
					if (tradeBill != null) {
						checkBillVo.setBill_type(TradeBillVo.BILL_CODE_PREFIX);
						checkBillVo.setBill_table_name(ITradeBillConstants.TABLE_NAME);
						checkBillVo.setBill_id(tradeBill.getId());
						checkBillVo.setBill_code(tradeBill.getBill_code());
						
						if (tradeBill.getPay_amount() == null) tradeBill.setPay_amount(BigDecimal.ZERO);
						if (tradeBill.getRefund_amount() == null) tradeBill.setRefund_amount(BigDecimal.ZERO);
						
						String check_msg = "";
						if ("在线支付".equals(alipayBillVo.getTrans_code_msg())) {//支付
							if (!TradeBillVo.PAY_TYPE_ALIPAY.equals(tradeBill.getPay_type())) {
								check_msg += "支付类型不符；";
							}
							if (!TradeBillVo.PAY_STATUS_SUCCESS.equals(tradeBill.getPay_status())) {
								check_msg += "支付状态不符；";
							}
							if (new BigDecimal(alipayBillVo.getTotal_fee()).compareTo(tradeBill.getPay_amount()) != 0) {
								check_msg += "支付金额不符；";
							}
						} else if ("转账".equals(alipayBillVo.getTrans_code_msg()) && "交易退款".equals(alipayBillVo.getSub_trans_code_msg())) {//退款
							if (!TradeBillVo.REFUND_STATUS_SUCCESS.equals(tradeBill.getRefund_status())) {
								check_msg += "退款状态不符；";
							}
							if (new BigDecimal(alipayBillVo.getOutcome()).compareTo(tradeBill.getRefund_amount()) != 0) {
								check_msg += "退款金额不符；";
							}
						}
						checkBillVo.setCheck_msg(check_msg);
						checkBillVo.setCheck_status(StringUtils.isEmpty(check_msg) ? "1" : "0");
						if ("0".equals(checkBillVo.getCheck_status())) {
							nofifyMsg += checkBillVo.getBill_code() + ",";
						}
					}
				} else if (CheckBillTypeUtils.isOrderBill(bill_code)) {
					OrderBillVo orderBill = orderBillService.findByCode(bill_code);
					if (orderBill != null) {
						
						checkBillVo.setBill_type(OrderBillVo.BILL_CODE_PREFIX);
						checkBillVo.setBill_table_name(IOrderBillConstants.TABLE_NAME);
						checkBillVo.setBill_id(orderBill.getId());
						checkBillVo.setBill_code(orderBill.getBill_code());
						
						if (orderBill.getPay_amount() == null) orderBill.setPay_amount(BigDecimal.ZERO);
						if (orderBill.getRefund_amount() == null) orderBill.setRefund_amount(BigDecimal.ZERO);
						
						String check_msg = "";
						if ("在线支付".equals(alipayBillVo.getTrans_code_msg())) {//支付
							if (!OrderBillVo.PAY_TYPE_ALIPAY.equals(orderBill.getPay_type())) {
								check_msg += "支付类型不符；";
							}
							if (!OrderBillVo.PAY_STATUS_SUCCESS.equals(orderBill.getPay_status())) {
								check_msg += "支付状态不符；";
							}
							if (new BigDecimal(alipayBillVo.getTotal_fee()).compareTo(orderBill.getPay_amount()) != 0) {
								check_msg += "支付金额不符；";
							}
						} else if ("转账".equals(alipayBillVo.getTrans_code_msg()) && "交易退款".equals(alipayBillVo.getSub_trans_code_msg())) {//退款
							if (!OrderBillVo.REFUND_STATUS_SUCCESS.equals(orderBill.getRefund_status())) {
								check_msg += "退款状态不符；";
							}
							if (new BigDecimal(alipayBillVo.getOutcome()).compareTo(orderBill.getRefund_amount()) != 0) {
								check_msg += "退款金额不符；";
							}
						}
						checkBillVo.setCheck_msg(check_msg);
						checkBillVo.setCheck_status(StringUtils.isEmpty(check_msg) ? "1" : "0");
						if ("0".equals(checkBillVo.getCheck_status())) {
							nofifyMsg += checkBillVo.getBill_code() + ",";
						}
					}
					
				} else if (CheckBillTypeUtils.isApplyBill(bill_code)) {
					//转账	代扣款-普通账户转账
					ApplyVo apply = applyService.findByCode(bill_code);
					if (apply != null) {
						checkBillVo.setBill_type(ApplyVo.BILL_CODE_PREFIX);
						checkBillVo.setBill_table_name(IApplyConstants.TABLE_NAME);
						checkBillVo.setBill_id(apply.getId());
						checkBillVo.setBill_code(apply.getBill_code());
						
						String check_msg = "";
						if (!ApplyVo.PAY_STATUS_SUCCESS.equals(apply.getPay_status())) {
							check_msg += "支付状态不符；";
						}
						if (new BigDecimal(alipayBillVo.getOutcome()).compareTo(apply.getApply_amount()) != 0) {
							check_msg += "支付金额不符；";
						}
						
						checkBillVo.setCheck_msg(check_msg);
						checkBillVo.setCheck_status(StringUtils.isEmpty(check_msg) ? "1" : "0");
						if ("0".equals(checkBillVo.getCheck_status())) {
							nofifyMsg += checkBillVo.getBill_code() + ",";
						}
					}
				} else if (CheckBillTypeUtils.isRechargeBill(bill_code)) {
					RechargeVo recharge = rechargeService.findByCode(bill_code);
					if (recharge != null) {
						checkBillVo.setBill_type(RechargeVo.BILL_CODE_PREFIX);
						checkBillVo.setBill_table_name(IRechargeConstants.TABLE_NAME);
						checkBillVo.setBill_id(recharge.getId());
						checkBillVo.setBill_code(recharge.getBill_code());
						
						String check_msg = "";
						if (!RechargeVo.PAY_TYPE_ALIPAY.equals(recharge.getPay_type())) {
							check_msg += "支付类型不符；";
						}
						if (!RechargeVo.PAY_STATUS_SUCCESS.equals(recharge.getPay_status())) {
							check_msg += "支付状态不符；";
						}
						if (new BigDecimal(alipayBillVo.getTotal_fee()).compareTo(recharge.getPay_amount()) != 0) {
							check_msg += "支付金额不符；";
						}
						
						checkBillVo.setCheck_msg(check_msg);
						checkBillVo.setCheck_status(StringUtils.isEmpty(check_msg) ? "1" : "0");
						if ("0".equals(checkBillVo.getCheck_status())) {
							nofifyMsg += checkBillVo.getBill_code() + ",";
						}
					}
				}
				
				checkList.add(checkBillVo);
			}
			checkBillService.insert(checkList.toArray(new CheckBillVo[0]));
			alipayBillService.update(alipayList.toArray(new AlipayBillVo[0]));
			
			//下一轮
			checkList.clear();
			alipayList = alipayBillService.queryByCondition("check_status = '0'", null, 0, 100);

		}
		
		if (StringUtils.isNotEmpty(nofifyMsg)) {
			String message = "以下订单对账时发现有问题：" + nofifyMsg;
			notificationService.addWNotification(1838, IUserConstants.TABLE_NAME + "-" + 1838 + "-" + "hint", message);
		}
	}

}
