package com.deertt.module.sc.job.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sc.job.dao.IJobDao;
import com.deertt.module.sc.job.vo.JobVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IJobService extends IDvBaseService<IJobDao, JobVo, Integer> {

	/**
	 * 查询招聘信息（包含申请人明细）
	 * @param id
	 * @return
	 */
   public   JobVo  findFull(Integer id);
}
