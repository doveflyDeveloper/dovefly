package com.deertt.module.sequence.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sequence.dao.ISequenceDao;
import com.deertt.module.sequence.service.ISequenceService;
import com.deertt.module.sequence.util.ISequenceConstants;
import com.deertt.module.sequence.vo.SequenceVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class SequenceService extends DvBaseService<ISequenceDao, SequenceVo, Integer> implements ISequenceService, ISequenceConstants {
	
	public SequenceVo findBySeqName(String seqName) {
		List<SequenceVo> list = this.queryByCondition("sequence_name = '" + seqName + "'", null);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Transactional
    public int updateByIdAndVersion(Long sequenceValue, Long id, Long version) {
    	return this.update(SQL_UPDATE_BY_ID_AND_VERSION, new Object[] { sequenceValue, id, version });
    }

}
