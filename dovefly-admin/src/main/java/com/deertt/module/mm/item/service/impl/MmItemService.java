package com.deertt.module.mm.item.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.item.dao.IMmItemDao;
import com.deertt.module.mm.item.service.IMmItemService;
import com.deertt.module.mm.item.util.IMmItemConstants;
import com.deertt.module.mm.item.vo.MmItemVo;
import com.deertt.utils.helper.DvHttpHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class MmItemService extends DvBaseService<IMmItemDao, MmItemVo, Integer> implements IMmItemService, IMmItemConstants {

	@Override
	public MmItemVo findByBarcode(String barcode) {
		return getDao().findByCondition("barcode = '" + barcode + "'");
	}
	
	/**
	 * 
	 * 
	 * https://market.aliyun.com/products/56928004/cmapi011806.html?spm=5176.730005.0.0.zaUoDy#sku=yuncode580600000
	 */
	public boolean checkExistsBarcodeFromInternet(String barcode) {
		boolean ret = false;
	    String url = "http://jisutxmcx.market.alicloudapi.com/barcode2/query";
	    Map<String, Object> headers = new HashMap<String, Object>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE 0b2c06ce404a4184ae2bafeacce5c197");
	    Map<String, Object> querys = new HashMap<String, Object>();
	    querys.put("barcode", barcode);
        String json = null;
	    try {
	    	json = DvHttpHelper.get(url, querys, headers);
	    	System.out.println(json.toString());
	    	JSONObject js = JSONObject.fromObject(json);
	    	if ("0".equals(js.optString("status", null))) {
	    		ret = true;
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return ret;
	}
}
