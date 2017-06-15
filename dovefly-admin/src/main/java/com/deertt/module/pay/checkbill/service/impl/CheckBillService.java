package com.deertt.module.pay.checkbill.service.impl;

import org.springframework.stereotype.Service;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.pay.checkbill.dao.ICheckBillDao;
import com.deertt.module.pay.checkbill.service.ICheckBillService;
import com.deertt.module.pay.checkbill.util.ICheckBillConstants;
import com.deertt.module.pay.checkbill.vo.CheckBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class CheckBillService extends DvBaseService<ICheckBillDao, CheckBillVo, Integer> implements ICheckBillService, ICheckBillConstants {
	
}
