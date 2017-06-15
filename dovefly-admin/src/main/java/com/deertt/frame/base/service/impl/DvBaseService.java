package com.deertt.frame.base.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.frame.base.vo.DvBaseVo;
import com.deertt.utils.helper.date.DvDateHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class DvBaseService<DAO extends IDvBaseDao<T, ID>, T extends DvBaseVo, ID extends Serializable> implements IDvBaseService<DAO, T, ID>{

	private DAO dao;

	/**
	 * 获取数据访问接口
	 * 
	 * @return
	 */
	public DAO getDao() {
		return dao;
	}

	/**
	 * 设置数据访问接口
	 * 
	 * @param dao
	 */
	@Autowired
	public void setDao(DAO dao) {
		this.dao = dao;
	}

	@Transactional
	public int save(T vo) {
		if (vo.isNew()) {
			return dao.insert(vo);
		} else {
			return dao.update(vo);
		}
	}

	@Transactional
	public int insert(T vo) {
		return dao.insert(vo);
	}

	@Transactional
	public int insert(T[] vos) {
		return dao.insert(vos);
	}

	@Transactional
	public int delete(ID id) {
		return dao.delete(id);
	}

	@Transactional
	public int delete(ID[] ids) {
		int sum = dao.delete(ids);
		return sum;
	}

	public T find(ID id) {
		T vo = dao.find(id);
		return vo;
	}

	@Transactional
	public int update(T vo) {
		int sum = dao.update(vo);
		return sum;
	}

	@Transactional
	public int update(T[] vos) {
		return dao.update(vos);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public int[] insertUpdateBatch(T[] vos) {
		int[] sum_insert_update = new int[2];
		List<T> lInsert = new ArrayList<T>();
		List<T> lUpdate = new ArrayList<T>();
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].isNew()) {
				lInsert.add(vos[i]);
			} else {
				lUpdate.add(vos[i]);
			}
		}
		try {
			if (lInsert.size() > 0) {
				sum_insert_update[0] = insert(lInsert.toArray((T[])vos.getClass().newInstance()));
			}
			if (lUpdate.size() > 0) {
				sum_insert_update[1] = update(lUpdate.toArray((T[])vos.getClass().newInstance()));
			}
		} catch (InstantiationException e) {
			throw new RuntimeException("批量插入或更新错误", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("批量插入或更新错误", e);
		}
		return sum_insert_update;
	}

	@Transactional
	public int update(String sql){
		return dao.update(sql);
	}
	
	@Transactional
	public int update(String sql, Object[] args) {
		return dao.update(sql, args);
	}
	
	public int queryForInt(String sql) {
		return dao.queryForInt(sql);
	}
	
	public Map<String, Object> queryForMap(String sql) {
		return dao.queryForMap(sql);
	}
	
	public List<Map<String, Object>> queryForMaps(String sql) {
		return dao.queryForMaps(sql);
	}

	public int getRecordCount(String queryCondition) {
		int sum = dao.getRecordCount(queryCondition);
		return sum;
	}

	public List<T> queryByCondition(String queryCondition, String orderStr) {
		return queryByCondition(queryCondition, orderStr, -1, -1);
	}

	public List<T> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
		return queryByCondition(queryCondition, orderStr, startIndex, size, false);
	}

	public List<T> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
		List<T> lResult = dao.queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
		return lResult;
	}
	
	public String generateBillCode(String prefix) {
		prefix = prefix == null ? "" : prefix;
		return prefix + DvDateHelper.formatDate(new Date(), "yyMMddHHmmss") + String.format("%04d", (int)(Math.random()*10000));
	}

}
