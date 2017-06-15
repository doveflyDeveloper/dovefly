package com.deertt.utils.helper;

import java.util.HashMap;
import java.util.Map;

import com.deertt.frame.base.project.ApplicationConfig;

public class WechatHelper {

	public static void main(String[] args) {
		sendNofification(144);
	}

	/**
	 * 发送微信通知消息
	 * @param id
	 * @return 
	 */
	public static String sendNofification(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.put("Accept", "application/json;charset=utf-8");
		return DvHttpHelper.postForm(ApplicationConfig.NOTIFICATION_SEND_URL, params, headers);
	}

}
