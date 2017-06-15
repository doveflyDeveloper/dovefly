package com.deertt.frame.base.project;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

	final static public Properties props = new Properties();

	static {
		InputStream in = ApplicationConfig.class.getClassLoader().getResourceAsStream("application-config.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//项目运行环境
	final public static String PROJECT_ENVIRONMENT = getProperty("Project.environment");
	
	//网站根路径
	final public static String PROJECT_CONTEXT_PATH = getProperty("Project.context_path");
	
	//文件导入关键词
	final public static String FILE_IMPORT_KEY = "file_import_key";
	
	//图片引用跟URL
	final public static String FILE_ROOT_URL_IMAGE = getProperty("FileConfig.file_root_url_image");
	
	//图片上传根路径
	final public static String FILE_ROOT_PATH_IMAGE = getProperty("FileConfig.file_root_path_image");
	
	//导入数据文件上传根路径
	final public static String FILE_ROOT_PATH_IMPORT = getProperty("FileConfig.file_root_path_import");
	
	//附件文件上传根路径
	final public static String FILE_ROOT_PATH_ATTACHMENT = getProperty("FileConfig.file_root_path_attachment");
	
	//图表下载临时目录根路径
	final public static String FILE_ROOT_PATH_SVG = getProperty("FileConfig.file_root_path_svg");
	
	//支付宝支付配置信息
	//支付宝分配给开发者的应用ID
	final public static String ALIPAYCONFIG_APP_ID = getProperty("AlipayConfig.app_id");
	
	final public static String ALIPAY_PUBLIC_KEY = getProperty("AlipayConfig.alipay_public_key");
	
	final public static String ALIPAY_PRIVATE_KEY = getProperty("AlipayConfig.alipay_private_key");
	
	//合作身份者ID，以2088开头由16位纯数字组成的字符串
	final public static String ALIPAYCONFIG_PARTNER = getProperty("AlipayConfig.partner");

	//收款支付宝账号，一般情况下收款账号就是签约账号
	final public static String ALIPAYCONFIG_SELLER_EMAIL = getProperty("AlipayConfig.seller_email");

	//账户名，个人支付宝账号是真实姓名 公司支付宝账号是公司名称（上海辰梁农业科技发展有限公司）
	final public static String ALIPAYCONFIG_SELLER_NAME = getProperty("AlipayConfig.seller_name");

	//商户的私钥
	final public static String ALIPAYCONFIG_KEY = getProperty("AlipayConfig.key");

	//调试用，创建TXT日志文件夹路径
	final public static String ALIPAYCONFIG_LOG_PATH = getProperty("AlipayConfig.log_path");
	
	//微信支付配置信息
	//证书存放位置
	final public static String WEIXINPAY_CERT_FILE_PATH = getProperty("WeixinPay.cert_file_path");
	//秘钥
	final public static String WEIXINPAY_PARTNER_KEY = getProperty("WeixinPay.partner_key");
	//公众账号ID
	final public static String WEIXINPAY_APP_ID = getProperty("WeixinPay.app_id");
	//商户号
	final public static String WEIXINPAY_MCH_ID = getProperty("WeixinPay.mch_id");
	//IP地址
	final public static String WEIXINPAY_ADMIN_DEERTT_IP = getProperty("WeixinPay.admin_deertt_ip");
	//接口调用方式
	final public static String WEIXINPAY_TRADE_TYPE = getProperty("WeixinPay.trade_type");
	
	//云片网短信发送用户KEY
	final public static String YUNPIAN_API_KEY = getProperty("Yunpian.api_key");
	//云片网短信发送URL
	final public static String YUNPIAN_SEND_SMS_URL = getProperty("Yunpian.send_sms_url");
	
	//小鹿汀汀微信发送URL
	final public static String NOTIFICATION_SEND_URL = getProperty("deertt.notification_send_url");
	
	//更新支付状态接口
	final public static String UPDATE_PURCHASE_PAY_URL = getProperty("deertt.update_purchase_pay_url");
	
	//更新支付状态接口
	final public static String UPDATE_TRADE_PAY_URL = getProperty("deertt.update_trade_pay_url");
	
	//小鹿微信系统二维码生成URL
	final public static String DEERTT_QRCODE_URL = getProperty("deertt.qrcode_url");
	
	public static String getProperty(String key){
		return props.getProperty(key);
	}
	
	public static String getProperty(String key, String defaultValue){
		return props.getProperty(key, defaultValue);
	}
	
	public static boolean isProduction() {
		return "production".equals(PROJECT_ENVIRONMENT);
	}
	
	public static void main(String[] args) {
		System.out.println(props);
	}
	
}
