package com.deertt.module.sequence.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sequence.dao.ISequenceDao;
import com.deertt.module.sequence.vo.SequenceVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ISequenceService extends IDvBaseService<ISequenceDao, SequenceVo, Integer> {
	
    public SequenceVo findBySeqName(String seqName);

    public int updateByIdAndVersion(Long sequenceValue, Long id, Long version);
	
}
