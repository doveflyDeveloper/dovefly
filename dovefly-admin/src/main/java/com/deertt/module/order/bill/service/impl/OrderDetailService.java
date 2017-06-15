package com.deertt.module.order.bill.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.order.bill.dao.IOrderDetailDao;
import com.deertt.module.order.bill.service.IOrderDetailService;
import com.deertt.module.order.bill.util.IOrderDetailConstants;
import com.deertt.module.order.bill.vo.OrderDetailVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class OrderDetailService extends DvBaseService<IOrderDetailDao, OrderDetailVo, Integer> implements IOrderDetailService, IOrderDetailConstants {

	@Transactional
	public int[] insertUpdateBatch(List<OrderDetailVo> voList, Integer bill_id) {
		int[] sum_insert_update_delete = new int[3];
		List<OrderDetailVo> lInsert = new ArrayList<OrderDetailVo>();
		List<OrderDetailVo> lUpdate = new ArrayList<OrderDetailVo>();
		List<Integer> lDelete = new ArrayList<Integer>();
		Map<Integer, OrderDetailVo> currentIds = new HashMap<Integer, OrderDetailVo>();
		if(voList != null){
			for (OrderDetailVo vo : voList) {
				vo.setBill_id(bill_id);
				if(vo.isNew()) {
					lInsert.add(vo);
				} else {
					lUpdate.add(vo);
					currentIds.put(vo.getId(), vo);
				}
			}
		}
		List<OrderDetailVo> oldList = this.queryByCondition("bill_id = " + bill_id, null);
		if(oldList != null){
			for (OrderDetailVo vo : oldList) {
				if(!currentIds.containsKey(vo.getId())){
					lDelete.add(vo.getId());
				}
			}
		}

		if(lInsert.size() > 0) {
			sum_insert_update_delete[0] = insert(lInsert.toArray(new OrderDetailVo[0]));
		}
		if(lUpdate.size() > 0) {
			sum_insert_update_delete[1] = update(lUpdate.toArray(new OrderDetailVo[0]));
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
