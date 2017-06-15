package com.deertt.module.order.bill.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.order.bill.dao.IOrderBillDao;
import com.deertt.module.order.bill.vo.OrderBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IOrderBillService extends IDvBaseService<IOrderBillDao, OrderBillVo, Integer> {
	
	/**
	 * 保存订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int saveFull(OrderBillVo vo);

	/**
	 * 新增订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int insertFull(OrderBillVo vo);

	/**
	 * 更新订单（包含订单明细）
	 * @param vo
	 * @return
	 */
	public int updateFull(OrderBillVo vo);
	
	/**
	 * 查询订单（包含订单明细，快递信息）
	 * @param id
	 * @return
	 */
	public OrderBillVo findFull(Integer id);
	
	/**
	 * 新下订单（锁定库存，等待支付）
	 * @param vo
	 * @return
	 */
	public int makeOrder(OrderBillVo vo);
	
	/**
	 * 关闭未支付订单并返还锁定库存
	 * @param id
	 * @return
	 */
	public int closeUnSubmitBill(Integer id);
	
	/**
	 * 货到付款订单提交
	 * @param id
	 * @return
	 */
	public int codPayAndSubmit(Integer id);
	
	/**
	 * 支付成功后回调并提交订单
	 * @param bean
	 */
	public int paySuccessCallBack(Integer id, String pay_type, String pay_code, BigDecimal pay_amount, Timestamp pay_time);

	/**
	 * 发货处理（扣库存）
	 * @param bean
	 */
	public int deliver(OrderBillVo vo);
	
	/**
	 * 确认收货，买家商品入库，卖家资金清算
	 * @param id
	 * @param isAutoReceive
	 * @return
	 */
	public int receive(Integer id, boolean isAutoReceive);
	
	/**
	 * 卖家同意退款
	 * @param id
	 * @return
	 */
	public int agreeRefund(Integer id);
	
	/**
	 * 卖家同意退货
	 * @param id
	 * @return
	 */
	public int agreeReturn(Integer id);
	
	/**
	 * 卖家拒绝退货
	 * @param id
	 * @return
	 */
	public int denyReturn(Integer id);
	
	/**
	 * 退款处理中（已向支付宝或微信提交退款申请后）
	 * 更新支付宝/微信退款交易流水号，退款时间等，更新订单状态为退款处理中
	 * @param bill_code
	 * @param refund_type
	 * @param refund_code
	 * @param refund_amount
	 * @param refund_time
	 * @return
	 */
	public int refunding(String bill_code, String refund_type, String refund_code, BigDecimal refund_amount, Timestamp refund_time);
	
	/**
	 * 订单退款成功回调
	 * 更新支付状态为支付成功，订单状态为已取消
	 * @param bill_code
	 * @return
	 */
	public int refundSuccessCallBack(String bill_code);
	
	/**
	 * 订单退款失败回调
	 * @param bill_code
	 * @param pay_msg
	 * @return
	 */
	public int refundFailCallBack(String bill_code, String pay_msg);

	/**
	 * 根据订单编号查询订单
	 * @param bill_code
	 * @return
	 */
	public OrderBillVo findByCode(String bill_code);

	/**
	 * 更新订单的支付信息
	 * @param vo
	 * @return
	 */
	public int updatePayInfo(OrderBillVo vo);
	
	/**
	 * 更新订单的退款信息
	 * @param vo
	 * @return
	 */
	public int updateRefundInfo(OrderBillVo vo);
	
	/**
	 * 统计某城市每天的订单量（未发货，已发货，已收货状态下的单量，总金额）
	 * @param city_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByBillDate(Integer city_id, String from_date, String to_date);
	
	/**
	 * 按日期统计店长进货量
	 * @param city_id
	 * @param shop_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportShopGroupByBillDate(Integer city_id, Integer shop_id, String from_date, String to_date);
	
	/**
	 * 运营主管管理的店长的进货订单情况
	 * @param city_id
	 * @param manager_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportManagerGroupByBillDate(Integer city_id, Integer manager_id, String from_date, String to_date);
	
	/**
	 * 统计各个城市的进货订单量
	 * @param city_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByCity(Integer city_id, String from_date, String to_date);
	
	/**
	 * 统计各个运营主管所管理店长的进货情况（单量，总金额）
	 * @param city_id
	 * @param manager_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByManager(Integer city_id, Integer manager_id, String from_date, String to_date);
	
	/**
	 * 统计各个学校的进货情况（单量，总金额）
	 * @param city_id
	 * @param manager_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupBySchool(Integer city_id, Integer manager_id, String from_date, String to_date);
	
	/**
	 * 统计各个店长的进货情况（单量，总金额）
	 * @param city_id
	 * @param manager_id
	 * @param 
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByShop(Integer city_id, Integer manager_id, Integer shop_id, String from_date, String to_date);
	
	 /**
	 * 统计具体学校店长的销售情况（订单量，销售金额）
	 * @param city_id
	 * @param manager_id
	 * @param warehouse_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportShopkeeper(Integer city_id, Integer manager_id, Integer school_id, String from_date, String to_date);
		
	/**
	 * 统计商品进货数量及金额
	 * @param city_id
	 * @param warehouse_id
	 * @param shop_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByGoods(Integer city_id, Integer warehouse_id, Integer shop_id, String from_date, String to_date);
	
	/**
	 * 查询订单及明细
	 * @param queryCondition
	 * @return
	 */
	public List<Map<String, Object>> queryListDetails(String queryCondition);
	
	/**
	 * 功能: 通过查询条件获得所有的VO对象订单状态
	 *
	 * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
	 * @return 查询到的VO列表
	 */
	public List<Map<String, Object>> reportCountGroupByStatus(String queryCondition);
	
	
	/**
	 * 查询采购商品对应的销售记录
	 * @param goods_id
	 * @return
	 */
	public List<Map<String, Object>> reportShopGroupByGoods(Integer goods_id, String from_date, String to_date);
}
