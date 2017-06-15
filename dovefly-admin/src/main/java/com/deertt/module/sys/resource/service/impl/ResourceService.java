package com.deertt.module.sys.resource.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.resource.dao.IResourceDao;
import com.deertt.module.sys.resource.service.IResourceService;
import com.deertt.module.sys.resource.util.IResourceConstants;
import com.deertt.module.sys.resource.vo.ResourceVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class ResourceService extends DvBaseService<IResourceDao, ResourceVo, Integer> implements IResourceService, IResourceConstants {

	@Transactional
	public int insert(ResourceVo vo) {
		updateIs_leaf(new Integer[]{vo.getParent_id()}, "0");
		return super.insert(vo);
	}

	@Transactional
	public int insert(ResourceVo[] vos) {
		Integer[] parent_ids = new Integer[vos.length];
		for (int i = 0; i < vos.length ; i++) {
			parent_ids[i] = vos[i].getParent_id();
		}
		updateIs_leaf(parent_ids, "0");
		return super.insert(vos);
	}

	@Transactional
	public int delete(Integer id) {
		ResourceVo vo = find(id);
		int sum = getRecordCount("parent_id = '" + vo.getParent_id() + "'");
		if(sum <= 1){
			updateIs_leaf(new Integer[]{vo.getParent_id()}, "1");
		}
		return super.delete(id);
	}

	@Transactional
	public int delete(Integer[] ids) {
		int sum = 0;
		for (Integer id : ids) {
			ResourceVo vo = find(id);
			int otherChildSum = getRecordCount("parent_id = '" + vo.getParent_id() + "'");
			if(otherChildSum <= 1){
				updateIs_leaf(new Integer[]{vo.getParent_id()}, "1");
			}
			sum += super.delete(id);
		}
		return sum;
	}
	
	@Transactional
	public int update(ResourceVo vo) {
		getDao().updateParentName(vo.getId(), vo.getName());
		return super.update(vo);
	}

	@Transactional
	public int update(ResourceVo[] vos) {
		for (ResourceVo vo : vos) {
			getDao().updateParentName(vo.getId(), vo.getName());
		}
		return super.update(vos);
	}

	@Transactional
	public int updateIs_leaf(Integer[] ids, String is_leaf){
		return getDao().updateIs_leaf(ids, is_leaf);
	}
	
	public String generateNextCode(Integer id){
		ResourceVo vo = getDao().find(id);
		String code = vo.getCode() + StringUtils.leftPad("1", RES_CODE_LENGTH, "0");
		String currMaxCode = getDao().queryChildNodeMaxCode(id);
		if(currMaxCode != null){
			code = StringUtils.leftPad(String.valueOf(Long.parseLong(currMaxCode) + 1), code.length(), "0");
		}
		return code;
	}

	public List<ResourceVo> queryResourcesByRoleId(Integer role_id){
		return getDao().queryResourcesByRoleId(role_id);
	}
	
	public List<ResourceVo> queryMenusByUserId(Integer user_id){
		return getDao().queryMenusByUserId(user_id);
	}

	@Transactional
	public int sort(String sort_ids) {
		return getDao().sort(sort_ids);
	}
	
}
