package com.deertt.module.mm.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.goods.service.IGoodsMService;
import com.deertt.module.mm.goods.dao.IGoodsMHotDao;
import com.deertt.module.mm.goods.service.IGoodsMHotService;
import com.deertt.module.mm.goods.util.IGoodsMHotConstants;
import com.deertt.module.mm.goods.vo.GoodsMHotVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class GoodsMHotService extends DvBaseService<IGoodsMHotDao, GoodsMHotVo, Integer> implements IGoodsMHotService, IGoodsMHotConstants {
	
	@Autowired
	private IGoodsMService goodsWService;
	
	@Transactional
	public int insert(GoodsMHotVo[] vos) {
		int sum = 0;
		for (GoodsMHotVo g : vos) {
			sum += super.insert(g);
		}
		return sum;
	}
	
	@Transactional
	public int onSale(Integer[] ids) {
		return getDao().enable(ids);
	}
	
	@Transactional
	public int offSale(Integer[] ids) {
		return getDao().disable(ids);
	}
	
	@Transactional
	public int sort(String goods_ids) {
		return getDao().sort(goods_ids);
	}
}
