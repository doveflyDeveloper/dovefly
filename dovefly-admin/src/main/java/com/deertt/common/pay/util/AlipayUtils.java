package com.deertt.common.pay.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.deertt.common.pay.vo.AlipayResult;
import com.deertt.common.pay.vo.AlipayUserInfo;
import com.deertt.common.pay.vo.RefundCheckResult;
import com.deertt.common.pay.vo.RefundResult;
import com.deertt.module.pay.alipaybill.vo.AlipayBillVo;
import com.deertt.utils.helper.date.DvDateHelper;

public class AlipayUtils {
	
	public static Logger logger = Logger.getLogger(AlipayUtils.class);
	
	public static final String URL = "https://openapi.alipay.com/gateway.do";
	
	public static void main(String[] args) {
		
		alipayRefund("T1608310856416725", new BigDecimal(3.10));
		checkAlipayRefundResult("T1608310856416725", "2016083121001004650250478533");
	}
	
	/**
	 * 支付宝订单查询
	 * @param out_trade_no 商户订单号
	 * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
	 * 
	 * 文档参考地址：
	 * alipay.trade.query (统一收单线下交易查询)
	 * https://doc.open.alipay.com/doc2/apiDetail.htm?spm=0.0.0.0.c3AyH7&apiId=757&docType=4
	 */
	public static AlipayResult tradeQuery(String out_trade_no) {
		AlipayResult result = null;
		AlipayClient alipayClient = new DefaultAlipayClient(URL, AlipayConfig.app_id, AlipayConfig.alipay_private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key);
		AlipayTradeQueryRequest req = new AlipayTradeQueryRequest();
		req.setBizContent("{" +
				"\"out_trade_no\":\"" + out_trade_no + "\"" +
				"}");
		
		try {
			logger.info("支付宝订单查询请求参数：" + req.getBizContent());
			AlipayTradeQueryResponse resp = alipayClient.execute(req);
			logger.info("支付宝订单查询返回结果：" + resp.getBody());
			System.out.println(resp.getBody());
			if ("10000".equals(resp.getCode())) {
				result = new AlipayResult(resp.getCode(), resp.getMsg(), resp.getSubCode(), resp.getSubMsg(),
						resp.getTradeNo(), resp.getOutTradeNo(),
						resp.getTradeStatus(), new BigDecimal(resp.getTotalAmount() == null ? "0" : resp.getTotalAmount()),
						new Timestamp(resp.getSendPayDate() == null ? System.currentTimeMillis() : resp.getSendPayDate().getTime()),
						resp.getBuyerLogonId());
			} else {
				result = new AlipayResult(resp.getCode(), resp.getMsg(), resp.getSubCode(), resp.getSubMsg());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 支付宝退款申请
	 * @param out_trade_no 商户订单号
	 * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
	 * 
	 * 文档参考地址：
	 * alipay.trade.refund (统一收单交易退款接口)
	 * https://doc.open.alipay.com/docs/api.htm?spm=a219a.7386797.0.0.tq8NDd&docType=4&apiId=759
	 */
	public static RefundResult alipayRefund(String out_trade_no, BigDecimal refund_amount) {
		RefundResult result = null;
		AlipayClient alipayClient = new DefaultAlipayClient(URL, AlipayConfig.app_id, AlipayConfig.alipay_private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key);
		AlipayTradeRefundRequest req = new AlipayTradeRefundRequest();
		req.setBizContent("{" +
				"\"out_trade_no\":\"" + out_trade_no + "\"," +
				"\"refund_amount\":" + refund_amount + "," +
				"\"refund_reason\":\"正常退款\"" +
				"}");
		
		try {
			logger.info("支付宝退款申请请求参数：" + req.getBizContent());
			AlipayTradeRefundResponse resp = alipayClient.execute(req);
			logger.info("支付宝退款申请返回结果：" + resp.getBody());
			System.out.println(resp.getBody());
			if ("10000".equals(resp.getCode())) {
				result = new RefundResult(true, resp.getOutTradeNo(), resp.getTradeNo(), new BigDecimal(resp.getRefundFee()), new Timestamp(resp.getGmtRefundPay().getTime()), resp.getMsg(), resp.getBody());
			} else {
				result = new RefundResult(false, out_trade_no, null, null, null, resp.getMsg() + "(" + resp.getSubMsg() + ")", resp.getBody());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 支付宝退款结果查询
	 * @param out_trade_no 商户订单号
	 * @param out_request_no 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
	 * 文档参考地址：
	 * alipay.trade.fastpay.refund.query (统一收单交易退款查询)
	 * https://doc.open.alipay.com/docs/api.htm?spm=a219a.7395905.0.0.91dl8t&docType=4&apiId=1049
	 */
	public static RefundCheckResult checkAlipayRefundResult(String out_trade_no, String out_request_no) {
		RefundCheckResult result = null;
		AlipayClient alipayClient = new DefaultAlipayClient(URL, AlipayConfig.app_id, AlipayConfig.alipay_private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key);
		AlipayTradeFastpayRefundQueryRequest req = new AlipayTradeFastpayRefundQueryRequest();
		req.setBizContent("{" +
		"\"out_trade_no\":\"" + out_trade_no + "\"," + 
		"\"out_request_no\":\"" + out_request_no + "\"" + 
		"}");
		
		try {
			logger.info("支付宝退款结果查询请求参数：" + req.getBizContent());
			AlipayTradeFastpayRefundQueryResponse resp = alipayClient.execute(req);
			logger.info("支付宝退款结果查询返回结果：" + resp.getBody());
			System.out.println(resp.getBody());
			if ("10000".equals(resp.getCode())) {
				result = new RefundCheckResult(true, resp.getMsg(), resp.getBody());
			} else {
				result = new RefundCheckResult(false, resp.getMsg() + "(" + resp.getSubMsg() + ")", resp.getBody());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 对账单下载
	 * @param bill_date
	 * @return
	 */
	public static List<AlipayBillVo> downloadBill(String bill_date) {
		List<AlipayBillVo> list = new ArrayList<AlipayBillVo>();
		//页号
		String page_no = "1";
		//必填，必须是正整数

		//账务查询开始时间
		String gmt_start_time = bill_date + " 00:00:00";
		//格式为：yyyy-MM-dd HH:mm:ss

		//账务查询结束时间
		String gmt_end_time = bill_date + " 23:59:59";
		//格式为：yyyy-MM-dd HH:mm:ss

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service_account_page_query);
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("page_no", page_no);
		sParaTemp.put("gmt_start_time", gmt_start_time);
		sParaTemp.put("gmt_end_time", gmt_end_time);

		// 建立请求
		String sHtmlText = "";
		try {
			sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("------------------------------------------");
		System.out.println(sHtmlText);
		System.out.println("------------------------------------------");
		String json = XmlUtils.xml2Json(sHtmlText);
		JSONObject result = JSONObject.fromObject(json);
		
		if ("T".equals(result.optString("is_success"))) {
			JSONObject responseObj = result.getJSONObject("response");
			JSONObject account_page_query_result = responseObj.getJSONObject("account_page_query_result");
//			String has_next_page = account_page_query_result.optString("has_next_page");//T or F
			Object logList = account_page_query_result.get("account_log_list");
			JSONArray account_log_list = null;
			if (logList instanceof JSONArray) {
				System.out.println("是JSONArray：" + logList);
				account_log_list = (JSONArray)logList;
			} else if (logList instanceof JSONObject) {
				System.out.println("是JSONObject：" + logList);
				account_log_list = new JSONArray();
				JSONObject account_log_list_obj = account_page_query_result.getJSONObject("account_log_list");
				if (account_log_list_obj != null) {
					account_log_list.add(account_log_list_obj.getJSONObject("AccountQueryAccountLogVO"));
				}
			}
			
			for (int i = 0; i < account_log_list.size(); i++) {
				JSONObject obj = account_log_list.getJSONObject(i);
				
				AlipayBillVo vo = new AlipayBillVo();
				vo.setBalance(obj.optString("balance"));
				vo.setBuyer_account(obj.optString("buyer_account"));
				vo.setCurrency(obj.optString("currency"));
				vo.setDeposit_bank_no(obj.optString("deposit_bank_no"));
				vo.setGoods_title(obj.optString("goods_title"));
				vo.setIncome(obj.optString("income"));
				vo.setIw_account_log_id(obj.optString("iw_account_log_id"));
				vo.setMemo(obj.optString("memo"));
				vo.setMerchant_out_order_no(obj.optString("merchant_out_order_no"));
				vo.setOutcome(obj.optString("outcome"));
				vo.setPartner_id(obj.optString("partner_id"));
				vo.setRate(obj.optString("rate"));
				vo.setSeller_account(obj.optString("seller_account"));
				vo.setSeller_fullname(obj.optString("seller_fullname"));
				vo.setService_fee(obj.optString("service_fee"));
				vo.setService_fee_ratio(obj.optString("service_fee_ratio"));
				vo.setSign_product_name(obj.optString("sign_product_name"));
				vo.setSub_trans_code_msg(obj.optString("sub_trans_code_msg"));
				vo.setTotal_fee(obj.optString("total_fee"));
				vo.setTrade_no(obj.optString("trade_no"));
				vo.setTrade_refund_amount(obj.optString("trade_refund_amount"));
				vo.setTrans_code_msg(obj.optString("trans_code_msg"));
				vo.setTrans_date(DvDateHelper.getTimestamp(obj.optString("trans_date")));
				vo.setCheck_status("0");
				System.out.println(vo);
				list.add(vo);
			}
			
		}
		return list;
	}
	
	/**
	 * 支付宝授权获取用户信息
	 * @param auth_code 用户授权码
	 * 
	 * 文档参考地址：
	 * alipay.system.oauth.token(获取会员信息快速接入)
	 * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.23VqNc&treeId=220&articleId=105337&docType=1
	 */
	public static AlipayUserInfo alipayAuth(String auth_code) {
		AlipayUserInfo result = null;
		AlipayClient alipayClient = new DefaultAlipayClient(URL, AlipayConfig.app_id, AlipayConfig.alipay_private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key);
		AlipaySystemOauthTokenRequest req = new AlipaySystemOauthTokenRequest();
		req.setCode(auth_code);
		req.setGrantType("authorization_code");
		
		try {
			logger.info("支付宝授权获取access_token请求参数：" + req.getCode());
		    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(req);
		    logger.info("支付宝授权获取access_token返回结果：" + oauthTokenResponse.getBody());
		    System.out.println(oauthTokenResponse.getBody());
		    String access_token = oauthTokenResponse.getAccessToken();
		    if (oauthTokenResponse.isSuccess()) {
		    	AlipayUserUserinfoShareRequest userInfoReq = new AlipayUserUserinfoShareRequest();
		    	try {
					logger.info("支付宝授权获取用户信息请求参数：" + access_token);
		    		AlipayUserUserinfoShareResponse userinfoResp = alipayClient.execute(userInfoReq, access_token);
		    		logger.info("支付宝授权获取用户信息返回结果：" + userinfoResp.getBody());
		    		System.out.println(userinfoResp.getBody());
		    		if (userinfoResp.isSuccess()) {
		    			if ("T".equals(userinfoResp.getIsCertified())) {
		    				
		    			}
		    			String avatar = userinfoResp.getAvatar();
		    			String real_name = userinfoResp.getRealName();
		    			String nick_name = userinfoResp.getNickName();
		    			String email = userinfoResp.getEmail();
		    			String mobile = userinfoResp.getMobile();
		    			
		    			result = new AlipayUserInfo(avatar, real_name, nick_name, email, mobile, userinfoResp.getIsCertified());
		    		}
		    		
		    	} catch (AlipayApiException e) {
		    		//处理异常
		    		e.printStackTrace();
		    	}
		    }
		} catch (AlipayApiException e) {
		    //处理异常
		    e.printStackTrace();
		}
		
		return result;
	}
	
}
