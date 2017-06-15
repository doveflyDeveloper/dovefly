package com.deertt.module.mm.check.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.check.dao.IStockCheckDetailDao;
import com.deertt.module.mm.check.service.IStockCheckDetailService;
import com.deertt.module.mm.check.util.IStockCheckDetailConstants;
import com.deertt.module.mm.check.vo.StockCheckDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class StockCheckDetailService extends DvBaseService<IStockCheckDetailDao, StockCheckDetailVo, Integer> implements IStockCheckDetailService, IStockCheckDetailConstants {

	@Transactional
	public int[] insertUpdateBatch(List<StockCheckDetailVo> voList, Integer bill_id) {
		int[] sum_insert_update_delete = new int[3];
		List<StockCheckDetailVo> lInsert = new ArrayList<StockCheckDetailVo>();
		List<StockCheckDetailVo> lUpdate = new ArrayList<StockCheckDetailVo>();
		List<Integer> lDelete = new ArrayList<Integer>();
		Map<Integer, StockCheckDetailVo> currentIds = new HashMap<Integer, StockCheckDetailVo>();
		if(voList != null){
			for (StockCheckDetailVo vo : voList) {
				vo.setBill_id(bill_id);
				if(vo.isNew()) {
					lInsert.add(vo);
				} else {
					lUpdate.add(vo);
					currentIds.put(vo.getId(), vo);
				}
			}
		}
		List<StockCheckDetailVo> oldList = this.queryByCondition("bill_id = " + bill_id, null);
		if(oldList != null){
			for (StockCheckDetailVo vo : oldList) {
				if(!currentIds.containsKey(vo.getId())){
					lDelete.add(vo.getId());
				}
			}
		}

		if(lInsert.size() > 0) {
			sum_insert_update_delete[0] = insert(lInsert.toArray(new StockCheckDetailVo[0]));
		}
		if(lUpdate.size() > 0) {
			sum_insert_update_delete[1] = update(lUpdate.toArray(new StockCheckDetailVo[0]));
		}
		if(lDelete.size() > 0) {
			sum_insert_update_delete[2] = delete(lDelete.toArray(new Integer[0]));
		}
		return sum_insert_update_delete;
	}
}
