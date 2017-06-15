package com.deertt.module.market.bill.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.express.vo.ExpressVo;
import com.deertt.module.market.bill.dao.IMarketBillDao;
import com.deertt.module.market.bill.vo.MarketBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMarketBillService extends IDvBaseService<IMarketBillDao, MarketBillVo, Integer> {
	
	/**
	 * 查询订单（包含订单明细，快递信息）
	 * @param id
	 * @return
	 */
	public MarketBillVo findFull(Integer id);
	
	/**
	 * 未提交订单超时，交易自动关闭（定时任务自动执行）
	 * @param id
	 * @return
	 */
	public int closeUnSubmitBill(Integer id);

	/**
	 * 提交订单发货处理
	 * @param id
	 */
	public int deliver(Integer id, ExpressVo expressVo);

//	/**
//	 * 已提交订单取消（锁定库存回归，资金退回）
//	 * @param id
//	 */
//	public int cancel(Integer id);
	
	/**
	 * 已发货订单超时，自动确认收货（定时任务自动执行）
	 * @param id
	 * @return
	 */
	public int receive(Integer id, boolean isAutoReceive);
	
	/**
	 * 同意退款
	 * @param id
	 * @return
	 */
	public int agreeRefund(Integer id);
	
	/**
	 * 同意退货
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
	 * @param pay_code
	 * @param pay_amount
	 * @param pay_time
	 * @return
	 */
	public int refunding(String bill_code, String pay_code, BigDecimal pay_amount, Timestamp pay_time);
	
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
	public MarketBillVo findByCode(String bill_code);
	
	/**
	 * 更新订单的支付信息
	 * @param vo
	 * @return
	 */
	public int updatePayInfo(MarketBillVo vo);
	
	/**
	 * 更新订单的退款信息
	 * @param vo
	 * @return
	 */
	public int updateRefundInfo(MarketBillVo vo);

	/**
	 * 统计店铺每日销售情况（单量，金额）
	 * @param city_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByBillDate(Integer city_id, String from_date, String to_date);
	
	/**
	 * 统计店铺每日销售情况
	 * @param city_id
	 * @param shop_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportShopGroupByBillDate(Integer city_id, Integer shop_id, String from_date, String to_date);
	
	/**
	 * 统计各个城市的销售情况（订单量，销售金额）
	 * @param city_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByCity(Integer city_id, String from_date, String to_date);
		
	/**
	 * 统计商品销售数量及金额
	 * @param city_id
	 * @param shop_id
	 * @param from_date
	 * @param to_date
	 * @return
	 */
	public List<Map<String, Object>> reportGroupByGoods(Integer market_id, String from_date, String to_date);

	public List<Map<String, Object>> queryListDetails(String queryCondition);
	
	public List<Map<String, Object>> reportCountGroupByStatus(String queryCondition);
	
	public List<Map<String, Object>> reportGroupByMarket(Integer market_id, String from_date, String to_date);
		

}
