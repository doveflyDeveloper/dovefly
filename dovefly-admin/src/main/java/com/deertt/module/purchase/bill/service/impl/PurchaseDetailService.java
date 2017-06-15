package com.deertt.module.purchase.bill.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.purchase.bill.dao.IPurchaseDetailDao;
import com.deertt.module.purchase.bill.service.IPurchaseDetailService;
import com.deertt.module.purchase.bill.util.IPurchaseDetailConstants;
import com.deertt.module.purchase.bill.vo.PurchaseDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class PurchaseDetailService extends DvBaseService<IPurchaseDetailDao, PurchaseDetailVo, Integer> implements IPurchaseDetailService, IPurchaseDetailConstants {

	@Transactional
	public int[] insertUpdateBatch(List<PurchaseDetailVo> voList, Integer bill_id) {
		int[] sum_insert_update_delete = new int[3];
		List<PurchaseDetailVo> lInsert = new ArrayList<PurchaseDetailVo>();
		List<PurchaseDetailVo> lUpdate = new ArrayList<PurchaseDetailVo>();
		List<Integer> lDelete = new ArrayList<Integer>();
		Map<Integer, PurchaseDetailVo> currentIds = new HashMap<Integer, PurchaseDetailVo>();
		if(voList != null){
			for (PurchaseDetailVo vo : voList) {
				vo.setBill_id(bill_id);
				if(vo.isNew()) {
					lInsert.add(vo);
				} else {
					lUpdate.add(vo);
					currentIds.put(vo.getId(), vo);
				}
			}
		}
		List<PurchaseDetailVo> oldList = this.queryByCondition("bill_id = " + bill_id, null);
		if(oldList != null){
			for (PurchaseDetailVo vo : oldList) {
				if(!currentIds.containsKey(vo.getId())){
					lDelete.add(vo.getId());
				}
			}
		}

		if(lInsert.size() > 0) {
			sum_insert_update_delete[0] = insert(lInsert.toArray(new PurchaseDetailVo[0]));
		}
		if(lUpdate.size() > 0) {
			sum_insert_update_delete[1] = update(lUpdate.toArray(new PurchaseDetailVo[0]));
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
