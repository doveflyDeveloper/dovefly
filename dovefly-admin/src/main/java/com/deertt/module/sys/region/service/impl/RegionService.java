package com.deertt.module.sys.region.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.sys.region.dao.IRegionDao;
import com.deertt.module.sys.region.service.IRegionService;
import com.deertt.module.sys.region.util.IRegionConstants;
import com.deertt.module.sys.region.vo.RegionVo;
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
public class RegionService extends DvBaseService<IRegionDao, RegionVo, Integer> implements IRegionService, IRegionConstants {
	
	@Autowired
	protected IUserService userService;
	
	@Autowired
	protected IWarehouseService warehouseService;
	
	@Transactional
	public int insert(RegionVo vo) {
		this.updateIs_leaf(new Integer[]{vo.getParent_id()}, "0");
		return super.insert(vo);
	}
	
	@Transactional
	public int updateCascade(RegionVo vo) {
		RegionVo oldVo = this.find(vo.getId());
		int sum = super.update(vo);
		if (!vo.getName().equals(oldVo.getName())) {
			getDao().updateChildrenNames(vo);
		}
		return sum;
	}
	
	/**
	 * 检查是否允许删除（此节点及其所有子节点下有未删除店铺则不允许删除，返回false）
	 */
	@Transactional
	public HandleResult checkCanDelete(Integer id) {
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		RegionVo vo = this.find(id);
		if (vo != null) {
			//检查此节点及其所有子节点下是否还有未删除店铺
//			List<ShopVo> shops = shopService.queryByCondition("region_code like '" + vo.getCode() + "%'", null);
//			if (shops != null && shops.size() > 0) {
//				String msg = "以下节点下尚有店铺关联，需先删除对应的店铺。\n";
//				for (ShopVo shop : shops) {
//					msg += "（节点：" + shop.getRegion_full_name() + "）-->（店铺：" + shop.getName()  + "）\n";
//				}
//				result.setSuccess(false);
//				result.setMessage(msg);
//			}
		}
		return result;
	}
	
	/**
	 * 删除区域及其所有下级区域
	 */
	@Transactional
	public int deleteCascade(Integer id) {
		int dSum = 0;
		RegionVo vo = this.find(id);
		if (vo != null) {
			int sum = getRecordCount("parent_id = " + vo.getParent_id());
			if (sum <= 1) {//父节点只包含这一个子节点了
				updateIs_leaf(new Integer[] { vo.getParent_id() }, "1");
			}
			dSum = getDao().deleteByCondition("code like '" + vo.getCode() + "%'");
		}
		return dSum;
	}

	@Transactional
	public int updateIs_leaf(Integer[] ids, String is_leaf){
		return getDao().updateIs_leaf(ids, is_leaf);
	}

	public String generateNextCode(Integer id){
		RegionVo vo = getDao().find(id);
		String code = (vo != null ? vo.getCode() : "") + StringUtils.leftPad("1", REG_CODE_LENGTH, "0");
		String currMaxCode = getDao().queryChildNodeMaxCode(id);
		if(currMaxCode != null){
			code = StringUtils.leftPad(String.valueOf(Long.parseLong(currMaxCode) + 1), code.length(), "0");
		}
		return code;
	}

	@Transactional
	public int sort(String sort_ids) {
		return getDao().sort(sort_ids);
	}
	
	@Transactional
	public int setManager(Integer manager_id, String region_ids) {
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
		sql.append(" WHERE id IN (" + region_ids + ")");
		return super.update(sql.toString());
	}
	
	@Transactional
	public int setWarehouse(Integer warehouse_id, String region_ids) {
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
		sql.append(" WHERE id IN (" + region_ids + ")");
		return super.update(sql.toString());
	}

}
