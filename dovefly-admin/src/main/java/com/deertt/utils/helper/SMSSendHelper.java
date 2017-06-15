package com.deertt.utils.helper;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.deertt.frame.base.project.ApplicationConfig;

public class SMSSendHelper {
	
	//
	final public static String TEMPLATE_TASK_NOTIFY = "【小鹿汀汀】#name#你好，您申请的店长账户已通过审核，分配给您的账号如下（账号：#account#，密码：#password#），请及时登录后台管理系统修改您的密码！（如非本人操作，请忽略本消息）";
	
	//
	final public static String TEMPLATE_GET_BACK_PASSWORD = "【小鹿汀汀】正在找回密码，您的验证码是#code#";
	
	//
	final public static String TEMPLATE_VERIFY_CODE = "【小鹿汀汀】亲爱的#name#，您的验证码是#code#。如非本人操作，请忽略本短信";
	

	public static void main(String[] args) {

	}
	
	public static boolean sendTaskNotifySms(String mobile, String content) {
		String sms = TEMPLATE_TASK_NOTIFY.replaceAll("#content#", content);
		return sendSms(mobile, sms);
	}
	
	public static boolean sendGetBackPasswordSms(String mobile, String code) {
		String sms = TEMPLATE_GET_BACK_PASSWORD.replaceAll("#code#", code);
		return sendSms(mobile, sms);
	}
	
	public static boolean sendVerifyCodeSms(String mobile, String name, String code) {
		String sms = TEMPLATE_VERIFY_CODE.replaceAll("#name#", name).replaceAll("#code#", code);
		return sendSms(mobile, sms);
	}

	public static boolean sendSms(String mobile, String text) {
		// 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
		// String text = "【云片网】您的验证码是1234";
		// 发短信调用示例
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("apikey", ApplicationConfig.YUNPIAN_API_KEY);
		params.put("text", text);
		params.put("mobile", mobile);
		String result = DvHttpHelper.post(ApplicationConfig.YUNPIAN_SEND_SMS_URL, params);
		//{"code":0,"msg":"OK","result":{"count":1,"fee":2,"sid":5736220558}}
		JSONObject json = JSONObject.parseObject(result);
		return json.getInteger("code") == 0;
	}

}
