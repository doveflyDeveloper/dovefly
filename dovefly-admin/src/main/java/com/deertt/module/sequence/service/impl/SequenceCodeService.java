package com.deertt.module.sequence.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sequence.dao.ISequenceCodeDao;
import com.deertt.module.sequence.service.ISequenceCodeService;
import com.deertt.module.sequence.util.ISequenceCodeConstants;
import com.deertt.module.sequence.vo.SequenceCodeVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class SequenceCodeService extends DvBaseService<ISequenceCodeDao, SequenceCodeVo, Integer> implements ISequenceCodeService, ISequenceCodeConstants {
	public String findCodeBySeqName(String seqName) {
		List<SequenceCodeVo> list = this.queryByCondition("sequence_name = '" + seqName + "'", null);
		if (list != null && list.size() > 0) {
			return list.get(0).getBiz_code();
		} else {
			return null;
		}
	}
}
