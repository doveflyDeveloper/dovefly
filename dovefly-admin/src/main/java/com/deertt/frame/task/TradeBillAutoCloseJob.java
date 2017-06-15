package com.deertt.frame.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.common.pay.util.AlipayUtils;
import com.deertt.common.pay.util.WxpayUtils;
import com.deertt.common.pay.vo.AlipayResult;
import com.deertt.common.pay.vo.WxpayResult;
import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.trade.bill.service.ITradeBillService;
import com.deertt.module.trade.bill.vo.TradeBillVo;
import com.deertt.utils.helper.DvHttpHelper;

/**
 * 销售订单自动关闭任务（每五分钟执行一次，关闭三十分钟内未提交订单）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class TradeBillAutoCloseJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private ITradeBillService tradeService;
	
	@Autowired
	private INotificationService notificationService;

	@Scheduled(cron = "30 0/5 * * * ?")
	public void closeTradeBill() {
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		// 查询所有三十分钟内未提交订单
		List<TradeBillVo> trades = tradeService.queryByCondition("status = '" + TradeBillVo.STATUS_EDIT + "' AND create_at < DATE_SUB(now(), INTERVAL 30 MINUTE)", null);
		if (trades != null && trades.size() > 0) {
			for (TradeBillVo bean : trades) {
				try {
					if (TradeBillVo.PAY_TYPE_ONLINE.equals(bean.getPay_type())) {
						String message = "订单[" + bean.getBill_code() + "]处于在线支付状态";
						WxpayResult wxpayResult = WxpayUtils.orderQuery(bean.getBill_code());
						if ("SUCCESS".equals(wxpayResult.getReturn_code())) {
							if ("SUCCESS".equals(wxpayResult.getResult_code())) {
								/*交易状态	trade_state	是	String(32)	SUCCESS	
								SUCCESS—支付成功
								REFUND—转入退款
								NOTPAY—未支付
								CLOSED—已关闭
								REVOKED—已撤销（刷卡支付）
								USERPAYING--用户支付中
								PAYERROR--支付失败(其他原因，如银行返回失败)*/
								if ("SUCCESS".equals(wxpayResult.getTrade_state())) {
									
//									String url = "https://www.deertt.com/api/wexin/updateTradePay";
									String url = ApplicationConfig.UPDATE_TRADE_PAY_URL;
									
									Map<String, Object> paramMap = new HashMap<String, Object>();
									paramMap.put("billCode", bean.getBill_code());
									paramMap.put("paidFee", wxpayResult.getTotal_fee());
									paramMap.put("paidId", wxpayResult.getTransaction_id());
									paramMap.put("payType", TradeBillVo.PAY_TYPE_WXPAY);
									
									String result = DvHttpHelper.postForm(url, paramMap, null);
									JSONObject json = JSONObject.fromObject(result);
									if ("SUCCESS".equals(json.optString("return_code", null))) {
										message = "订单[" + bean.getBill_code() + "]已微信支付成功，自动关闭定时任务调用订单提交接口成功！";
									} else {
										message = "订单[" + bean.getBill_code() + "]已微信支付成功，自动关闭定时任务调用订单提交接口失败！";
									}
								} else if ("USERPAYING".equals(wxpayResult.getTrade_state())) {
									message = "订单[" + bean.getBill_code() + "]微信支付中，不能关闭订单！";
								} else {
									message = null;
									tradeService.closeUnSubmitBill(bean.getId());
								}
							} else {
								//微信支付订单不存在，可能是支付宝支付
								if ("ORDERNOTEXIST".equals(wxpayResult.getErr_code())) {
									AlipayResult alipayResult = AlipayUtils.tradeQuery(bean.getBill_code());
									if ("10000".equals(alipayResult.getCode())) {
										/*
										 * trade_status String 必填 32 交易状态：
										 * WAIT_BUYER_PAY（交易创建，等待买家付款）、
										 * TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、 
										 * TRADE_SUCCESS（交易支付成功）、
										 * TRADE_FINISHED（交易结束，不可退款）
										 * TRADE_CLOSED
										 */
										if ("TRADE_SUCCESS".equals(alipayResult.getTrade_status())) {
											
//											String url = "https://www.deertt.com/api/wexin/updatePurchasePay";
											String url = ApplicationConfig.UPDATE_TRADE_PAY_URL;
											
											Map<String, Object> paramMap = new HashMap<String, Object>();
											paramMap.put("billCode", bean.getBill_code());
											paramMap.put("paidFee", alipayResult.getTotal_amount());
											paramMap.put("paidId", alipayResult.getTrade_no());
											paramMap.put("payType", TradeBillVo.PAY_TYPE_ALIPAY);
											
											String result = DvHttpHelper.postForm(url, paramMap, null);
											JSONObject json = JSONObject.fromObject(result);
											if ("SUCCESS".equals(json.optString("return_code", null))) {
												message = "订单[" + bean.getBill_code() + "]已支付宝支付成功，自动关闭定时任务调用订单提交接口成功！";
											} else {
												message = "订单[" + bean.getBill_code() + "]已支付宝支付成功，自动关闭定时任务调用订单提交接口失败！";
											}
//										} else if ("WAIT_BUYER_PAY".equals(alipayResult.getTrade_status())) {
//											message = "订单[" + bean.getBill_code() + "]支付宝支付中，不能关闭订单！";
										} else {
											message = null;
											tradeService.closeUnSubmitBill(bean.getId());
										}
										
									} else {
										if ("ACQ.TRADE_NOT_EXIST".equals(alipayResult.getSub_code())) {
											message = null;
											tradeService.closeUnSubmitBill(bean.getId());
										}
									}
								}
							}
						} else {
							//微信调用接口有问题
							message = "订单[" + bean.getBill_code() + "]查询订单支付状态时，微信接口返回失败！";
						}
						if (message != null) {
							notificationService.addWNotification(1838, IUserConstants.TABLE_NAME + "-" + 1838 + "-" + "hint", message);
						}
					} else {
						tradeService.closeUnSubmitBill(bean.getId());
					}
//					logger.info("----------");
//					logger.info(bean);
//					logger.info("----------");
				} catch (Exception e) {
					logger.info(e);
					e.printStackTrace();
				}
			}
		}
	}
}
