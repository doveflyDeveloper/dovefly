package com.deertt.module.report.service.impl;

import org.springframework.stereotype.Service;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.report.dao.IReportDao;
import com.deertt.module.report.service.IReportService;
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
@Service
public class ReportService extends DvBaseService<IReportDao, ReportVo, Integer> implements IReportService, IReportConstants {
	
}
