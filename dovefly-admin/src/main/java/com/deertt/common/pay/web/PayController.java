package com.deertt.common.pay.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.deertt.common.pay.util.AlipayUtils;
import com.deertt.common.pay.util.WxpayUtils;
import com.deertt.common.pay.vo.AlipayUserInfo;
import com.deertt.common.pay.vo.PayCheckResult;
import com.deertt.common.pay.vo.PayResult;
import com.deertt.common.pay.vo.RefundCheckResult;
import com.deertt.common.pay.vo.RefundResult;
import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.frame.base.util.CheckBillTypeUtils;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.fund.apply.service.IApplyService;
import com.deertt.module.fund.apply.vo.ApplyVo;
import com.deertt.module.fund.recharge.service.IRechargeService;
import com.deertt.module.fund.recharge.vo.RechargeVo;
import com.deertt.module.fund.refund.service.IRefundService;
import com.deertt.module.fund.refund.vo.RefundVo;
import com.deertt.module.order.bill.service.IOrderBillService;
import com.deertt.module.order.bill.vo.OrderBillVo;
import com.deertt.module.sys.market.service.IMarketService;
import com.deertt.module.sys.market.vo.MarketVo;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.shop.vo.ShopVo;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
import com.deertt.module.sys.warehouse.vo.WarehouseVo;
import com.deertt.module.trade.bill.service.ITradeBillService;
import com.deertt.module.trade.bill.vo.TradeBillVo;
import com.deertt.utils.helper.DvJspHelper;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.encryt.Digest;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Controller
@RequestMapping("/pay")
public class PayController extends DvBaseController {
	
	public final static String JSP_PREFIX = "jsp/common/pay";
	
	@Autowired
	protected ITradeBillService tradeService;
	
	@Autowired
	protected IOrderBillService orderService;
	
	@Autowired
	protected IRechargeService rechargeService;
	
	@Autowired
	protected IApplyService applyService;
	
	@Autowired
	protected IRefundService refundService;
	
	@Autowired
	protected IUserService userService;
	
	@Autowired
	protected IWarehouseService warehouseService;
	
	@Autowired
	protected IShopService shopService;
	
	@Autowired
	protected IMarketService marketService;
	
	/*---------- 汀汀支付 start----------*/
	/**
	 * 前往汀汀支付页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toTtpay/{bill_code}")
	public String toTtpay(HttpServletRequest request, @PathVariable("bill_code") String bill_code) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (CheckBillTypeUtils.isTradeBill(bill_code)) {
			TradeBillVo bean = tradeService.findByCode(bill_code);
			map.put("out_trade_no", bean.getBill_code());
			map.put("total_fee", bean.getReal_amount());
			map.put("user_account", userService.find(bean.getBuyer_id()).getAccount());
			request.setAttribute("bean", map);
		} else if (CheckBillTypeUtils.isOrderBill(bill_code)) {
			OrderBillVo bean = orderService.findByCode(bill_code);
			map.put("out_trade_no", bean.getBill_code());
			map.put("total_fee", bean.getReal_amount());
			map.put("user_account", userService.find(shopService.find(bean.getShop_id()).getShopkeeper_id()).getAccount());
			request.setAttribute(REQUEST_BEAN, map);
		} else {
			throw new BusinessException("不支持的订单类型！");
		}
		
		return JSP_PREFIX + "/ttpay";
	}
	
	/**
	 * 汀汀支付
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ttpay/{bill_code}")
	public String ttpay(HttpServletRequest request, @PathVariable("bill_code") String bill_code) throws Exception {
		String basePath = ApplicationConfig.PROJECT_CONTEXT_PATH;
		String msg = "";
		String redirect = "";
		
		String user_account = request.getParameter("user_account");
		String user_password = request.getParameter("user_password");
		String password = Digest.hex2Base64(Digest.SHA1(user_account + user_password, Digest.Cipher.HEX));
		if (!password.equals(LoginHelper.getUser().getPassword()) || !LoginHelper.getUserAccount().equals(user_account)) {
			throw new BusinessException("支付账号或密码错误！");
		}

		if (CheckBillTypeUtils.isTradeBill(bill_code)) {
			throw new BusinessException("销售订单暂时不支持汀汀支付！");
		} else if (CheckBillTypeUtils.isOrderBill(bill_code)) {
			OrderBillVo bean = orderService.findByCode(bill_code);
			if (!OrderBillVo.PAY_STATUS_NO.equals(bean.getPay_status())) {
				throw new BusinessException("订单支付状态异常！");
			}
			
			String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
			orderService.paySuccessCallBack(bean.getId(), OrderBillVo.PAY_TYPE_TTPAY, pay_code, bean.getReal_amount(), DvDateHelper.getSysTimestamp());
			msg = "支付成功！订单号：" + bean.getBill_code() + "，汀汀支付流水号：" + pay_code;
			
			redirect = basePath + "/orderBillController/detail/" + bean.getId();
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("redirect", redirect);
		return JSP_PREFIX + "/ttpayReturn";
	}
	/*---------- 汀汀支付 end----------*/
	
	/*---------- 支付宝支付 start----------*/
	/**
	 * 支付宝支付（及时到账）
	 * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.bqPdYG&treeId=108&articleId=104743&docType=1
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAlipay/{bill_code}")
	public String toAlipay(HttpServletRequest request, @PathVariable("bill_code") String bill_code) throws Exception {
		
		//支付类型
		String payment_type = "1";
		//必填，不能修改
		
		//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String basePath = ApplicationConfig.PROJECT_CONTEXT_PATH;
			
		//页面跳转异步通知页面路径
		String notify_url = basePath + "/pay/alipayNotify";
		//需http://格式的完整路径，不能加?id=123这类自定义参数
		
		//页面跳转同步通知页面路径
		String return_url = basePath + "/pay/alipayReturn";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
		
		//防钓鱼时间戳
		String anti_phishing_key = "";
		//若要使用请调用类文件submit中的query_timestamp函数
		
		//客户端的IP地址
		String exter_invoke_ip = DvJspHelper.getRemoteIp(request);
		//非局域网的外网IP地址，如：221.0.0.1
		
		//商户订单号
		String out_trade_no = "";
		//商户网站订单系统中唯一订单号，必填
		//订单名称
		String subject = "";
		//必填
		//付款金额
		String total_fee = "0.00";
		//必填
		
		//订单描述
		String body = "";
		
		if (CheckBillTypeUtils.isTradeBill(bill_code)) {
			TradeBillVo bean = tradeService.findByCode(bill_code);
			out_trade_no = bean.getBill_code();
			subject = "订单【" + bean.getBill_code() + "】";
			total_fee = bean.getReal_amount() + "";
			body = "";
		} else if (CheckBillTypeUtils.isOrderBill(bill_code)) {
			OrderBillVo bean = orderService.findByCode(bill_code);
			out_trade_no = bean.getBill_code();
			subject = "订单【" + bean.getBill_code() + "】";
			total_fee = bean.getReal_amount() + "";
			body = "";
		} else if (CheckBillTypeUtils.isRechargeBill(bill_code)) {//充值
			RechargeVo bean = rechargeService.findByCode(bill_code);
			out_trade_no = bean.getBill_code();
			subject = "订单【" + bean.getBill_code() + "】";
			total_fee = bean.getRecharge_amount() + "";
			body = "";
		} else {
			throw new BusinessException("不支持的订单类型！");
		}
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service_pay);
	    sParaTemp.put("partner", AlipayConfig.partner);
	    sParaTemp.put("seller_email", AlipayConfig.seller_email);
	    sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		request.setAttribute("payForm", sHtmlText);
		return JSP_PREFIX + "/alipay";
	}
	
	/**
	 * 支付宝支付完成异步通知回调接口
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/alipayNotify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "";	//请不要修改或删除(此接口只能返回"success"或"fail")
		try {
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = iter.next();
				String[] values = requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
			
			//商户订单号
			String bill_code = request.getParameter("out_trade_no");
			
			//支付宝交易号
			String trade_no = request.getParameter("trade_no");
			
			//交易状态
			String trade_status = request.getParameter("trade_status");
			
			//买家邮箱或手机号
			String buyer_email = request.getParameter("buyer_email");
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)
			if (AlipayNotify.verify(params)) {//验证成功
				//请在这里加上商户的业务逻辑程序代码
				if (trade_status.equals("TRADE_FINISHED")) {
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
					
					//注意：
					//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
					
					//注意：
					//付款完成后，支付宝系统发送该交易状态通知
					if (CheckBillTypeUtils.isOrderBill(bill_code)) {
						OrderBillVo bean = orderService.findByCode(bill_code);
						if (OrderBillVo.PAY_STATUS_NO.equals(bean.getPay_status())) {
							orderService.paySuccessCallBack(bean.getId(), OrderBillVo.PAY_TYPE_ALIPAY, trade_no, bean.getReal_amount(), DvDateHelper.getSysTimestamp());
						}
						
						//抓取用户支付宝账号记录到用户表内
//						if (StringUtils.isNotEmpty(buyer_email)) {
//							UserVo user = userService.find(bean.getBuyer_id());
//							if (StringUtils.isEmpty(user.getAlipay_account())) {
//								user.setAlipay_account(buyer_email);
//								userService.changeAlipay(user);
//							}
//						}
						
					} else if (CheckBillTypeUtils.isRechargeBill(bill_code)) {//充值
						RechargeVo bean = rechargeService.findByCode(bill_code);
						if (RechargeVo.PAY_STATUS_NO.equals(bean.getPay_status())) {
							rechargeService.paySuccessCallBack(bean.getId(), RechargeVo.PAY_TYPE_ALIPAY, trade_no, bean.getRecharge_amount(), DvDateHelper.getSysTimestamp());
						}
						
						//抓取用户支付宝账号记录到用户表内
//						if (StringUtils.isNotEmpty(buyer_email)) {
//							UserVo user = userService.find(bean.getReceive_id());
//							if (StringUtils.isEmpty(user.getAlipay_account())) {
//								user.setAlipay_account(buyer_email);
//								userService.changeAlipay(user);
//							}
//						}
					}
				}
				result = "success";
			} else {//验证失败
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
		}
		
		response.getOutputStream().print(result);
		response.flushBuffer();
	}
	
	/**
	 * 支付宝支付完成同步通知页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/alipayReturn")
	public String alipayReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String basePath = ApplicationConfig.PROJECT_CONTEXT_PATH;
		String msg = "";
		String redirect = "";
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
		
		//商户订单号
		String bill_code = request.getParameter("out_trade_no");
		
		//支付宝交易号
		String trade_no = request.getParameter("trade_no");
		
		//交易状态
		String trade_status = request.getParameter("trade_status");
		
		//买家邮箱或手机号
		String buyer_email = request.getParameter("buyer_email");
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)
		if (AlipayNotify.verify(params)) {//验证成功
			//请在这里加上商户的业务逻辑程序代码
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED")) {
				//
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				if (CheckBillTypeUtils.isOrderBill(bill_code)) {
					OrderBillVo bean = orderService.findByCode(bill_code);
                    if (OrderBillVo.PAY_STATUS_NO.equals(bean.getPay_status())) {
                    	orderService.paySuccessCallBack(bean.getId(), OrderBillVo.PAY_TYPE_ALIPAY, trade_no, bean.getReal_amount(), DvDateHelper.getSysTimestamp());
    				}
                    msg = "支付成功！订单号：" + bill_code + "，支付宝交易号：" + trade_no;
                    redirect = basePath + "/orderBillController/detail/" + bean.getId();
                    
					//抓取用户支付宝账号记录到用户表内
//					if (StringUtils.isNotEmpty(buyer_email)) {
//						UserVo user = userService.find(bean.getBuyer_id());
//						if (StringUtils.isEmpty(user.getAlipay_account())) {
//							user.setAlipay_account(buyer_email);
//							userService.changeAlipay(user);
//						}
//					}
					
				} else if (CheckBillTypeUtils.isRechargeBill(bill_code)) {//充值
					RechargeVo bean = rechargeService.findByCode(bill_code);
					if (RechargeVo.PAY_STATUS_NO.equals(bean.getPay_status())) {
						rechargeService.paySuccessCallBack(bean.getId(), RechargeVo.PAY_TYPE_ALIPAY, trade_no, bean.getRecharge_amount(), DvDateHelper.getSysTimestamp());
					}
					msg = "支付成功！订单号：" + bill_code + "，支付宝交易号：" + trade_no;
                    redirect = basePath + "/rechargeController/detail/" + bean.getId();
                    
                    //抓取用户支付宝账号记录到用户表内
//					if (StringUtils.isNotEmpty(buyer_email)) {
//						UserVo user = userService.find(bean.getStore_id());
//						if (StringUtils.isEmpty(user.getAlipay_account())) {
//							user.setAlipay_account(buyer_email);
//							userService.changeAlipay(user);
//						}
//					}
				}
			}
			
		} else {
			msg = "支付失败！订单号：" + bill_code + "，支付宝交易号：" + trade_no;
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("redirect", redirect);
		return JSP_PREFIX + "/alipayReturn";
	}

	/*---------- 支付宝支付 end----------*/
	
	/*---------- 微信支付 start----------*/
	/**
	 * 微信支付，获取扫描支付二维码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toWxpay/{bill_code}")
	public String toWxpay(HttpServletRequest request, @PathVariable("bill_code") String bill_code) throws Exception {
		String basePath = ApplicationConfig.PROJECT_CONTEXT_PATH;
		
		//支付扫描二维码地址
		String code_url = null;
		
		//错误提示消息
		String error_msg = null;
		
		//支付结果通知页面
		String notify_url = basePath + "/pay/wxpayNotify";;
		
		//支付结果查询页面
		String check_url = "";
		
		//支付完成跳转页面
		String return_url = "";
		
		//商户订单号
		String out_trade_no = "";
		
		//付款金额(分)
		int total_fee = 0;
		
		//订单描述
		String body = "";
		
		//商品ID trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
		String product_id = "";
		
		if (CheckBillTypeUtils.isTradeBill(bill_code)) {
			TradeBillVo bean = tradeService.findByCode(bill_code);
			out_trade_no = bean.getBill_code();
			total_fee = bean.getReal_amount().multiply(new BigDecimal(100)).intValue();
			body = "订单【" + out_trade_no + "】";
			product_id = bean.getId().toString();
			check_url = basePath + "/tradeBillController/checkPayStatus/" + bean.getId();
			return_url = basePath + "/pay/wxpayReturn/" + bean.getBill_code();
		} else if (CheckBillTypeUtils.isOrderBill(bill_code)) {
			OrderBillVo bean = orderService.findByCode(bill_code);
			out_trade_no = bean.getBill_code();
			total_fee = bean.getReal_amount().multiply(new BigDecimal(100)).intValue();
			body = "订单【" + bean.getBill_code() + "】";
			product_id = bean.getId().toString();
			check_url = basePath + "/orderBillController/checkPayStatus/" + bean.getId();
			return_url = basePath + "/pay/wxpayReturn/" + bean.getBill_code();
		} else if (CheckBillTypeUtils.isRechargeBill(bill_code)) {//充值
			RechargeVo bean = rechargeService.findByCode(bill_code);
			out_trade_no = bean.getBill_code();
			total_fee = bean.getRecharge_amount().multiply(new BigDecimal(100)).intValue();
			body = "订单【" + out_trade_no + "】";
			product_id = bean.getId().toString();
			check_url = basePath + "/rechargeController/checkPayStatus/" + bean.getId();
			return_url = basePath + "/pay/wxpayReturn/" + bean.getBill_code();
		} else {
			throw new BusinessException("不支持的订单类型！");
		}
		
		//建立请求
		PayResult wxpayResult = WxpayUtils.unifiedOrder(out_trade_no, total_fee, body, product_id, notify_url);
		if (wxpayResult.isSuccess()) {
			code_url = wxpayResult.getPay_code();
		} else {
			error_msg = wxpayResult.getPay_msg();
		}
		
		request.setAttribute("code_url", code_url);
		request.setAttribute("check_url", check_url);
		request.setAttribute("return_url", return_url);
		request.setAttribute("error_msg", error_msg);
		return JSP_PREFIX + "/wxpay";
	}
	
	/**
	 * 微信支付完成异步通知回调接口
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxpayNotify")
	public void wxpayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String return_code = "";
		String return_msg = "";
		try {
			String responseStr = WxpayUtils.inputStream2String(request.getInputStream());
			Map<String, String> map = WxpayUtils.parseXml2Map(responseStr);
			
			if ("SUCCESS".equals(map.get("return_code"))) {//验证成功
				if ("SUCCESS".equals(map.get("result_code"))) {
					String bill_code = map.get("out_trade_no");//商户订单号 out_trade_no
					String transaction_id = map.get("transaction_id");//微信支付订单号 transaction_id
					String time_end = map.get("time_end");//支付完成时间 yyyyMMddHHmmss
					String total_fee = map.get("total_fee");//总金额

					if (CheckBillTypeUtils.isOrderBill(bill_code)) {
						OrderBillVo bean = orderService.findByCode(bill_code);
						if (OrderBillVo.PAY_STATUS_NO.equals(bean.getPay_status())) {
							orderService.paySuccessCallBack(bean.getId(), OrderBillVo.PAY_TYPE_WXPAY, transaction_id, new BigDecimal(total_fee).divide(new BigDecimal(100)), DvDateHelper.getTimestamp(time_end));
						}
						return_code = "SUCCESS";
						return_msg = "OK";
						
					} else if (CheckBillTypeUtils.isRechargeBill(bill_code)) {//充值
						RechargeVo bean = rechargeService.findByCode(bill_code);
						if (RechargeVo.PAY_STATUS_NO.equals(bean.getPay_status())) {
							rechargeService.paySuccessCallBack(bean.getId(), RechargeVo.PAY_TYPE_WXPAY, transaction_id, new BigDecimal(total_fee).divide(new BigDecimal(100)), DvDateHelper.getTimestamp(time_end));
						}
						return_code = "SUCCESS";
						return_msg = "OK";
					}
				}
			} else {//验证失败
				return_code = "FAIL";
				return_msg = "NO";
			}
		} catch (Exception e) {
			return_code = "FAIL";
			return_msg = "NO";
		}
		String result = "<xml><return_code>" + return_code + "</return_code><return_msg>" + return_msg + "</return_msg></xml>";
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml");
		response.getOutputStream().print(result);
		response.flushBuffer();
	}
	
	/**
	 * 微信支付完成跳转页面(微信不支持支付后自动跳转，通过前台页面异步定时检查订单支付状态，自动跳转到支付成功页面)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxpayReturn/{bill_code}")
	public String wxpayReturn(HttpServletRequest request, @PathVariable("bill_code") String bill_code) throws Exception {
		String basePath = ApplicationConfig.PROJECT_CONTEXT_PATH;
		String msg = "";
		String redirect = "";
		if (CheckBillTypeUtils.isTradeBill(bill_code)) {
			TradeBillVo bean = tradeService.findByCode(bill_code);
			if (TradeBillVo.PAY_STATUS_SUCCESS.equals(bean.getPay_status())) {
				msg = "支付成功！订单号：" + bean.getBill_code() + "，微信支付交易号：" + bean.getPay_code();
			}
			redirect = basePath + "/tradeBillController/detail/" + bean.getId();
		} else if (CheckBillTypeUtils.isOrderBill(bill_code)) {
			OrderBillVo bean = orderService.findByCode(bill_code);
			if (OrderBillVo.PAY_STATUS_SUCCESS.equals(bean.getPay_status())) {
				msg = "支付成功！订单号：" + bean.getBill_code() + "，微信支付交易号：" + bean.getPay_code();
			}
			redirect = basePath + "/orderBillController/detail/" + bean.getId();
		} else if (CheckBillTypeUtils.isRechargeBill(bill_code)) {//充值
			RechargeVo bean = rechargeService.findByCode(bill_code);
			if (RechargeVo.PAY_STATUS_SUCCESS.equals(bean.getPay_status())) {
				msg = "支付成功！订单号：" + bean.getBill_code() + "，微信支付交易号：" + bean.getPay_code();
			}
			redirect = basePath + "/userController/userInfo";
		}
			
		request.setAttribute("msg", msg);
		request.setAttribute("redirect", redirect);
		return JSP_PREFIX + "/wxpayReturn";
	}
	
	/*---------- 提现支付 start----------*/
	/**
	 * 支付宝提现支付
	 * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.ivAhnm&treeId=64&articleId=104804&docType=1
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAlipayBatchPay/{bill_code}")
	public String toAlipayBatchPay(HttpServletRequest request, HttpServletResponse response, @PathVariable("bill_code") String bill_code) throws Exception {
		String basePath = ApplicationConfig.PROJECT_CONTEXT_PATH;
		
		ApplyVo bean = applyService.findByCode(bill_code);
		if (!ApplyVo.STATUS_UNDEAL.equals(bean.getStatus())) {
			throw new BusinessException("提现申请已经被处理或正在处理中，不能重复处理！");
		}
		
		if (ApplyVo.STORE_TYPE_W.equals(bean.getStore_type())) {
			WarehouseVo warehouse = warehouseService.find(bean.getStore_id());
			if (warehouse.getLocked_amount().compareTo(bean.getApply_amount()) < 0) {
				throw new BusinessException("货仓账户待提款金额不足，无法处理此请求！");
			}
		} else if (ApplyVo.STORE_TYPE_S.equals(bean.getStore_type())) {
			ShopVo shop = shopService.find(bean.getStore_id());
			if (shop.getLocked_amount().compareTo(bean.getApply_amount()) < 0) {
				throw new BusinessException("店铺账户待提款金额不足，无法处理此请求！");
			}
		} else if (ApplyVo.STORE_TYPE_M.equals(bean.getStore_type())) {
			MarketVo market = marketService.find(bean.getStore_id());
			if (market.getLocked_amount().compareTo(bean.getApply_amount()) < 0) {
				throw new BusinessException("超市账户待提款金额不足，无法处理此请求！");
			}
		}
		
		//更改状态为处理中，防止多用户重复处理
		bean.setStatus(ApplyVo.STATUS_DEALING);
		applyService.update(bean);//更改状态为处理中
		
		//服务器异步通知页面路径
		String notify_url = basePath + "/pay/alipayBatchPayNotify";

		//付款账号
		String email = AlipayConfig.seller_email;

		//付款账户名
		String account_name = AlipayConfig.seller_name;

		//付款当天日期 yyyyMMdd
		String pay_date = DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyyMMdd");

		//批次号
		
//		String batch_no = DvDateHelper.formatDate(new Date(bean.getApply_time().getTime()), "yyyyMMddHHmmss") + bean.getBill_code();
		String batch_no = DvDateHelper.getJoinedSysDateTime() + bean.getBill_code();

		//付款总金额
		String batch_fee = bean.getApply_amount().toString();

		//付款笔数
		String batch_num = "1";
		//必填，即参数detail_data的值中，“|”字符出现的数量加1，最大支持1000笔（即“|”字符出现的数量999个）

		//付款详细数据
		String detail_data = bean.getBill_code() + "^" + bean.getReceive_account() + "^" + bean.getReceive_real_name() + "^" + batch_fee + "^" + bean.getBrief();
		//示例：0001^fcm1039882808^0.01^红包福利
		//必填，格式：流水号1^收款方帐号1^真实姓名^付款金额1^备注说明1|流水号2^收款方帐号2^真实姓名^付款金额2^备注说明2....
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service_batch_trans_notify);
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("email", email);
		sParaTemp.put("account_name", account_name);
		sParaTemp.put("pay_date", pay_date);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("batch_fee", batch_fee);
		sParaTemp.put("batch_num", batch_num);
		sParaTemp.put("detail_data", detail_data);
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		request.setAttribute("payForm", sHtmlText);
		return JSP_PREFIX + "/alipayBatchPay";
	}
	
	/**
	 * 支付宝提现支付异步通知回调（由支付宝主动调用）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/alipayBatchPayNotify")
	public void alipayBatchPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "";	//请不要修改或删除(此接口只能返回"success"或"fail")
		
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		//批量付款数据中转账成功的详细信息
		String success_details = request.getParameter("success_details");

		//批量付款数据中转账失败的详细信息
		String fail_details = request.getParameter("fail_details");
		
		if (AlipayNotify.verify(params)) {//验证成功
			//请在这里加上商户的业务逻辑程序代码
			//批量付款中成功付款的信息。 格式为：流水号^收款方账号^收款账号姓名^付款金额^成功标识(S)^成功原因(null)^支付宝内部流水号^完成时间。 每条记录以“|”间隔。
			//A20160307000003^1039882808@qq.com^冯春明^1.00^S^^20160307544164544^20160307103600|
			if (StringUtils.isNotEmpty(success_details)) {//付款成功
				String[] items = success_details.split("\\|");
				for (String item : items) {
					String[] pros = item.split("\\^");
					String bill_code = pros[0];
//					String rcv_account = pros[1];
//					String rcv_name = pros[2];
					String amount = pros[3];
//					String flag = pros[4];
					String reason = pros[5];
					String alipay_no = pros[6];
					String finish_time = pros[7];
					ApplyVo bean = applyService.findByCode(bill_code);
					applyService.paying(bean.getBill_code(), ApplyVo.APPLY_TO_ALIPAY, alipay_no, new BigDecimal(amount), new Timestamp(new SimpleDateFormat("yyyyMMddHHmmss").parse(finish_time).getTime()));
					applyService.paySuccessCallBack(bean.getBill_code());
				}
			}
			if (StringUtils.isNotEmpty(fail_details)) {//付款失败
				String[] items = fail_details.split("\\|");
				for (String item : items) {
					String[] pros = item.split("\\^");
					String bill_code = pros[0];
//					String rcv_account = pros[1];
//					String rcv_name = pros[2];
					String amount = pros[3];
//					String flag = pros[4];
					String reason = pros[5];
					String alipay_no = pros[6];
					String finish_time = pros[7];
					ApplyVo bean = applyService.findByCode(bill_code);
					applyService.paying(bean.getBill_code(), ApplyVo.APPLY_TO_ALIPAY, alipay_no, new BigDecimal(amount), new Timestamp(new SimpleDateFormat("yyyyMMddHHmmss").parse(finish_time).getTime()));
					applyService.payFailCallBack(bean.getBill_code(), reason);
				}
			}
			result = "success";
		} else {//验证失败
			result = "fail";
		}
		
		response.getOutputStream().print(result);
		response.flushBuffer();
	}
	
	/**
	 * 微信提现支付
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toWxpayBatchPay/{bill_code}")
	public String toWxpayBatchPay(HttpServletRequest request, HttpServletResponse response, @PathVariable("bill_code") String bill_code) throws Exception {
		String basePath = ApplicationConfig.PROJECT_CONTEXT_PATH;
		String msg = "";
		String redirect = ""; 
		
		ApplyVo bean = applyService.findByCode(bill_code);
		if (!ApplyVo.STATUS_UNDEAL.equals(bean.getStatus())) {
			throw new BusinessException("提现申请已经被处理或正在处理中，不能重复处理！");
		}
		
		if (ApplyVo.STORE_TYPE_W.equals(bean.getStore_type())) {
			WarehouseVo warehouse = warehouseService.find(bean.getStore_id());
			if (warehouse.getLocked_amount().compareTo(bean.getApply_amount()) < 0) {
				throw new BusinessException("货仓账户待提款金额不足，无法处理此请求！");
			}
		} else if (ApplyVo.STORE_TYPE_S.equals(bean.getStore_type())) {
			ShopVo shop = shopService.find(bean.getStore_id());
			if (shop.getLocked_amount().compareTo(bean.getApply_amount()) < 0) {
				throw new BusinessException("店铺账户待提款金额不足，无法处理此请求！");
			}
		} else if (ApplyVo.STORE_TYPE_M.equals(bean.getStore_type())) {
			MarketVo market = marketService.find(bean.getStore_id());
			if (market.getLocked_amount().compareTo(bean.getApply_amount()) < 0) {
				throw new BusinessException("超市账户待提款金额不足，无法处理此请求！");
			}
		}
		
		bean.setStatus(ApplyVo.STATUS_DEALING);
		applyService.update(bean);//更改状态为处理中
		
		int amount = bean.getApply_amount().multiply(new BigDecimal(100)).intValue() ;
		
		PayResult payResult = WxpayUtils.transfers(bean.getBill_code(), bean.getReceive_account(), bean.getReceive_real_name(), amount, bean.getBrief());
		
		if (payResult.isSuccess()) {
			applyService.paying(bean.getBill_code(), ApplyVo.APPLY_TO_ALIPAY, payResult.getPay_code(), payResult.getPay_amount(), payResult.getPay_time());
			
			//5秒后查询支付结果
			Thread.sleep(5000);
			
			PayCheckResult payCheckResult = WxpayUtils.getTransferInfo(bean.getBill_code());
			if (payCheckResult.isSuccess()) {
				applyService.paySuccessCallBack(bean.getBill_code());
				msg = "提现成功！提现单号：" + bean.getBill_code() + "，微信支付交易号：" + payResult.getPay_code();
			} else {
				applyService.payFailCallBack(bean.getBill_code(), payCheckResult.getMessage());
				msg = "提现失败！失败原因：" + payCheckResult.getMessage();
			}
		} else {
			applyService.payFailCallBack(bean.getBill_code(), payResult.getPay_msg());
			msg = "提现失败！失败原因：" + payResult.getPay_msg();
		}
		
		redirect = basePath + "/applyController/detail/" + bean.getId();
		request.setAttribute("msg", msg);
		request.setAttribute("redirect", redirect);
		return JSP_PREFIX + "/wxpayBatchPayReturn";
	}
	
	/*---------- 提现支付 start----------*/
	
	/*---------- 退款 start----------*/
	/**
	 * 支付宝退款
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAlipayRefund/{refund_bill_code}")
	public String toAlipayRefund(HttpServletRequest request, HttpServletResponse response, @PathVariable("refund_bill_code") String refund_bill_code) throws Exception {
		String basePath = ApplicationConfig.PROJECT_CONTEXT_PATH;
		String msg = "";
		String redirect = "";
		
		RefundVo bean = refundService.findByCode(refund_bill_code);
		if (!RefundVo.STATUS_UNDEAL.equals(bean.getStatus())) {
			throw new BusinessException("退款申请已经被处理或正在处理中，不能重复处理！");
		}
		
		//更改状态为处理中，防止多用户重复处理
		bean.setStatus(RefundVo.STATUS_DEALING);
		refundService.update(bean);
		
		RefundResult refundResult = AlipayUtils.alipayRefund(bean.getRefer_bill_code(), bean.getRefund_amount());
		
		if (CheckBillTypeUtils.isTradeBill(bean.getRefer_bill_code())) {
			if (refundResult.isSuccess()) {
				tradeService.refunding(bean.getRefer_bill_code(), refundResult.getPay_code(), refundResult.getPay_amount(), refundResult.getPay_time());
				
				//5秒后查询支付结果
				Thread.sleep(5000);
				
				RefundCheckResult refundCheckResult = AlipayUtils.checkAlipayRefundResult(bean.getRefer_bill_code(), refundResult.getPay_code());
				if (refundCheckResult.isSuccess()) {
					tradeService.refundSuccessCallBack(bean.getRefer_bill_code());
					msg = "退款成功！退款单号：" + bean.getRefer_bill_code() + "，支付宝退款交易号：" + refundResult.getPay_code();
				} else {
					tradeService.refundFailCallBack(bean.getRefer_bill_code(), refundCheckResult.getMessage());
					msg = "退款失败！失败原因：" + refundCheckResult.getMessage();
				}
			} else {
				tradeService.refundFailCallBack(bean.getRefer_bill_code(), refundResult.getPay_msg());
				msg = "退款失败！失败原因：" + refundResult.getPay_msg();
			}
		} else if (CheckBillTypeUtils.isOrderBill(bean.getRefer_bill_code())) {
			if (refundResult.isSuccess()) {
				orderService.refunding(bean.getRefer_bill_code(), OrderBillVo.REFUND_TYPE_ALIPAY, refundResult.getPay_code(), refundResult.getPay_amount(), refundResult.getPay_time());
				
				//5秒后查询支付结果
				Thread.sleep(5000);
				
				RefundCheckResult refundCheckResult = AlipayUtils.checkAlipayRefundResult(bean.getRefer_bill_code(), refundResult.getPay_code());
				if (refundCheckResult.isSuccess()) {
					orderService.refundSuccessCallBack(bean.getRefer_bill_code());
					msg = "退款成功！退款单号：" + bean.getRefer_bill_code() + "，支付宝退款交易号：" + refundResult.getPay_code();
				} else {
					orderService.refundFailCallBack(bean.getRefer_bill_code(), refundCheckResult.getMessage());
					msg = "退款失败！失败原因：" + refundCheckResult.getMessage();
				}
			} else {
				orderService.refundFailCallBack(bean.getRefer_bill_code(), refundResult.getPay_msg());
				msg = "退款失败！失败原因：" + refundResult.getPay_msg();
			}
		}
		
		redirect = basePath + "/refundController/detail/" + bean.getId();
		request.setAttribute("msg", msg);
		request.setAttribute("redirect", redirect);
		return JSP_PREFIX + "/alipayRefundReturn";
	}
	
	/**
	 * 微信退款页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toWxpayRefund/{refund_bill_code}")
	public String toWxpayRefund(HttpServletRequest request, HttpServletResponse response, @PathVariable("refund_bill_code") String refund_bill_code) throws Exception {
		String basePath = ApplicationConfig.PROJECT_CONTEXT_PATH;
		String msg = "";
		String redirect = "";
		
		RefundVo bean = refundService.findByCode(refund_bill_code);
		if (!RefundVo.STATUS_UNDEAL.equals(bean.getStatus())) {
			throw new BusinessException("退款申请已经被处理或正在处理中，不能重复处理！");
		}
		
		//更改状态为处理中，防止多用户重复处理
		bean.setStatus(RefundVo.STATUS_DEALING);
		refundService.update(bean);
		
		int total_fee = bean.getRefund_amount().multiply(new BigDecimal(100)).intValue();
		int refund_fee = total_fee;
		
		RefundResult refundResult = WxpayUtils.refund(bean.getBill_code(), bean.getRefer_bill_code(), total_fee, refund_fee);
		
		if (CheckBillTypeUtils.isTradeBill(bean.getRefer_bill_code())) {
			if (refundResult.isSuccess()) {
				tradeService.refunding(bean.getRefer_bill_code(), refundResult.getPay_code(), refundResult.getPay_amount(), refundResult.getPay_time());
				
				//5秒后查询支付结果
				Thread.sleep(5000);
				
				RefundCheckResult refundCheckResult = WxpayUtils.refundquery(bean.getRefer_bill_code());
				if (refundCheckResult.isSuccess()) {
					tradeService.refundSuccessCallBack(bean.getRefer_bill_code());
					msg = "退款成功！退款单号：" + bean.getRefer_bill_code() + "，微信退款交易号：" + refundResult.getPay_code();
				} else {
					tradeService.refundFailCallBack(bean.getRefer_bill_code(), refundCheckResult.getMessage());
					msg = "退款失败！失败原因：" + refundCheckResult.getMessage();
				}
			} else {
				tradeService.refundFailCallBack(bean.getRefer_bill_code(), refundResult.getPay_msg());
				msg = "退款失败！失败原因：" + refundResult.getPay_msg();
			}
		} else if (CheckBillTypeUtils.isOrderBill(bean.getRefer_bill_code())) {
			if (refundResult.isSuccess()) {
				orderService.refunding(bean.getRefer_bill_code(), OrderBillVo.REFUND_TYPE_WXPAY, refundResult.getPay_code(), refundResult.getPay_amount(), refundResult.getPay_time());
				
				//5秒后查询支付结果
				Thread.sleep(5000);
				
				RefundCheckResult refundCheckResult = WxpayUtils.refundquery(bean.getRefer_bill_code());
				if (refundCheckResult.isSuccess()) {
					orderService.refundSuccessCallBack(bean.getRefer_bill_code());
					msg = "退款成功！退款单号：" + bean.getRefer_bill_code() + "，微信退款交易号：" + refundResult.getPay_code();
				} else {
					orderService.refundFailCallBack(bean.getRefer_bill_code(), refundCheckResult.getMessage());
					msg = "退款失败！失败原因：" + refundCheckResult.getMessage();
				}
			} else {
				orderService.refundFailCallBack(bean.getRefer_bill_code(), refundResult.getPay_msg());
				msg = "退款失败！失败原因：" + refundResult.getPay_msg();
			}
		}
		
		redirect = basePath + "/refundController/detail/" + bean.getId();
		request.setAttribute("msg", msg);
		request.setAttribute("redirect", redirect);
		return JSP_PREFIX + "/wxpayRefundReturn";
	}
	/*---------- 退款 end----------*/
	
	
	/**
	 * 支付宝提现支付异步通知回调（由支付宝主动调用）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/alipayAuthCallback")
	public String alipayAuthCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "";
		
		String auth_code = request.getParameter("auth_code");
		String app_id = request.getParameter("app_id");
		String scope = request.getParameter("scope");
		String state = request.getParameter("state");
		logger.info("-------------------------------------------------------");
		logger.info(request.getParameterMap());
		AlipayUserInfo userInfo = AlipayUtils.alipayAuth(auth_code);
		
		return JSP_PREFIX + "/alipayAuth";
	}
	
}
