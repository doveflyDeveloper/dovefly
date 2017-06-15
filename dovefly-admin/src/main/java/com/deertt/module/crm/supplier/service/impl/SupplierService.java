package com.deertt.module.crm.supplier.service.impl;

import org.springframework.stereotype.Service;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.crm.supplier.dao.ISupplierDao;
import com.deertt.module.crm.supplier.service.ISupplierService;
import com.deertt.module.crm.supplier.util.ISupplierConstants;
import com.deertt.module.crm.supplier.vo.SupplierVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class SupplierService extends DvBaseService<ISupplierDao, SupplierVo, Integer> implements ISupplierService, ISupplierConstants {
	
}
