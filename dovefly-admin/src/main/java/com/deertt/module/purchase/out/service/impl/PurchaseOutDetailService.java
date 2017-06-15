package com.deertt.module.purchase.out.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.purchase.out.dao.IPurchaseOutDetailDao;
import com.deertt.module.purchase.out.service.IPurchaseOutDetailService;
import com.deertt.module.purchase.out.util.IPurchaseOutDetailConstants;
import com.deertt.module.purchase.out.vo.PurchaseOutDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class PurchaseOutDetailService extends DvBaseService<IPurchaseOutDetailDao, PurchaseOutDetailVo, Integer> implements IPurchaseOutDetailService, IPurchaseOutDetailConstants {

	@Transactional
	public int[] insertUpdateBatch(List<PurchaseOutDetailVo> voList, Integer bill_id) {
		int[] sum_insert_update_delete = new int[3];
		List<PurchaseOutDetailVo> lInsert = new ArrayList<PurchaseOutDetailVo>();
		List<PurchaseOutDetailVo> lUpdate = new ArrayList<PurchaseOutDetailVo>();
		List<Integer> lDelete = new ArrayList<Integer>();
		Map<Integer, PurchaseOutDetailVo> currentIds = new HashMap<Integer, PurchaseOutDetailVo>();
		if(voList != null){
			for (PurchaseOutDetailVo vo : voList) {
				vo.setBill_id(bill_id);
				if(vo.isNew()) {
					lInsert.add(vo);
				} else {
					lUpdate.add(vo);
					currentIds.put(vo.getId(), vo);
				}
			}
		}
		List<PurchaseOutDetailVo> oldList = this.queryByCondition("bill_id = " + bill_id, null);
		if(oldList != null){
			for (PurchaseOutDetailVo vo : oldList) {
				if(!currentIds.containsKey(vo.getId())){
					lDelete.add(vo.getId());
				}
			}
		}

		if(lInsert.size() > 0) {
			sum_insert_update_delete[0] = insert(lInsert.toArray(new PurchaseOutDetailVo[0]));
		}
		if(lUpdate.size() > 0) {
			sum_insert_update_delete[1] = update(lUpdate.toArray(new PurchaseOutDetailVo[0]));
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
