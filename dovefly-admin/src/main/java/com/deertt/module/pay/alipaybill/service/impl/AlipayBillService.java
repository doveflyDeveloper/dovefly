package com.deertt.module.pay.alipaybill.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.pay.alipaybill.dao.IAlipayBillDao;
import com.deertt.module.pay.alipaybill.service.IAlipayBillService;
import com.deertt.module.pay.alipaybill.util.IAlipayBillConstants;
import com.deertt.module.pay.alipaybill.vo.AlipayBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class AlipayBillService extends DvBaseService<IAlipayBillDao, AlipayBillVo, Integer> implements IAlipayBillService, IAlipayBillConstants {
	
	@Transactional
	public int insert(AlipayBillVo[] vos) {
		int sum = 0;
		for (AlipayBillVo alipayBillVo : vos) {
			sum += getDao().insert(alipayBillVo);
		}
		return sum;
	};
	
}
