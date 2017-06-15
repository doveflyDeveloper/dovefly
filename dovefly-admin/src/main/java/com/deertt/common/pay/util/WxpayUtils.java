package com.deertt.common.pay.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.deertt.common.pay.vo.PayCheckResult;
import com.deertt.common.pay.vo.PayResult;
import com.deertt.common.pay.vo.RefundCheckResult;
import com.deertt.common.pay.vo.RefundResult;
import com.deertt.common.pay.vo.WxpayResult;
import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.pay.wxbill.vo.WxBillVo;
import com.deertt.utils.helper.date.DvDateHelper;

public class WxpayUtils {
	
	public static Logger logger = Logger.getLogger(WxpayUtils.class);
	
	//证书存放位置
	public static final String CERT_FILE_PATH = ApplicationConfig.WEIXINPAY_CERT_FILE_PATH;
	
	//秘钥
	public static final String PARTNER_KEY = ApplicationConfig.WEIXINPAY_PARTNER_KEY;
	
	//公众账号ID
	public static final String APP_ID = ApplicationConfig.WEIXINPAY_APP_ID;
	
	//商户号
	public static final String MCH_ID = ApplicationConfig.WEIXINPAY_MCH_ID;
	
	//IP地址
	public static final String ADMIN_DEERTT_IP = ApplicationConfig.WEIXINPAY_ADMIN_DEERTT_IP;
	
	//调用方式
	public static final String TRADE_TYPE = ApplicationConfig.WEIXINPAY_TRADE_TYPE;
	
	//统一下单URL
	public static final String WEIXIN_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//查询订单URL
	public static final String WEIXIN_ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	//关闭订单URL
	public static final String WEIXIN_CLOSEORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	//申请退款URL
	public static final String WEIXIN_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	//查询退款URL
	public static final String WEIXIN_REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	//下载对账单
	public static final String WEIXIN_DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	
	//企业付款到用户URL
	public static final String WEIXIN_TRANSFERS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	//企业付款到用户URL
	public static final String WEIXIN_GETTRANSFERINFO_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("out_trade_no", "160128000024");
		paramMap.put("total_fee", "1");
		paramMap.put("body", "士大夫士大夫");
		paramMap.put("notify_url", ApplicationConfig.PROJECT_CONTEXT_PATH + "/pay/weixinPayController/payNotify/purchase/683450");
		paramMap.put("product_id", "12345");
		
		//////////////////////////////////////////////////////////////////

		
	}
	
	/**
	 * 微信扫描支付（微信统一下单接口）
	 * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
	 * @param out_trade_no
	 * @param total_fee 付款金额(分)
	 * @param body
	 * @param product_id
	 * @param notify_url
	 * @return
	 */
	public static PayResult unifiedOrder(String out_trade_no, Integer total_fee, String body, String product_id, String notify_url) {
		PayResult result = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", APP_ID);
		params.put("mch_id", MCH_ID);
		params.put("trade_type", TRADE_TYPE);
		params.put("spbill_create_ip", ADMIN_DEERTT_IP);
		params.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		
		params.put("out_trade_no", out_trade_no);
		params.put("total_fee", total_fee.toString());
		params.put("body", body);
		params.put("product_id", product_id);
		params.put("notify_url", notify_url);
		
		params.put("sign", getSign(params));//签名
		String paramXml = parseMap2Xml(params);
		logger.info("微信支付请求参数：" + paramXml);
		String retXml = postXml(WEIXIN_UNIFIEDORDER_URL, paramXml, false);
		logger.info("微信支付返回结果：" + retXml);
		Map<String, String> retMap = parseXml2Map(retXml);
		if ("SUCCESS".equals(retMap.get("return_code"))) {
			if ("SUCCESS".equals(retMap.get("result_code"))) {
				result = new PayResult(true, out_trade_no, retMap.get("code_url"), new BigDecimal(total_fee / 100.0), DvDateHelper.getSysTimestamp(), retMap.get("return_msg"), retMap.toString());
			} else {
				result = new PayResult(false, out_trade_no, null, null, null, retMap.get("err_code_des"), retMap.toString());
			}
		} else {
			result = new PayResult(false, out_trade_no, null, null, null, retMap.get("return_msg"), retMap.toString());
		}
		return result;
	}
	
	/**
	 * 微信支付订单状态查询接口
	 * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
	 * @param out_trade_no 商户订单号
	 * @return
	 */
	public static WxpayResult orderQuery(String out_trade_no) {
		WxpayResult result = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", APP_ID);
		params.put("mch_id", MCH_ID);
		params.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("out_trade_no", out_trade_no);
		
		params.put("sign", getSign(params));//签名
		String paramXml = parseMap2Xml(params);
		logger.info("微信订单结果查询请求参数：" + paramXml);
		String retXml = postXml(WEIXIN_ORDERQUERY_URL, paramXml, false);
		logger.info("微信订单结果查询返回结果：" + retXml);
		Map<String, String> retMap = parseXml2Map(retXml);
		
		if ("SUCCESS".equals(retMap.get("return_code"))) {
			if ("SUCCESS".equals(retMap.get("result_code"))) {
				result = new WxpayResult(
						retMap.get("return_code"),
						retMap.get("return_msg"),
						retMap.get("result_code"),
						retMap.get("err_code"),
						retMap.get("err_code_des"),
						retMap.get("trade_state"),
						new BigDecimal(retMap.get("total_fee") == null ? "0" : retMap.get("total_fee")).divide(new BigDecimal(100)),
						retMap.get("transaction_id"),
						retMap.get("out_trade_no"),
						DvDateHelper.getTimestamp(retMap.get("time_end")));
			} else {
				result = new WxpayResult(retMap.get("return_code"), retMap.get("return_msg"), retMap.get("result_code"), retMap.get("err_code"), retMap.get("err_code_des"));
			}
		} else {
			result = new WxpayResult(retMap.get("return_code"), retMap.get("return_msg"));
		}
		return result;
	}
	
	/**
	 * 微信申请退款接口
	 * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
	 * @param out_refund_no 商户退款单号
	 * @param out_trade_no 商户订单号
	 * @param total_fee 订单总金额，单位为分
	 * @param refund_fee 退款总金额，单位为分
	 * @return
	 */
	public static RefundResult refund(String out_refund_no, String out_trade_no, Integer total_fee, Integer refund_fee) {
		RefundResult result = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", APP_ID);
		params.put("mch_id", MCH_ID);
		params.put("op_user_id", MCH_ID);
		params.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		
		params.put("out_refund_no", out_refund_no);
		params.put("out_trade_no", out_trade_no);
		params.put("total_fee", total_fee.toString());
		params.put("refund_fee", refund_fee.toString());
		params.put("refund_account", "REFUND_SOURCE_RECHARGE_FUNDS");
		
		params.put("sign", getSign(params));//签名
		String paramXml = parseMap2Xml(params);
		logger.info("微信退款申请请求参数：" + paramXml);
		String retXml = postXml(WEIXIN_REFUND_URL, paramXml, true);
		logger.info("微信退款申请返回结果：" + retXml);
		Map<String, String> retMap = parseXml2Map(retXml);
		if ("SUCCESS".equals(retMap.get("return_code"))) {
			if ("SUCCESS".equals(retMap.get("result_code"))) {
				result = new RefundResult(true, retMap.get("out_trade_no"), retMap.get("refund_id"), new BigDecimal(Integer.valueOf(retMap.get("refund_fee")) / 100.0), DvDateHelper.getSysTimestamp(), retMap.get("return_msg"), retMap.toString());
			} else {
				result = new RefundResult(false, out_trade_no, null, null, null, retMap.get("err_code_des"), retMap.toString());
			}
		} else {
			result = new RefundResult(false, out_trade_no, null, null, null, retMap.get("return_msg"), retMap.toString());
		}
		return result;
	}
	
	/**
	 * 退款状态查询接口
	 * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
	 * @param out_trade_no 商户订单号
	 * @return
	 */
	public static RefundCheckResult refundquery(String out_trade_no) {
		RefundCheckResult result = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", APP_ID);
		params.put("mch_id", MCH_ID);
		params.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("out_trade_no", out_trade_no);
		
		params.put("sign", getSign(params));//签名
		String paramXml = parseMap2Xml(params);
		logger.info("微信退款结果查询请求参数：" + paramXml);
		String retXml = postXml(WEIXIN_REFUNDQUERY_URL, paramXml, false);
		logger.info("微信退款结果查询返回结果：" + retXml);
		Map<String, String> retMap = parseXml2Map(retXml);
		
		if ("SUCCESS".equals(retMap.get("return_code"))) {
			if ("SUCCESS".equals(retMap.get("result_code"))) {
//				退款状态	refund_status_$n
//				SUCCESS—退款成功；FAIL—退款失败；PROCESSING—退款处理中；NOTSURE—未确定，需要商户原退款单号重新发起
//				CHANGE—转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
				String refund_status_0 = retMap.get("refund_status_0");
				if ("SUCCESS".equals(refund_status_0) || "PROCESSING".equals(refund_status_0)) {//处理中的暂时默认成功
					result = new RefundCheckResult(true, refund_status_0, retMap.toString());
				} else {//FAIL	NOTSURE	CHANGE
					result = new RefundCheckResult(false, retMap.get("err_code_des"), retMap.toString());
				}
			} else {
				result = new RefundCheckResult(false, retMap.get("err_code_des"), retMap.toString());
			}
		} else {
			result = new RefundCheckResult(false, retMap.get("return_msg"), retMap.toString());
		}
		return result;
	}
	
	/**
	 * 企业向微信用户个人付款接口，目前支持向指定微信用户的openid付款
	 * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
	 * @param partner_trade_no 商户订单号
	 * @param openid 用户openid
	 * @param re_user_name 收款用户真实姓名
	 * @param amount 企业付款金额 单位：分
	 * @param desc 企业付款描述信息
	 * @return
	 * @throws Exception
	 */
	public static PayResult transfers(String partner_trade_no, String openid, String re_user_name, Integer amount, String desc) throws Exception {
		PayResult result = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("mch_appid", APP_ID);
		params.put("mchid", MCH_ID);
		params.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("spbill_create_ip", ADMIN_DEERTT_IP);
		
		params.put("partner_trade_no", partner_trade_no);
		params.put("openid", openid);
		params.put("check_name", "OPTION_CHECK");
		params.put("re_user_name", re_user_name);
		params.put("amount", amount.toString());//转换为单位分，整数
		params.put("desc", desc);
		
		params.put("sign", getSign(params));// 签名
		String paramXml = parseMap2Xml(params);
		logger.info("微信付款请求参数：" + paramXml);
		String retXml = postXml(WEIXIN_TRANSFERS_URL, paramXml, true);
		logger.info("微信付款返回结果：" + retXml);
		Map<String, String> retMap = parseXml2Map(retXml);
		
		if ("SUCCESS".equals(retMap.get("return_code"))) {
			if ("SUCCESS".equals(retMap.get("result_code"))) {
				result = new PayResult(true, retMap.get("partner_trade_no"), retMap.get("payment_no"), new BigDecimal(amount / 100.0), DvDateHelper.getTimestamp(retMap.get("payment_time")), retMap.get("return_msg"), retMap.toString());
			} else {
				result = new PayResult(false, partner_trade_no, null, null, null, retMap.get("err_code_des"), retMap.toString());
			}
		} else {
			result = new PayResult(false, partner_trade_no, null, null, null, retMap.get("return_msg"), retMap.toString());
		}
		
		return result;
	}
	
	/**
	 * 商户的企业付款操作进行结果查询，返回付款操作详细结果
	 * https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3
	 * @param partner_trade_no
	 * @return
	 */
	public static PayCheckResult getTransferInfo(String partner_trade_no) {
		PayCheckResult result = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", APP_ID);
		params.put("mch_id", MCH_ID);
		params.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		
		params.put("partner_trade_no", partner_trade_no);
		
		params.put("sign", getSign(params));//签名
		String paramXml = parseMap2Xml(params);
		
		logger.info("微信付款结果查询请求参数：" + paramXml);
		String retXml = postXml(WEIXIN_GETTRANSFERINFO_URL, paramXml, true);
		logger.info("微信付款结果查询返回结果：" + retXml);
		Map<String, String> retMap = parseXml2Map(retXml);
		
		if ("SUCCESS".equals(retMap.get("return_code"))) {
			if ("SUCCESS".equals(retMap.get("result_code"))) {
				
				//转账状态	status	是	SUCCESS	string(16)	
				//SUCCESS:转账成功
				//FAILED:转账失败
				//PROCESSING:处理中
				String status = retMap.get("status");
				
				if ("SUCCESS".equals(status) || "PROCESSING".equals(status)) {//处理中的暂时默认成功
					result = new PayCheckResult(true, retMap.get("reason"), retMap.toString());
				} else {//FAIL	NOTSURE	CHANGE
					result = new PayCheckResult(false, retMap.get("err_code_des"), retMap.toString());
				}
			} else {
				result = new PayCheckResult(false, retMap.get("err_code_des"), retMap.toString());
			}
		} else {
			result = new PayCheckResult(false, retMap.get("return_msg"), retMap.toString());
		}
		
		return result;
	}
	
	/**
	 * 下载某一天的对账单
	 * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
	 * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
	 * 3、对账单中涉及金额的字段单位为“元”。
	 * 4、对账单接口只能下载三个月以内的账单。
	 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6
	 * @param bill_date yyyyMMdd
	 * @return
	 */
	public static List<WxBillVo> downloadBill(String bill_date) {
		List<WxBillVo> list = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", APP_ID);
		params.put("mch_id", MCH_ID);
		params.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		
		params.put("bill_date", bill_date);
		params.put("bill_type", "ALL");//ALL，返回当日所有订单信息，默认值		SUCCESS，返回当日成功支付的订单		REFUND，返回当日退款订单
		
		params.put("sign", getSign(params));//签名
		String paramXml = parseMap2Xml(params);
		logger.info("对账单下载请求参数：" + paramXml);
		String retXml = postXml(WEIXIN_DOWNLOADBILL_URL, paramXml, false);
		logger.info("对账单下载返回结果：" + retXml);
		
		if (!retXml.startsWith("<xml>")) {//成功
			list = new ArrayList<WxBillVo>();
			String data = retXml;//\r\n
			String[] datas = data.split("\\\r\\\n");
			for (int i = 0; i < datas.length; i++) {
				if (i > 0 && i < datas.length - 2) {//第一行是标题行，最后两行是汇总行，都忽略
					String[] fields = datas[i].substring(1).split(",`");
					
					WxBillVo vo = new WxBillVo();
					vo.setTrade_time(DvDateHelper.getTimestamp(fields[0]));
					vo.setGh_id(fields[1]);
					vo.setMch_id(fields[2]);
					vo.setSub_mch(fields[3]);
					vo.setDevice_id(fields[4]);
					vo.setWx_order(fields[5]);
					vo.setBz_order(fields[6]);
					vo.setOpen_id(fields[7]);
					vo.setTrade_type(fields[8]);
					vo.setTrade_status(fields[9]);
					vo.setBank(fields[10]);
					vo.setCurrency(fields[11]);
					vo.setTotal_money(fields[12]);
					vo.setRed_packet_money(fields[13]);
					vo.setWx_refund(fields[14]);
					vo.setBz_refund(fields[15]);
					vo.setRefund_money(fields[16]);
					vo.setRed_packet_refund(fields[17]);
					vo.setRefund_type(fields[18]);
					vo.setRefund_status(fields[19]);
					vo.setProduct_name(fields[20]);
					vo.setBz_data_packet(fields[21]);
					vo.setFee(fields[22]);
					vo.setRate(fields[23]);
					vo.setCheck_status("0");
					list.add(vo);
				}
			}
		}
		return list;
	}
	
	/**
	 * 将Map内的数据以key的字典序排序并拼接成字符串（如果参数值为空或空值则忽略此参数），如："key1=value1&key2=value2"
	 * @param map
	 * @return
	 */
	public static String sortParamsByAscii(Map<String, String> map) {
		String ret = "";
		if (map != null) {
			String[] keys = map.keySet().toArray(new String[0]);
			Arrays.sort(keys);
			for (int i = 0; i < keys.length; i++) {
				if (StringUtils.isNotEmpty(map.get(keys[i]))) {
					ret += "&" + keys[i] + "=" + map.get(keys[i]);
				}
			}
			if (ret.length() > 0) {
				ret = ret.substring(1);
			}
		}
		System.out.println("SortParamsByAscii=" + ret);
		return ret;
	}
	
	/**
	 * 根据Map内数据生成签名
	 * @param map
	 * @return
	 */
	public static String getSign(Map<String, String> map) {
		String ret = sortParamsByAscii(map) + "&key=" + WxpayUtils.PARTNER_KEY;
		try {
			ret = DigestUtils.md5Hex(ret.getBytes("utf-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("getSign=" + ret);
		return ret;
	}
	
	/**
	 * 将Map数据解析为XML格式
	 * @param map
	 * @return
	 */
	public static String parseMap2Xml(Map<String, String> map) {
		String ret = "";
		if (map != null) {
			ret += "<xml>";
			for (Map.Entry<String, String> entry : map.entrySet()) {
				ret += "<" + entry.getKey() + ">" + (entry.getValue() == null ? "" : entry.getValue()) + "</" + entry.getKey() + ">";
			}
			ret += "</xml>";
		}
		System.out.println("parseMap2Xml=" + ret);
		return ret;
	}
	
	/**
	 * 将XML数据解析为Map
	 * @param map
	 * @return
	 */
	public static Map<String, String> parseXml2Map(String xml) {
		Map<String, String> ret = null;
		if (xml != null) {
			ret = new HashMap<String, String>();
			//创建一个新的字符串
			StringReader reader = new StringReader(xml);
			SAXReader saxReader = new SAXReader();
			try {
				Document document = saxReader.read(reader);
				Element rootEle = document.getRootElement();
				for (Iterator i = rootEle.elementIterator(); i.hasNext();) {
					Element node = (Element) i.next();
					ret.put(node.getName(), node.getText());
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		System.out.println("parseXml2Map=" + ret);
		return ret;
	}

	/**
	 * 发送xml数据请求到server端，返回数据也为XML
	 * 
	 * @param url
	 *            xml请求数据地址
	 * @param xmlStr
	 *            发送的xml数据流
	 * @param userCert
	 *            是否使用证书
	 * @return 
	 */
	public static String postXml(String url, String xmlStr, boolean userCert) {
		String ret = null;
		
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("Content-Type", "text/xml");
		httppost.addHeader("charset", "utf-8");
		httppost.setEntity(new StringEntity(xmlStr, "utf-8"));
		
		// 创建httpclient工具对象
		CloseableHttpClient httpclient = null;
		
		if (userCert) {
			try {
				// 指定读取证书格式为PKCS12
				KeyStore keyStore = KeyStore.getInstance("PKCS12");
				// 读取本机存放的PKCS12证书文件
				FileInputStream instream = new FileInputStream(new File(CERT_FILE_PATH));
				// 指定PKCS12的密码(商户ID)
				keyStore.load(instream, MCH_ID.toCharArray());
				SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCH_ID.toCharArray()).build();
				// 指定TLS版本
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
						sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
				// 设置httpclient的SSLSocketFactory
				httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			httpclient = HttpClients.createDefault();
		}
		
		try {
			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			ret = EntityUtils.toString(entity, "utf-8");//取出应答字符串
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("postXml=" + ret);
		return ret;
	}
	
	/**
	 * 将数据流转为字符串
	 * @param is
	 * @return
	 */
	public static String inputStream2String(InputStream is) {
		String ret = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			byte[] bytes = new byte[1024];
			bos = new ByteArrayOutputStream();
			int count = 0;
			while ((count = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, count);
			}
			byte[] strByte = bos.toByteArray();
			ret = new String(strByte, 0, strByte.length, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("inputStream2String=" + ret);
		return ret;
	}
}
