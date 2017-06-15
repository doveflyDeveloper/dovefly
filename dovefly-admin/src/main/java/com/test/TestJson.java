package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deertt.utils.helper.DvHttpHelper;


public class TestJson {

	public static void main(String[] args) {
		
//		aliyunBarcodeSearch("06917878036526");
//		asListTest();
//		removeListTest();
		
		System.out.println(gcd(45, 18));
	}
	
	public static void aliyunBarcodeSearch(String barcode) {
	    String url = "http://jisutxmcx.market.alicloudapi.com/barcode2/query";
	    Map<String, Object> headers = new HashMap<String, Object>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE 0b2c06ce404a4184ae2bafeacce5c197");
	    Map<String, Object> querys = new HashMap<String, Object>();
	    querys.put("barcode", barcode);

	    try {
	    	String response = DvHttpHelper.get(url, querys, headers);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void asListTest() {
	    int[] a = {1,2,3,4,5};
	    System.out.println(Arrays.asList(a).size());
	    
	    Integer[] b = {1,2,3,4,5};
	    System.out.println(Arrays.asList(b).size());
	}
	
	public static void removeListTest() {
	    List<Integer> datas = new ArrayList<Integer>(100);
	    for (int i = 0; i < 100; i++) {
	    	datas.add(i);
	    }
	    
	    for (int i = 0, size = datas.size(); i < size; i++) {
	    	if (i >= 20 && i < 30) {
	    		datas.remove(i);
	    	}
	    }
	    
	    System.out.println(datas);
	    
	}
	
	/**
	 * 最大公约数
	 * @param p
	 * @param q
	 * @return
	 */
	public static int gcd(int p, int q) {
	   if (q == 0) return p;
	   int r = p % q;
	   return gcd(q, r);
	}

}
