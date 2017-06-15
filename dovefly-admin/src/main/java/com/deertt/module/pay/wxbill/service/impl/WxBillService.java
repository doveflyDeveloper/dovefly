package com.deertt.module.pay.wxbill.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.pay.wxbill.dao.IWxBillDao;
import com.deertt.module.pay.wxbill.service.IWxBillService;
import com.deertt.module.pay.wxbill.util.IWxBillConstants;
import com.deertt.module.pay.wxbill.vo.WxBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class WxBillService extends DvBaseService<IWxBillDao, WxBillVo, Integer> implements IWxBillService, IWxBillConstants {
	
	@Transactional
	public int insert(WxBillVo[] vos) {
		int sum = 0;
		for (WxBillVo wxBillVo : vos) {
			sum += getDao().insert(wxBillVo);
		}
		return sum;
	};
	
}
