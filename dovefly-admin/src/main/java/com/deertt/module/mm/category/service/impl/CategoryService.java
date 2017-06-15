package com.deertt.module.mm.category.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.mm.category.dao.ICategoryDao;
import com.deertt.module.mm.category.service.ICategoryService;
import com.deertt.module.mm.category.util.ICategoryConstants;
import com.deertt.module.mm.category.vo.CategoryVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class CategoryService extends DvBaseService<ICategoryDao, CategoryVo, Integer> implements ICategoryService, ICategoryConstants {

	@Transactional
	public int insert(CategoryVo vo) {
		updateIs_leaf(new Integer[]{vo.getParent_id()}, "0");
		return super.insert(vo);
	}
	
	@Transactional
	public int updateCascade(CategoryVo vo) {
		CategoryVo oldVo = this.find(vo.getId());
		int sum = super.update(vo);
		if (!vo.getName().equals(oldVo.getName())) {
	        getDao().updateParentName(vo.getId(), vo.getName());
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
//		result.setSuccess(false);
//		result.setMessage("分类信息不可轻易删除，如需删除，请联系系统管理员！");
		
		return result;
	}
	
	/**
	 * 删除区域及其所有下级区域
	 */
	@Transactional
	public int deleteCascade(Integer id) {
		int dSum = 0;
		CategoryVo vo = this.find(id);
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
		CategoryVo vo = getDao().find(id);
		String code = vo.getCode() + StringUtils.leftPad("1", CAT_CODE_LENGTH, "0");
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
}
