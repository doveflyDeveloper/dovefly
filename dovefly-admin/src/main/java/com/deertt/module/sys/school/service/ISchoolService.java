package com.deertt.module.sys.school.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.school.dao.ISchoolDao;
import com.deertt.module.sys.school.vo.SchoolVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ISchoolService extends IDvBaseService<ISchoolDao, SchoolVo, Integer> {
	
	/**
	 * 对选定的数据进行排序
	 * @param sort_ids
	 */
	public int sort(String sort_ids);
	
	/**
	 * 指派运营主管
	 * @param manager_id
	 * @param school_ids
	 * @return
	 */
	public int setManager(Integer manager_id, String school_ids);
	
	/**
	 * 指派仓库
	 * @param warehouse_id
	 * @param school_ids
	 * @return
	 */
	public int setWarehouse(Integer warehouse_id, String school_ids);
	
	/**
	 * 更新本学校的店铺数
	 * @param id
	 * @return
	 */
	public int updateShopCount(Integer id);
	
	/**
	 * 更新本学校的用户数
	 * @param id
	 * @return
	 */
	public int updateUserCount(Integer id);

}
