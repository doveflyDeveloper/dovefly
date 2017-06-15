package com.deertt.module.trade.bill.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.refund.service.IRefundService;
import com.deertt.module.fund.refund.vo.RefundVo;
import com.deertt.module.mm.goods.service.IGoodsSService;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.trade.bill.dao.ITradeBillDao;
import com.deertt.module.trade.bill.service.ITradeBillService;
import com.deertt.module.trade.bill.service.ITradeDetailService;
import com.deertt.module.trade.bill.util.ITradeBillConstants;
import com.deertt.module.trade.bill.vo.TradeBillVo;
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
public class TradeBillService extends DvBaseService<ITradeBillDao, TradeBillVo, Integer> implements ITradeBillService, ITradeBillConstants {
	
	@Autowired
	private ITradeDetailService detailService;
	
	@Autowired
	private IGoodsSService goodsSService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IShopService shopService;
	
	@Autowired
	private IRefundService refundService;
	
	@Autowired
	private INotificationService notificationService;
	
	@Transactional
	public TradeBillVo findFull(Integer id) {
		TradeBillVo vo = this.find(id);
		vo.setDetails(detailService.queryByCondition("bill_id = " + id, null));
		return vo;
	}
	
	@Transactional
	public int closeUnSubmitBill(Integer id) {
		TradeBillVo vo = this.findFull(id);
		
		if (!TradeBillVo.STATUS_EDIT.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于未提交状态，不可关闭！");
		}
		
		//释放此订单占用的锁定库存
		if (vo.getDetails() != null) {
			for (TradeDetailVo detail : vo.getDetails()) {
				goodsSService.turnbackStockLockedToStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		
		//如果使用汀豆，则返回给用户
		if (vo.getUse_coin_quantity() > 0) {
			userService.addCoin_quantity(vo.getBuyer_id(), vo.getUse_coin_quantity());
		}
		
		//设置为已关闭
		return this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_CLOSED, true);
	}
	
	@Transactional
	public int deliver(Integer id) {
		TradeBillVo vo = this.findFull(id);
		
		if (!(TradeBillVo.STATUS_SUBMIT.equals(vo.getStatus()) || TradeBillVo.STATUS_APPLY_FOR_REFUND.equals(vo.getStatus()))) {
			throw new BusinessException("订单不处于待发货或申请退款状态，不可发货处理！");
		}
		
		//设置为已收货
		return this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_DELIVERED, false);
	}
	
	@Transactional
	public int receive(Integer id, boolean isAutoReceive) {
		TradeBillVo vo = this.findFull(id);
		
		if (!TradeBillVo.STATUS_DELIVERED.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于已发货状态，不可进行确认收货操作！");
		}
		
		//卖家扣锁定库存
		if (vo.getDetails() != null) {
			for (TradeDetailVo detail : vo.getDetails()) {
				goodsSService.reduceStockLocked(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		
		//赠送的汀豆进入买家账户
		if (vo.getSend_coin_quantity() > 0) {
			userService.addCoin_quantity(vo.getBuyer_id(), vo.getSend_coin_quantity());
		}
		
		
		//待收款金额转为可用资金
		if (TradeBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
			shopService.reduceHalfway_amount(vo.getShop_id(), vo.getIncome_amount());
			shopService.addBalance_amount(vo.getShop_id(), vo.getBill_code(), vo.getPay_code(), vo.getIncome_amount(), "订单【" + vo.getBill_code() + "】客户已确认收货，店长入账：" + vo.getTotal_amount() + "元", "");
		}
		
		//设置订单为已收货
		return this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_RECEIVED, isAutoReceive);
	}
	
	@Transactional
	public int agreeRefund(Integer id) {
		TradeBillVo vo = this.findFull(id);
		
		if (!TradeBillVo.STATUS_APPLY_FOR_REFUND.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于申请退款状态，不可同意退款处理！");
		}
		
		if (TradeBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
			//增加退款请求
			refundService.addTRefund(vo.getShop_id(), vo.getShop_name(), vo.getBuyer_id(), vo.getBuyer_name(), vo.getBill_code(), vo.getPay_type(), vo.getPay_amount());
			
			//设置为同意退款
			return this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_AGREE_TO_REFUND, false);
		} else if (TradeBillVo.PAY_TYPE_COD.equals(vo.getPay_type())){//货到付款
			//释放此订单占用的锁定库存
			if (vo.getDetails() != null) {
				for (TradeDetailVo detail : vo.getDetails()) {
					goodsSService.turnbackStockLockedToStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
				}
			}
			
			//设置为已退款
			return this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_REFUNDED, false);
		}
		return 1;
		
	}
	
	@Transactional
	public int agreeReturn(Integer id) {
		TradeBillVo vo = this.findFull(id);
		
		if (!TradeBillVo.STATUS_APPLY_FOR_RETURN.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于申请退货状态，不可同意退货处理！");
		}
		
		if (TradeBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
			
			//增加退款请求
			refundService.addTRefund(vo.getShop_id(), vo.getShop_name(), vo.getBuyer_id(), vo.getBuyer_name(), vo.getBill_code(), vo.getPay_type(), vo.getPay_amount());
			
			//设置为同意退货
			return this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_AGREE_TO_RETURN, false);
		} else if (TradeBillVo.PAY_TYPE_COD.equals(vo.getPay_type())){//货到付款
			//释放此订单占用的锁定库存
			if (vo.getDetails() != null) {
				for (TradeDetailVo detail : vo.getDetails()) {
					goodsSService.turnbackStockLockedToStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
				}
			}
			
			//设置为已退货
			return this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_RETURNED, false);
		}
		return 1;

	}
	
	@Transactional
	public int denyReturn(Integer id) {
		TradeBillVo vo = this.findFull(id);
		
		if (!TradeBillVo.STATUS_APPLY_FOR_RETURN.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于申请退货状态，不可拒绝退货处理！");
		}
		
		//设置订单为已发货
		//TODO 这地方应该是发送拒绝退货的消息
		return this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_DELIVERED, false);
	}
	
	@Transactional
	public int refunding(String bill_code, String pay_code, BigDecimal pay_amount, Timestamp pay_time) {
		TradeBillVo vo = this.findByCode(bill_code);
		
		if (!(TradeBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus()) || TradeBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus()))) {
			throw new BusinessException("订单不处于同意退款或同意退货状态，退款回调失败！");
		}
		
		//更新退款请求为退款处理中
		RefundVo refund = refundService.findByReferBillCode(bill_code);
		refund.setPay_code(pay_code);
		refund.setPay_time(pay_time);
		refund.setPay_status(RefundVo.PAY_STATUS_DEALING);
		refund.setStatus(RefundVo.STATUS_DEALING);
		refundService.update(refund);
		
		//更新订单退款状态为退款中
		vo.setRefund_code(pay_code);
		vo.setRefund_amount(pay_amount);
		vo.setRefund_time(pay_time);
		vo.setRefund_status(TradeBillVo.REFUND_STATUS_ING);
		return this.updateRefundInfo(vo);
	}
	
	@Transactional
	public int refundSuccessCallBack(String bill_code) {
		int sum = 0;
		TradeBillVo vo = this.findByCode(bill_code);
		vo = this.findFull(vo.getId());
		
		if (!(TradeBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus()) || TradeBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus()))) {
			throw new BusinessException("订单不处于同意退款或同意退货状态，退款回调失败！");
		}
		
		//返回此订单锁定数量
		if (vo.getDetails() != null) {
			for (TradeDetailVo detail : vo.getDetails()) {
				if (TradeBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus())) {//退款，未发货，未扣减锁定库存，只需释放锁定库存
					goodsSService.turnbackStockLockedToStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
				} else if (TradeBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus())) {//退货，已发货，未收货，未扣减库存，只需释放锁定库存
					goodsSService.turnbackStockLockedToStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
				}
			}
		}
		
		//扣减卖家待收款金额
		if (TradeBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
			shopService.reduceHalfway_amount(vo.getShop_id(), vo.getIncome_amount());
		}
		
		//更新退款申请记录
		RefundVo refund = refundService.findByReferBillCode(bill_code);
		refund.setPay_status(RefundVo.PAY_STATUS_SUCCESS);
		refund.setStatus(RefundVo.STATUS_SUCCESS);
		refundService.update(refund);
		
		//更新订单退款状态
		vo.setRefund_status(TradeBillVo.REFUND_STATUS_SUCCESS);
		this.updateRefundInfo(vo);
		
		//设置订单为已退歀或已退货状态
		if (TradeBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus())) {
			sum = this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_REFUNDED, false);
		} else if (TradeBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus())) {
			sum = this.updateBillStatusAndSendMessage(vo, TradeBillVo.STATUS_RETURNED, false);
		}
		
		return sum;
	}
	
	@Transactional
	public int refundFailCallBack(String bill_code, String pay_msg) {
		TradeBillVo vo = this.findByCode(bill_code);
		
		if (!(TradeBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus()) || TradeBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus()))) {
			throw new BusinessException("订单不处于退款或退货中状态，退款回调失败！");
		}
		
		//更新退款请求记录
		RefundVo refund = refundService.findByReferBillCode(bill_code);
		refund.setPay_status(RefundVo.PAY_STATUS_FAIL);
		refund.setStatus(RefundVo.STATUS_FAIL);
		refund.setPay_msg(pay_msg);
		refundService.update(refund);
		
		//更新订单退款记录
		vo.setRefund_status(TradeBillVo.REFUND_STATUS_FAIL);
		return this.updateRefundInfo(vo);
	}
	
	
	public TradeBillVo findByCode(String bill_code) {
		return getDao().findByCondition("bill_code = '" + bill_code + "'");
	}
	
	@Transactional
	public int updatePayInfo(TradeBillVo vo) {
		return getDao().updatePayInfo(vo);
	}
	
	@Transactional
	public int updateRefundInfo(TradeBillVo vo) {
		return getDao().updateRefundInfo(vo);
	}
	
	/**
	 * 
	 * @param vo
	 * @param status
	 * @return
	 */
	@Transactional
	public int updateBillStatusAndSendMessage(TradeBillVo vo, String status, boolean isAutoRun) {
		int sum = 0;
		String sql = SQL_UPDATE_STATUS_BY_ID;
		if (TradeBillVo.STATUS_SUBMIT.equals(status)) {
			//如果订单提交，那么更新订单bill_date为最新日期
			sql = SQL_SUBMIT_BY_ID;
		} else if (TradeBillVo.STATUS_DELIVERED.equals(status)) {
			sql = SQL_DELIVER_BY_ID;
		} else if (TradeBillVo.STATUS_RECEIVED.equals(status)) {
			sql = SQL_RECEIVE_BY_ID;
		}
		sum = getDao().update(sql, new Object[]{ status, vo.getId() });
		
		//发送通知消息
		if (TradeBillVo.STATUS_SUBMIT.equals(status)) {
			//给卖家发送新订单消息
			String message = "新一笔订单待发货";
			notificationService.addWMNotification("s", vo.getShop_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_SUBMIT, message);
		} else if (TradeBillVo.STATUS_DELIVERED.equals(status)) {
			//买家，订单已发货消息
			String message = "您有一笔订单已发货";
			notificationService.addWMNotification(vo.getBuyer_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_DELIVERED, message);
			
		} else if (TradeBillVo.STATUS_RECEIVED.equals(status)) {
			if (isAutoRun) {//系统自动确认的
				//买家，订单自动确认收货消息
				String buyer_message = "您有一笔订单超时未确认收货，系统已自动确认收货";
				notificationService.addWMNotification(vo.getBuyer_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_RECEIVED, buyer_message);

				//卖家，订单自动确认收货消息
				String seller_message = "用户有一笔订单超时未确认收货，系统已自动确认收货";
				notificationService.addWMNotification("s", vo.getShop_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_RECEIVED, seller_message);
			} else {
				//卖家，订单已收货消息
				String message = "用户有一笔订单已确认收货";
				notificationService.addWMNotification("s", vo.getShop_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_RECEIVED, message);
			}
			
		} else if (TradeBillVo.STATUS_APPLY_FOR_REFUND.equals(status)) {
			//卖家，订单申请退歀消息
			String message = "用户有一笔订单申请退歀";
			notificationService.addWMNotification("s", vo.getShop_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_APPLY_FOR_REFUND, message);

		} else if (TradeBillVo.STATUS_APPLY_FOR_RETURN.equals(status)) {
			//卖家，订单申请退货消息
			String message = "用户有一笔订单申请退货";
			notificationService.addWMNotification("s", vo.getShop_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_APPLY_FOR_RETURN, message);
			
		} else if (TradeBillVo.STATUS_AGREE_TO_REFUND.equals(status)) {
			//买家，订单申请退货消息
			String message = "您有一笔订单卖家已同意退款";
			notificationService.addWMNotification(vo.getBuyer_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_AGREE_TO_REFUND, message);
			
		} else if (TradeBillVo.STATUS_AGREE_TO_RETURN.equals(status)) {
			//买家，订单申请退货消息
			String message = "您有一笔订单卖家已同意退货";
			notificationService.addWMNotification(vo.getBuyer_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_AGREE_TO_RETURN, message);

		} else if (TradeBillVo.STATUS_REFUNDED.equals(status)) {
			//买家，订单已退款消息
			String buyer_message = "您有一笔订单已成功退款";
			notificationService.addWMNotification(vo.getBuyer_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_REFUNDED, buyer_message);

			//卖家，订单已退款消息
			String seller_message = "用户有一笔订单已成功退款";
			notificationService.addWMNotification("s", vo.getShop_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_REFUNDED, seller_message);
			
		} else if (TradeBillVo.STATUS_RETURNED.equals(status)) {
			//买家，订单已退货消息
			String buyer_message = "您有一笔订单已成功退货";
			notificationService.addWMNotification(vo.getBuyer_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_RETURNED, buyer_message);

			//卖家，订单已退货消息
			String seller_message = "用户有一笔订单已成功退货";
			notificationService.addWMNotification("s", vo.getShop_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_RETURNED, seller_message);
	
		} else if (TradeBillVo.STATUS_CLOSED.equals(status)) {
			if (isAutoRun) {//系统自动确认的
				//买家，未支付订单关闭消息
				String message = "您有一笔订单超时未支付，已自动关闭";
				notificationService.addWMNotification(vo.getBuyer_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_CLOSED, message);
			} else {
				//买家，订单取消消息（货到付款退款或退货）
				String message = "您有一笔订单已取消并关闭";
				notificationService.addWMNotification(vo.getBuyer_id(), ITradeBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + TradeBillVo.STATUS_CLOSED, message);
			}
		}
		
		return sum;
	}
	
	public List<Map<String, Object>> reportGroupByBillDate(Integer city_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_BILL_DATE;
		if (city_id == null) {
			sql = sql.replace("AND city_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);
		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> reportShopGroupByBillDate(Integer city_id, Integer shop_id, String from_date, String to_date) {
		String sql = SQL_REPORT_SHOP_GROUP_BY_BILL_DATE;
		if (city_id == null) {
			sql = sql.replace("AND city_id = ? ", "");
		}
		if (shop_id == null) {
			sql = sql.replace("AND shop_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (shop_id != null) params.add(shop_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);
		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> reportManagerGroupByBillDate(Integer city_id, Integer manager_id, String from_date, String to_date) {
		String sql = SQL_REPORT_MANAGER_GROUP_BY_BILL_DATE;
		if (city_id == null) {
			sql = sql.replace("AND city_id = ? ", "");
		}
		if (manager_id == null) {
			sql = sql.replace("AND manager_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (manager_id != null) params.add(manager_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);
		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> reportGroupByCity(Integer city_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_CITY;
		if (city_id == null) {
			sql = sql.replace("AND city_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);

		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> reportGroupByManager(Integer city_id, Integer manager_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_MANAGER;
		if (city_id == null) {
			sql = sql.replace("AND city_id = ? ", "");
		}
		if (manager_id == null) {
			sql = sql.replace("AND manager_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (manager_id != null) params.add(manager_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);

		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> reportGroupByShop(Integer city_id, Integer manager_id, Integer shop_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_SHOP;
		if (city_id == null) {
			sql = sql.replace("AND city_id = ? ", "");
		}
		if (manager_id == null) {
			sql = sql.replace("AND manager_id = ? ", "");
		} else if (manager_id == 0) {
			sql = sql.replace("AND manager_id = ? ", "AND manager_id IS NULL ");
		}
		if (shop_id == null) {
			sql = sql.replace("AND shop_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (manager_id != null && manager_id != 0) params.add(manager_id);
		if (shop_id != null) params.add(shop_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> reportGroupByGoods(Integer city_id, Integer shop_id, String from_date, String to_date) {
		
		String sql = SQL_REPORT_GROUPY_BY_GOODS;

		if (city_id == null) {
			sql = sql.replace("AND t.city_id = ? ", "");
		}
		if (shop_id == null) {
			sql = sql.replace("AND t.shop_id = ? ", "");
		}
		
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (shop_id != null) params.add(shop_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);

		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> queryListDetails(String queryCondition) {
		String sql = SQL_LIST_DETAILS;
		if (StringUtils.isNotEmpty(queryCondition)) {
			sql = sql + " AND t." + queryCondition.replaceAll("and ", "AND t.");
		}
		
		sql += " ORDER BY t.id desc";
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql);
	}

	@Transactional
	public List<Map<String, Object>> reportCountGroupByStatus(
			String queryCondition) {
		String sql = SQL_REPORT_GROUP_BY_STATUS;
		if (StringUtils.isNotEmpty(queryCondition)) {
			sql = sql.replace("#WHERE#", "WHERE " + queryCondition);
		} else {
			sql = sql.replace("#WHERE#", "");
		}
		return (List<Map<String, Object>>) getDao().queryForMaps(sql);
	}

	@Override
	public List<Map<String, Object>> reportGroupBySchool(Integer city_id, Integer manager_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUP_BY_SCHOOL;
		if (city_id == null) {
			sql = sql.replace("AND city_id = ? ", "");
		}
		if (manager_id == null) {
			sql = sql.replace("AND manager_id = ? ", "");
		} else if (manager_id == 0) {
			sql = sql.replace("AND manager_id = ? ", "AND manager_id IS NULL ");
		}
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (manager_id != null && manager_id != 0) params.add(manager_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}

	@Override
	public List<Map<String, Object>> reportShopkeeper(Integer city_id, Integer manager_id, Integer school_id, String from_date, String to_date) {
		String sql = SQL_REPORT_SHOPKEEPER;
		if (city_id == null) {
			sql = sql.replace("AND city_id = ? ", "");
		}
		if (manager_id == null) {
			sql = sql.replace("AND manager_id = ? ", "");
		} else if (manager_id == 0) {
			sql = sql.replace("AND manager_id = ? ", "AND manager_id IS NULL ");
		}
		if (school_id == null) {
			sql = sql.replace("AND school_id = ? ", "");
		}
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (manager_id != null && manager_id != 0) params.add(manager_id);
		if (school_id != null) params.add(school_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
}
