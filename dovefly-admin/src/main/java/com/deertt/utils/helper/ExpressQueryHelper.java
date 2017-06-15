package com.deertt.utils.helper;

import com.deertt.common.express.KdniaoTrackQueryAPI;

public class ExpressQueryHelper {

	public static void main(String[] args) {
		String result = queryExpressProgress("ZTO", "719438612124");
		System.out.print(result);
	}
	
	public static String queryExpressProgress(String expCode, String expNo) {
		String result = "";
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		try {
			result = api.getOrderTracesByJson(expCode, expNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
