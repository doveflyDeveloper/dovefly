package com.deertt.module.sc.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sc.job.dao.IJobApplyDao;
import com.deertt.module.sc.job.service.IJobApplyService;
import com.deertt.module.sc.job.util.IJobApplyConstants;
import com.deertt.module.sc.job.vo.JobApplyVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class JobApplyService extends DvBaseService<IJobApplyDao, JobApplyVo, Integer> implements IJobApplyService, IJobApplyConstants {
	
}
