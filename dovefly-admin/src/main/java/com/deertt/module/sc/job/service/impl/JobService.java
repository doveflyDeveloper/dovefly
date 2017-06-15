package com.deertt.module.sc.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sc.job.dao.IJobDao;
import com.deertt.module.sc.job.service.IJobApplyService;
import com.deertt.module.sc.job.service.IJobService;
import com.deertt.module.sc.job.util.IJobConstants;
import com.deertt.module.sc.job.vo.JobVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class JobService extends DvBaseService<IJobDao, JobVo, Integer> implements IJobService, IJobConstants {

	@Autowired
	private IJobApplyService jobApplyService;

	@Transactional
	public JobVo findFull(Integer id) {
		JobVo vo = super.find(id);
		vo.setDetails(jobApplyService.queryByCondition("job_id = " + id, null));
		return vo;
	}

}
