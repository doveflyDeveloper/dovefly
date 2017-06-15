package com.deertt.module.order.back.service;

import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.order.back.dao.IOrderBackBillDao;
import com.deertt.module.order.back.vo.OrderBackBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IOrderBackBillService extends IDvBaseService<IOrderBackBillDao, OrderBackBillVo, Integer> {
	
	/**
	 * 保存订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int saveFull(OrderBackBillVo vo);

	/**
	 * 新增订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int insertFull(OrderBackBillVo vo);

	/**
	 * 更新订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int updateFull(OrderBackBillVo vo);
	
	/**
	 * 删除订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int deleteFull(Integer id);
	
	/**
	 * 删除订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int deleteFull(Integer[] ids);
	
	/**
	 * 查询订单（包含订单明细，快递信息）
	 * @param id
	 * @return
	 */
	public OrderBackBillVo findFull(Integer id);
	
	/**
	 * 提交订单
	 * @param id
	 */
	public int saveAndSubmit(OrderBackBillVo vo);
	
	/**
	 * 确认收货，商品入库，生成库存
	 * @param id
	 * @return
	 */
	public int receive(Integer id);
	
	/**
	 * 退回
	 * @param id
	 * @return
	 */
    public int reject(Integer id);
	
	/**
	 * 根据订单编号查询订单
	 * @param bill_code
	 * @return
	 */
	public OrderBackBillVo findByCode(String bill_code);

	/**
	 * 更新订单的支付信息
	 * @param vo
	 * @return
	 */
	public int updatePayInfo(OrderBackBillVo vo);

	/**
	 * 更新状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateStatus(Integer id, String status);
    
    /**
	 * 查询订单及明细
	 * @param queryCondition
	 * @return
	 */
	public List<Map<String, Object>> queryListDetails(String queryCondition);

}
