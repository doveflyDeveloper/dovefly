package com.deertt.module.sys.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.school.dao.ISchoolDao;
import com.deertt.module.sys.school.service.ISchoolService;
import com.deertt.module.sys.school.util.ISchoolConstants;
import com.deertt.module.sys.school.vo.SchoolVo;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.vo.UserVo;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
import com.deertt.module.sys.warehouse.vo.WarehouseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class SchoolService extends DvBaseService<ISchoolDao, SchoolVo, Integer> implements ISchoolService, ISchoolConstants {
	
	@Autowired
	protected IUserService userService;
	
	@Autowired
	protected IWarehouseService warehouseService;
	
	@Transactional
	public int sort(String sort_ids) {
		return getDao().sort(sort_ids);
	}
	
	@Transactional
	public int setManager(Integer manager_id, String school_ids) {
		UserVo manager = null;
		if (manager_id != null && manager_id != 0) {
			manager = userService.find(manager_id);
		} else {
			manager = new UserVo();
			manager.setId(null);
			manager.setReal_name("");
		}
		StringBuilder sql = new StringBuilder("UPDATE " + TABLE_NAME + " SET manager_id = " + manager_id);
		sql.append(", manager_name = '" + manager.getReal_name() + "'");
		sql.append(" WHERE id IN (" + school_ids + ")");
		return super.update(sql.toString());
	}
	
	@Transactional
	public int setWarehouse(Integer warehouse_id, String school_ids) {
		WarehouseVo warehouse = null;
		if (warehouse_id != null && warehouse_id != 0) {
			warehouse = warehouseService.find(warehouse_id);
		} else {
			warehouse = new WarehouseVo();
			warehouse.setId(null);
			warehouse.setWarehouse_name("");
		}
		StringBuilder sql = new StringBuilder("UPDATE " + TABLE_NAME + " SET warehouse_id = " + warehouse_id);
		sql.append(", warehouse_name = '" + warehouse.getWarehouse_name() + "'");
		sql.append(" WHERE id IN (" + school_ids + ")");
		return super.update(sql.toString());
	}

	@Transactional
	public int updateShopCount(Integer id) {
		return super.update(SQL_UPDATE_SHOP_COUNT_BY_ID, new Object[]{ id });
	}
	
	@Transactional
	public int updateUserCount(Integer id) {
		return super.update(SQL_UPDATE_USER_COUNT_BY_ID, new Object[]{ id });
	}
}
