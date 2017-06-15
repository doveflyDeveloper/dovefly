package com.deertt.module.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.module.report.dao.IReportDao;
import com.deertt.module.report.util.IReportConstants;
import com.deertt.module.report.vo.ReportVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Repository
public class ReportDao extends DvBaseDao<ReportVo, Integer> implements IReportDao, IReportConstants {

	@Override
	public int insert(ReportVo vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ReportVo[] vos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByCondition(String queryCondition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReportVo find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportVo findByCondition(String queryCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(ReportVo vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ReportVo[] vos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRecordCount(String queryCondition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ReportVo> queryByCondition(String queryCondition,
			String orderStr, int startIndex, int size, boolean selectAllClumn) {
		// TODO Auto-generated method stub
		return null;
	}



}
