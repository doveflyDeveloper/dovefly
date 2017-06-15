package com.deertt.module.trade.bill.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.trade.bill.dao.ITradeDetailDao;
import com.deertt.module.trade.bill.service.ITradeDetailService;
import com.deertt.module.trade.bill.util.ITradeDetailConstants;
import com.deertt.module.trade.bill.vo.TradeDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class TradeDetailService extends DvBaseService<ITradeDetailDao, TradeDetailVo, Integer> implements ITradeDetailService, ITradeDetailConstants {

	@Transactional
	public int[] insertUpdateBatch(List<TradeDetailVo> voList, Integer bill_id) {
		int[] sum_insert_update_delete = new int[3];
		List<TradeDetailVo> lInsert = new ArrayList<TradeDetailVo>();
		List<TradeDetailVo> lUpdate = new ArrayList<TradeDetailVo>();
		List<Integer> lDelete = new ArrayList<Integer>();
		Map<Integer, TradeDetailVo> currentIds = new HashMap<Integer, TradeDetailVo>();
		if(voList != null){
			for (TradeDetailVo vo : voList) {
				vo.setBill_id(bill_id);
				if(vo.isNew()) {
					lInsert.add(vo);
				} else {
					lUpdate.add(vo);
					currentIds.put(vo.getId(), vo);
				}
			}
		}
		List<TradeDetailVo> oldList = this.queryByCondition("bill_id = " + bill_id, null);
		if(oldList != null){
			for (TradeDetailVo vo : oldList) {
				if(!currentIds.containsKey(vo.getId())){
					lDelete.add(vo.getId());
				}
			}
		}

		if(lInsert.size() > 0) {
			sum_insert_update_delete[0] = insert(lInsert.toArray(new TradeDetailVo[0]));
		}
		if(lUpdate.size() > 0) {
			sum_insert_update_delete[1] = update(lUpdate.toArray(new TradeDetailVo[0]));
		}
		if(lDelete.size() > 0) {
			sum_insert_update_delete[2] = delete(lDelete.toArray(new Integer[0]));
		}
		return sum_insert_update_delete;
	}
	
	@Transactional
	public int deleteByBill(Integer bill_id) {
		return getDao().deleteByCondition("bill_id = " + bill_id);
	}
}
