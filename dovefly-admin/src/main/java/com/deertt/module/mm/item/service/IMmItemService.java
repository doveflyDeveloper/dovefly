package com.deertt.module.mm.item.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.mm.item.dao.IMmItemDao;
import com.deertt.module.mm.item.vo.MmItemVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMmItemService extends IDvBaseService<IMmItemDao, MmItemVo, Integer> {

	public MmItemVo findByBarcode(String barcode);
	
	public boolean checkExistsBarcodeFromInternet(String barcode);
	
}
