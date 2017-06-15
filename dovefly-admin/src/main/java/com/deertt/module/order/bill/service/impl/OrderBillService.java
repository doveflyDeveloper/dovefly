package com.deertt.module.order.bill.service.impl;

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
import com.deertt.module.mm.goods.service.IGoodsWService;
import com.deertt.module.order.bill.dao.IOrderBillDao;
import com.deertt.module.order.bill.service.IOrderBillService;
import com.deertt.module.order.bill.service.IOrderDetailService;
import com.deertt.module.order.bill.util.IOrderBillConstants;
import com.deertt.module.order.bill.vo.OrderBillVo;
import com.deertt.module.order.bill.vo.OrderDetailVo;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
import com.deertt.utils.helper.date.DvDateHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class OrderBillService extends DvBaseService<IOrderBillDao, OrderBillVo, Integer> implements IOrderBillService, IOrderBillConstants {

	@Autowired
	private IOrderDetailService detailService;
	
	@Autowired
	private IGoodsWService goodsWService;
	
	@Autowired
	private IGoodsSService goodsSService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IShopService shopService;
	
	@Autowired
	private IWarehouseService warehouseService;
	
	@Autowired
	private INotificationService notificationService;
	
	@Autowired
	private IRefundService refundService;
	
    @Transactional
    public int saveFull(OrderBillVo vo) {
        if (vo.isNew()) {
            return this.insertFull(vo);
        } else {
            return this.updateFull(vo);
        }
    }
	
	@Transactional
	public int insertFull(OrderBillVo vo) {
		BigDecimal total_amount = BigDecimal.ZERO;
		BigDecimal quantity = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (OrderDetailVo d : vo.getDetails()) {
				total_amount = total_amount.add(d.getSub_total());
				quantity = quantity.add(d.getQuantity());
			}
		}
		vo.setTotal_amount(total_amount);
		vo.setReal_amount(total_amount);
		vo.setIncome_amount(total_amount);
		vo.setQuantity(quantity);
		vo.setUse_coin_amount(new BigDecimal(vo.getUse_coin_quantity() * 0.1));
		
		if (vo.getUse_coin_quantity() > 0) {
			vo.setReal_amount(total_amount.subtract(new BigDecimal(vo.getUse_coin_quantity()*0.1)));
			vo.setIncome_amount(vo.getReal_amount());
		}
		int sum = super.insert(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}
	
	@Transactional
	public int updateFull(OrderBillVo vo) {
		BigDecimal total_amount = BigDecimal.ZERO;
		BigDecimal quantity = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (OrderDetailVo d : vo.getDetails()) {
				total_amount = total_amount.add(d.getSub_total());
				quantity = quantity.add(d.getQuantity());
			}
		}
		vo.setTotal_amount(total_amount);
		vo.setReal_amount(total_amount);
		vo.setIncome_amount(total_amount);
		vo.setQuantity(quantity);
		vo.setUse_coin_amount(new BigDecimal(vo.getUse_coin_quantity() * 0.1));
		
		if (vo.getUse_coin_quantity() > 0) {
			vo.setReal_amount(total_amount.subtract(new BigDecimal(vo.getUse_coin_quantity()*0.1)));
			vo.setIncome_amount(vo.getReal_amount());
		}
		int sum = super.update(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}

	public OrderBillVo findFull(Integer id) {
		OrderBillVo vo = super.find(id);
		vo.setDetails(detailService.queryByCondition("bill_id = " + id, null));
		return vo;
	}
	
	@Transactional
	public int makeOrder(OrderBillVo vo) {
		vo.setStatus(OrderBillVo.STATUS_EDIT);
		int sum = this.insertFull(vo);
		
		//锁定卖家的库存
		if (vo.getDetails() != null) {
			for (OrderDetailVo detail : vo.getDetails()) {
				goodsWService.lockStockSumToStockLocked(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		
		return sum;
	}
	
	@Transactional
	public int closeUnSubmitBill(Integer id) {
		OrderBillVo vo = this.findFull(id);
		
		if (!OrderBillVo.STATUS_EDIT.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于未提交状态，不可关闭！");
		}
		
		//释放锁定的库存
		if (vo.getDetails() != null) {
			for (OrderDetailVo detail : vo.getDetails()) {
				goodsWService.turnbackStockLockedToStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		
		//如果使用汀豆，则返回买家
		if (vo.getUse_coin_quantity() > 0) {
			userService.addCoin_quantity(shopService.find(vo.getShop_id()).getShopkeeper_id(), vo.getUse_coin_quantity());
		}
		
		//设置为已关闭
		return this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_CLOSED, true);
	}
	
	@Transactional
	public int codPayAndSubmit(Integer id) {
		OrderBillVo bean = this.findFull(id);
		
		if (!OrderBillVo.STATUS_EDIT.equals(bean.getStatus())) {
			throw new BusinessException("订单不处于未提交状态，不可提交！");
		}
		
		//更新支付方式及支付状态
		bean.setPay_type(OrderBillVo.PAY_TYPE_COD);
		bean.setPay_status(OrderBillVo.PAY_STATUS_NO);
		this.updatePayInfo(bean);
		
		//设置订单为已提交
		return this.updateBillStatusAndSendMessage(bean, OrderBillVo.STATUS_SUBMIT, false);
	}
    
	@Transactional
	public int paySuccessCallBack(Integer id, String pay_type, String pay_code, BigDecimal pay_amount, Timestamp pay_time) {
		OrderBillVo bean = this.findFull(id);
		if (!OrderBillVo.STATUS_EDIT.equals(bean.getStatus())) {
			throw new BusinessException("订单不处于未提交状态，不可提交！");
		}
		
		//如果是汀汀支付，则从买家扣款，其他支付方式则是支付成功后再回调此方法
		if (OrderBillVo.PAY_TYPE_TTPAY.equals(pay_type)) {
			shopService.reduceBalance_amount(bean.getShop_id(), bean.getBill_code(), pay_code, pay_amount, "订单【" + bean.getBill_code() + "】支付成功，支出金额：" + pay_amount + "元。", "");
		}
		
		//卖家收款至待收款部分
		warehouseService.addHalfway_amount(bean.getWarehouse_id(), bean.getIncome_amount());
			
		//更新支付方式及支付状态
		bean.setPay_type(pay_type);
		bean.setPay_code(pay_code);
		bean.setPay_amount(pay_amount);
		bean.setPay_time(pay_time);
		bean.setPay_status(OrderBillVo.PAY_STATUS_SUCCESS);
		this.updatePayInfo(bean);
		
		//设置订单为已提交
		return this.updateBillStatusAndSendMessage(bean, OrderBillVo.STATUS_SUBMIT, false);		
	}

	@Transactional
	public int deliver(OrderBillVo vo) {
		if (!(OrderBillVo.STATUS_SUBMIT.equals(vo.getStatus()) || OrderBillVo.STATUS_APPLY_FOR_REFUND.equals(vo.getStatus()))) {
			throw new BusinessException("订单不处于已提交或申请退款状态，不可进行发货操作！");
		}
		
		//卖家扣减锁定库存
		if (vo.getDetails() != null) {
			for (OrderDetailVo detail : vo.getDetails()) {
				goodsWService.reduceStockLocked(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		
		//设置订单为已发货状态
		return this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_DELIVERED, false);
	}
	
	@Transactional
	public int receive(Integer id, boolean isAutoReceive) {
		OrderBillVo vo = this.findFull(id);
		
		if (!OrderBillVo.STATUS_DELIVERED.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于已发货状态，不可进行确认收货操作！");
		}
		
		//商品入买家库存
		if (vo.getDetails() != null) {
			for (OrderDetailVo detail : vo.getDetails()) {
				goodsSService.addStockSum(vo.getShop_id(), detail.getGoods_id(), detail.getQuantity(), null, detail.getSale_price(), vo.getBill_code());
			}
		}
		
		//订单金额从卖家待收款账户进入已收款账户
		if (OrderBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
			warehouseService.reduceHalfway_amount(vo.getWarehouse_id(), vo.getIncome_amount());
			warehouseService.addBalance_amount(vo.getWarehouse_id(), vo.getBill_code(), vo.getPay_code(), vo.getIncome_amount(), "订单【" + vo.getBill_code() + "】交易完成，进账：" + vo.getIncome_amount() + "元。", "");
		}
		
		//TODO 此处反送给用户汀豆
		
//		if (vo.getCity_id() == 3 
//				&& vo.getSubmit_time().compareTo(DvDateHelper.getTimestamp("2016-11-16 00:00:00")) > 0) {//目前临时只支持郑州返券
//			Integer tpl_id = null;
//			if (vo.getTotal_amount().compareTo(new BigDecimal(1000)) >= 0) {
//				tpl_id = 13;
//			} else if (vo.getTotal_amount().compareTo(new BigDecimal(500)) >= 0) {
//				tpl_id = 12;
//			} else if (vo.getTotal_amount().compareTo(new BigDecimal(200)) >= 0) {
//				tpl_id = 11;
//			}
//			
//			if (tpl_id != null) {
//				CouponTemplateVo ct = couponTemplateService.find(tpl_id);
//				if (CouponTemplateVo.TYPE_FD.equals(ct.getType())) {
//					//TODO 暂时先只支持减免类型
//					CouponOwnerVo c = new CouponOwnerVo();
//					c.setCity_id(vo.getCity_id());
//					c.setCity_name(vo.getCity_name());
//					c.setUser_id(vo.getShop_id());
//					c.setUser_name(vo.getShop_name());
//					c.setIssuer_id(ct.getIssuer_id());
//					c.setIssuer_name(ct.getIssuer_name());
//					c.setCoupon_tpl_id(ct.getId());
//					c.setCoupon_name(ct.getName());
//					c.setCoupon_scope(ct.getScope());
//					c.setCoupon_type(ct.getType());
//					//{"fd_limit_amount":"200","fd_discount_amount":"5"}
//					JSONObject rule = JSONObject.fromObject(ct.getRule());
//					c.setCoupon_discount(new BigDecimal(rule.optInt("fd_discount_amount", 0)));
//					c.setCoupon_rule("{\"line\":" + rule.optInt("fd_limit_amount", 0) + ",\"discount\":" + c.getCoupon_discount() + "}");
//					c.setCoupon_desc("订单满" + rule.optInt("fd_limit_amount", 0) + "元，立减" + c.getCoupon_discount() + "元优惠券");
//					c.setCoupon_start_time(DvDateHelper.getSysTimestamp());
//					c.setCoupon_end_time(new Timestamp(DvDateHelper.getSysTimestamp().getTime() + 1000L*60*60*24*ct.getPeriod() - 1));
//					c.setCoupon_use_status("0");
//					c.setStatus("1");
//					c.setRemark(vo.getBill_code());
//					
//					couponOwnerService.insert(c);
//					
//					String message = "恭喜您获得一张" + c.getCoupon_name() + "，下次下单时可用";
//					notificationService.addWMNotification(c.getUser_id(), ICouponOwnerConstants.TABLE_NAME + "-" + c.getId() + "-" + "send", message);
//					
//				}
//			}
//		}
		
		//设置订单为已收货
		return this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_RECEIVED, isAutoReceive);
	}
	
	@Transactional
	public int agreeRefund(Integer id) {
		int sum = 0;
		OrderBillVo vo = this.findFull(id);
		
		if (!OrderBillVo.STATUS_APPLY_FOR_REFUND.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于申请退款状态，不可同意退款处理！");
		}
		
		if (!OrderBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
			throw new BusinessException("订单并未付款，不可同意退款处理！");
		}
		
//		2016-11-29，店长退款都退回至汀汀账户
//		if (OrderBillVo.PAY_TYPE_TTPAY.equals(vo.getPay_type())) {//汀汀支付，直接退款

    		//如果使用汀豆，则返回买家
    		if (vo.getUse_coin_quantity() > 0) {
    			userService.addCoin_quantity(shopService.find(vo.getShop_id()).getShopkeeper_id(), vo.getUse_coin_quantity());
    		}
    		
    		//订单返回此订单锁定数量
    		if (vo.getDetails() != null) {
    			for (OrderDetailVo detail : vo.getDetails()) {
    				goodsWService.turnbackStockLockedToStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
    			}
    		}
    		
    		//扣减卖家待收款金额
    		if (OrderBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
    			warehouseService.reduceHalfway_amount(vo.getWarehouse_id(), vo.getIncome_amount());
    		}
    		
    		//买家退款至汀汀账户
    		String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
    		shopService.addOrderTurnbackAmount(vo.getShop_id(), vo.getBill_code(), pay_code, vo.getPay_amount(), "订单【" + vo.getBill_code() + "】已取消，有退款：" + vo.getPay_amount() + "元。");
    		
    		//更新订单退款状态为已退款
    		vo.setRefund_type(OrderBillVo.REFUND_TYPE_TTPAY);
    		vo.setRefund_code(pay_code);
    		vo.setRefund_amount(vo.getPay_amount());
    		vo.setRefund_time(DvDateHelper.getSysTimestamp());
    		vo.setRefund_status(OrderBillVo.REFUND_STATUS_SUCCESS);
    		this.updateRefundInfo(vo);
    		
    		//设置订单为已取消状态
    		sum = this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_REFUNDED, false);
//		} else {//其他支付，走退款流程
//			
//			//增加退款请求
//			refundService.addRefund(vo.getWarehouse_id(), vo.getSeller_name(), vo.getShop_id(), , vo.getBill_code(), vo.getPay_type(), vo.getPay_amount());
//			
//			//设置订单为同意退款
//			sum = this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_AGREE_TO_REFUND, false);			
//		}
		
		return sum;
	}
	
	@Transactional
	public int agreeReturn(Integer id) {
		int sum = 0;
		OrderBillVo vo = this.findFull(id);
		
		if (!OrderBillVo.STATUS_APPLY_FOR_RETURN.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于申请退货状态，不可同意退货处理！");
		}
		
		if (!OrderBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
			throw new BusinessException("订单并未付款，不可同意退款处理！");
		}
		
//		2016-11-29，店长退款都退回至汀汀账户
//		if (OrderBillVo.PAY_TYPE_TTPAY.equals(vo.getPay_type())) {//汀汀支付，直接退款

			//如果使用汀豆，则返回买家
			if (vo.getUse_coin_quantity() > 0) {
				userService.addCoin_quantity(shopService.find(vo.getShop_id()).getShopkeeper_id(), vo.getUse_coin_quantity());
			}
    		
    		//订单返回此订单锁定数量
    		if (vo.getDetails() != null) {
    			for (OrderDetailVo detail : vo.getDetails()) {
    				//已发货，卖家已扣库存，而买家还未收入库存
    				goodsWService.addStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
    			}
    		}
    		
    		//扣减卖家待收款金额
    		if (OrderBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
    			warehouseService.reduceHalfway_amount(vo.getWarehouse_id(), vo.getIncome_amount());
    		}
    		
    		//买家退款至汀汀账户
    		String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
    		shopService.addOrderTurnbackAmount(vo.getShop_id(), vo.getBill_code(), pay_code, vo.getPay_amount(), "订单【" + vo.getBill_code() + "】已取消，有退款：" + vo.getPay_amount() + "元。");
    		
    		//更新订单退款状态为已退款
    		vo.setRefund_type(OrderBillVo.REFUND_TYPE_TTPAY);
    		vo.setRefund_code(pay_code);
    		vo.setRefund_amount(vo.getPay_amount());
    		vo.setRefund_time(DvDateHelper.getSysTimestamp());
    		vo.setRefund_status(OrderBillVo.REFUND_STATUS_SUCCESS);
    		this.updateRefundInfo(vo);
    		
    		//设置订单为已取消状态
    		sum = this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_RETURNED, false);			
//		} else {//其他支付，走退款流程
//			
//			//增加退款请求
//			refundService.addRefund(vo.getWarehouse_id(), vo.getSeller_name(), vo.getShop_id(), , vo.getBill_code(), vo.getPay_type(), vo.getPay_amount());
//			
//			//设置订单为同意退货
//			sum = this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_AGREE_TO_RETURN, false);
//		}
		
		return sum;
	}
	
	@Transactional
	public int denyReturn(Integer id) {
		OrderBillVo vo = this.findFull(id);
		
		if (!OrderBillVo.STATUS_APPLY_FOR_RETURN.equals(vo.getStatus())) {
			throw new BusinessException("订单不处于申请退货状态，不可拒绝退货处理！");
		}
		
		//设置订单为已发货
		//TODO 这地方应该是发送拒绝退货的消息
		return this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_DELIVERED, false);
	}
	
	@Transactional
	public int refunding(String bill_code, String refund_type, String refund_code, BigDecimal refund_amount, Timestamp refund_time) {
		OrderBillVo vo = this.findByCode(bill_code);
		
		if (!(OrderBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus()) || OrderBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus()))) {
			throw new BusinessException("订单不处于同意退款或同意退货状态，退款回调失败！");
		}
		
		//更新退款请求为退款处理中
		RefundVo refund = refundService.findByReferBillCode(bill_code);
		refund.setPay_code(refund_code);
		refund.setPay_time(refund_time);
		refund.setPay_status(RefundVo.PAY_STATUS_DEALING);
		refund.setStatus(RefundVo.STATUS_DEALING);
		refundService.update(refund);
		
		//更新订单退款状态为退款中
		vo.setRefund_type(refund_type);
		vo.setRefund_code(refund_code);
		vo.setRefund_amount(refund_amount);
		vo.setRefund_time(refund_time);
		vo.setRefund_status(OrderBillVo.REFUND_STATUS_ING);
		return this.updateRefundInfo(vo);
	}
	
	@Transactional
	public int refundSuccessCallBack(String bill_code) {
		int sum = 0;
		OrderBillVo vo = this.findByCode(bill_code);
		vo = this.findFull(vo.getId());
		
		if (!(OrderBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus()) || OrderBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus()))) {
			throw new BusinessException("订单不处于同意退款或同意退货中状态，退款回调失败！");
		}
		
		//如果使用优惠券，则重置优惠券为未使用状态
		//如果使用汀豆，则返回买家
		if (vo.getUse_coin_quantity() > 0) {
			userService.addCoin_quantity(shopService.find(vo.getShop_id()).getShopkeeper_id(), vo.getUse_coin_quantity());
		}
		
		//订单返回此订单锁定数量
		if (vo.getDetails() != null) {
			for (OrderDetailVo detail : vo.getDetails()) {
				if (OrderBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus())) {//退款，未发货，未扣减库存，只需释放锁定库存
					goodsWService.turnbackStockLockedToStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
				} else if (OrderBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus())) {//退货，已发货，未收货，已扣减库存，需要重新加回库存
					goodsWService.addStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
				}
			}
		}
		
		//扣减卖家待收款金额
		if (OrderBillVo.PAY_STATUS_SUCCESS.equals(vo.getPay_status())) {
			warehouseService.reduceHalfway_amount(vo.getWarehouse_id(), vo.getIncome_amount());
		}
		
		//更新退款申请记录
		RefundVo refund = refundService.findByReferBillCode(bill_code);
		refund.setPay_status(RefundVo.PAY_STATUS_SUCCESS);
		refund.setStatus(RefundVo.STATUS_SUCCESS);
		refundService.update(refund);
		
		//更新订单退款状态
		vo.setRefund_status(OrderBillVo.REFUND_STATUS_SUCCESS);
		this.updateRefundInfo(vo);
		
		//设置订单为已退歀或已退货状态
		if (OrderBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus())) {
			sum = this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_REFUNDED, false);
		} else if (OrderBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus())) {
			sum = this.updateBillStatusAndSendMessage(vo, OrderBillVo.STATUS_RETURNED, false);
		}
		
		return sum;
	}
	
	@Transactional
	public int refundFailCallBack(String bill_code, String pay_msg) {
		OrderBillVo vo = this.findByCode(bill_code);
		
		if (!(OrderBillVo.STATUS_AGREE_TO_REFUND.equals(vo.getStatus()) || OrderBillVo.STATUS_AGREE_TO_RETURN.equals(vo.getStatus()))) {
			throw new BusinessException("订单不处于退款或退货中状态，退款回调失败！");
		}
		
		//更新退款请求记录
		RefundVo refund = refundService.findByReferBillCode(bill_code);
		refund.setPay_status(RefundVo.PAY_STATUS_FAIL);
		refund.setStatus(RefundVo.STATUS_FAIL);
		refund.setPay_msg(pay_msg);
		refundService.update(refund);
		
		//更新订单退款记录
		vo.setRefund_status(OrderBillVo.REFUND_STATUS_FAIL);
		return this.updateRefundInfo(vo);
	}
	
	public OrderBillVo findByCode(String bill_code) {
		return getDao().findByCondition("bill_code = '" + bill_code + "'");
	}
	
	@Transactional
	public int updatePayInfo(OrderBillVo vo) {
		return getDao().updatePayInfo(vo);
	}
	
	@Transactional
	public int updateRefundInfo(OrderBillVo vo) {
		return getDao().updateRefundInfo(vo);
	}
	
	/**
	 * 
	 * @param vo
	 * @param status
	 * @return
	 */
	@Transactional
	public int updateBillStatusAndSendMessage(OrderBillVo vo, String status, boolean isAutoRun) {
		int sum = 0;
		String sql = SQL_UPDATE_STATUS_BY_ID;
		if (OrderBillVo.STATUS_SUBMIT.equals(status)) {
			sql = SQL_SUBMIT_BY_ID;
		} else if (OrderBillVo.STATUS_DELIVERED.equals(status)) {
			sql = SQL_DELIVER_BY_ID;
		} else if (OrderBillVo.STATUS_RECEIVED.equals(status)) {
			sql = SQL_RECEIVE_BY_ID;
		}
		
		sum = getDao().update(sql, new Object[]{ status, vo.getId() });
		
		//发送通知消息
		if (OrderBillVo.STATUS_SUBMIT.equals(status)) {
			//给卖家发送新订单消息
			String message = "新一笔订单待发货";
			notificationService.addWMNotification("w", vo.getWarehouse_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_SUBMIT, message);
		} else if (OrderBillVo.STATUS_DELIVERED.equals(status)) {
			//买家，订单已发货消息
			String message = "您有一笔订单已发货";
			notificationService.addWMNotification("s", vo.getShop_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_DELIVERED, message);
			
		} else if (OrderBillVo.STATUS_RECEIVED.equals(status)) {
			if (isAutoRun) {//系统自动确认的
				//买家，订单自动确认收货消息
				String buyer_message = "您有一笔订单超时未确认收货，系统已自动确认收货";
				
				notificationService.addWMNotification("s", vo.getShop_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_RECEIVED, buyer_message);

				//卖家，订单自动确认收货消息
				String seller_message = "店长有一笔订单超时未确认收货，系统已自动确认收货";
				notificationService.addWMNotification("w", vo.getWarehouse_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_RECEIVED, seller_message);
			} else {
				//卖家，订单已收货消息
				String message = "店长有一笔订单已确认收货";
				notificationService.addWMNotification("w", vo.getWarehouse_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_RECEIVED, message);
			}
			
		} else if (OrderBillVo.STATUS_APPLY_FOR_REFUND.equals(status)) {
			//卖家，订单申请退歀消息
			String message = "店长有一笔订单申请退歀";
			notificationService.addWMNotification("w", vo.getWarehouse_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_APPLY_FOR_REFUND, message);

		} else if (OrderBillVo.STATUS_APPLY_FOR_RETURN.equals(status)) {
			//卖家，订单申请退货消息
			String message = "店长有一笔订单申请退货";
			notificationService.addWMNotification("w", vo.getWarehouse_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_APPLY_FOR_RETURN, message);
			
		} else if (OrderBillVo.STATUS_AGREE_TO_REFUND.equals(status)) {
			//买家，订单申请退货消息
			String message = "您有一笔订单卖家已同意退款";
			notificationService.addWMNotification("s", vo.getShop_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_AGREE_TO_REFUND, message);
			
		} else if (OrderBillVo.STATUS_AGREE_TO_RETURN.equals(status)) {
			//买家，订单申请退货消息
			String message = "您有一笔订单卖家已同意退货";
			notificationService.addWMNotification("s", vo.getShop_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_AGREE_TO_RETURN, message);

		} else if (OrderBillVo.STATUS_REFUNDED.equals(status)) {
			//买家，订单已退款消息
			String buyer_message = "您有一笔订单已成功退款";
			notificationService.addWMNotification("s", vo.getShop_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_REFUNDED, buyer_message);

			//卖家，订单已退款消息
			String seller_message = "店长有一笔订单已成功退款";
			notificationService.addWMNotification("w", vo.getWarehouse_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_REFUNDED, seller_message);
			
		} else if (OrderBillVo.STATUS_RETURNED.equals(status)) {
			//买家，订单已退货消息
			String buyer_message = "您有一笔订单已成功退货";
			notificationService.addWMNotification("s", vo.getShop_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_RETURNED, buyer_message);

			//卖家，订单已退货消息
			String seller_message = "店长有一笔订单已成功退货";
			notificationService.addWMNotification("w", vo.getWarehouse_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_RETURNED, seller_message);
	
		} else if (OrderBillVo.STATUS_CLOSED.equals(status)) {
			//买家，未支付订单关闭消息
			String message = "您有一笔订单超时未支付，已自动关闭";
			notificationService.addWMNotification("s", vo.getShop_id(), IOrderBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBillVo.STATUS_CLOSED, message);
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
	
	public List<Map<String, Object>> reportGroupByGoods(Integer city_id, Integer warehouse_id, Integer shop_id, String from_date, String to_date) {
		
		String sql = SQL_REPORT_GROUPY_BY_GOODS;

		if (city_id == null) {
			sql = sql.replace("AND o.city_id = ? ", "");
		}
		if (warehouse_id == null) {
			sql = sql.replace("AND o.warehouse_id = ? ", "");
		}
		if (shop_id == null) {
			sql = sql.replace("AND o.shop_id = ? ", "");
		}
		
		List<Object> params = new ArrayList<Object>();
		if (city_id != null) params.add(city_id);
		if (warehouse_id != null) params.add(warehouse_id);
		if (shop_id != null) params.add(shop_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);

		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
	public List<Map<String, Object>> queryListDetails(String queryCondition) {
		String sql = SQL_LIST_DETAILS;
		if (StringUtils.isNotEmpty(queryCondition)) {
			sql = sql + " AND o." + queryCondition.replaceAll("and ", "AND o.");
		}
		
		sql += " ORDER BY o.id desc";
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql);
	}

	@Override
	public List<Map<String, Object>> reportCountGroupByStatus(String queryCondition) {
		String sql = SQL_REPORT_GROUP_BY_STATUS;
		if (StringUtils.isNotEmpty(queryCondition)) {
			sql = sql.replace("#WHERE#", "WHERE " + queryCondition);
		} else {
			sql = sql.replace("#WHERE#", "");
		}
		return (List<Map<String, Object>>) getDao().queryForMaps(sql);
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

	@Override
	public List<Map<String, Object>> reportShopGroupByGoods(Integer goods_id, String from_date, String to_date) {
		String sql = SQL_REPORT_GROUPY_BY_ORDER_GOODS;
		
		if (goods_id == null) {
			sql = sql.replace("AND goods_id = ? ", "");
		}
		
		List<Object> params = new ArrayList<Object>();
		if (goods_id != null) params.add(goods_id);
		if (from_date != null) params.add(from_date);
		if (to_date != null) params.add(to_date);
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql, params.toArray());
	}
	
}
