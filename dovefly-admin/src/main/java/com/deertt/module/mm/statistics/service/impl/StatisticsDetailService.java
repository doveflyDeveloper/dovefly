package com.deertt.module.mm.statistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.statistics.dao.IStatisticsDetailDao;
import com.deertt.module.mm.statistics.service.IStatisticsDetailService;
import com.deertt.module.mm.statistics.util.IStatisticsDetailConstants;
import com.deertt.module.mm.statistics.vo.StatisticsDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class StatisticsDetailService extends DvBaseService<IStatisticsDetailDao, StatisticsDetailVo, Integer> implements IStatisticsDetailService, IStatisticsDetailConstants {

	@Transactional
	public int[] insertUpdateBatch(List<StatisticsDetailVo> voList, Integer bill_id) {
		int[] sum_insert_update_delete = new int[3];
		List<StatisticsDetailVo> lInsert = new ArrayList<StatisticsDetailVo>();
		List<StatisticsDetailVo> lUpdate = new ArrayList<StatisticsDetailVo>();
		List<Integer> lDelete = new ArrayList<Integer>();
		Map<Integer, StatisticsDetailVo> currentIds = new HashMap<Integer, StatisticsDetailVo>();
		if(voList != null){
			for (StatisticsDetailVo vo : voList) {
				vo.setBill_id(bill_id);
				if(vo.isNew()) {
					lInsert.add(vo);
				} else {
					lUpdate.add(vo);
					currentIds.put(vo.getId(), vo);
				}
			}
		}
		List<StatisticsDetailVo> oldList = this.queryByCondition("bill_id = " + bill_id, null);
		if(oldList != null){
			for (StatisticsDetailVo vo : oldList) {
				if(!currentIds.containsKey(vo.getId())){
					lDelete.add(vo.getId());
				}
			}
		}

		if(lInsert.size() > 0) {
			sum_insert_update_delete[0] = insert(lInsert.toArray(new StatisticsDetailVo[0]));
		}
		if(lUpdate.size() > 0) {
			sum_insert_update_delete[1] = update(lUpdate.toArray(new StatisticsDetailVo[0]));
		}
		if(lDelete.size() > 0) {
			sum_insert_update_delete[2] = delete(lDelete.toArray(new Integer[0]));
		}
		return sum_insert_update_delete;
	}
	
}
