package com.test;

import java.util.HashMap;
import java.util.Map;

import com.deertt.utils.helper.DvHttpHelper;

/*
 * 作者：cslience
 * 功能：十进制转换成任意进制的方法
 * 求余数将其存入数组中
 * 
 */
public class ShortURLTest {

	public static void main(String[] args) {
		//百度短地址，本域名不支持，猜测可能域名未备案
//		String url = "http://dwz.cn/create.php";
//		String longUrl = "http://open.jie-ci.com/car/20000202/456789123";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("url", longUrl);
//		String result = DvHttpHelper.post(url, params);
//		System.out.println(result);
		
		//新浪短地址，需注册开发者账号，然后创建一个应用（需审核），通过应用ID调用
//		String url = "http://api.t.sina.com.cn/short_url/shorten.json";
//		String longUrl = "http://open.jie-ci.com/car/20000202/456789123";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("source", "3271760578");
//		params.put("url_long", longUrl);
//		String result = DvHttpHelper.get(url, params, null);
//		System.out.println(result);
		
		//六度短地址，目前没有发现有什么限制，可靠性待考察
		String url = "http://6du.in/";
		String longUrl = "http://open.jie-ci.com/car/20000202/456789123";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("is_api", "1");
		params.put("lurl", longUrl);
		String result = DvHttpHelper.get(url, params, null);
		System.out.println(result);
		
	}

}