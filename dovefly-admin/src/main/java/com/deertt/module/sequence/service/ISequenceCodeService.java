package com.deertt.module.sequence.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sequence.dao.ISequenceCodeDao;
import com.deertt.module.sequence.vo.SequenceCodeVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ISequenceCodeService extends IDvBaseService<ISequenceCodeDao, SequenceCodeVo, Integer> {
	public String findCodeBySeqName(String seqName);
}
