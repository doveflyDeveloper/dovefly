package com.deertt.module.order.back.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mm.goods.service.IGoodsSService;
import com.deertt.module.mm.goods.service.IGoodsWService;
import com.deertt.module.order.back.dao.IOrderBackBillDao;
import com.deertt.module.order.back.service.IOrderBackBillService;
import com.deertt.module.order.back.service.IOrderBackDetailService;
import com.deertt.module.order.back.util.IOrderBackBillConstants;
import com.deertt.module.order.back.vo.OrderBackBillVo;
import com.deertt.module.order.back.vo.OrderBackDetailVo;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.shop.service.IShopService;
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
public class OrderBackBillService extends DvBaseService<IOrderBackBillDao, OrderBackBillVo, Integer> implements IOrderBackBillService, IOrderBackBillConstants {

	@Autowired
	private IOrderBackDetailService detailService;
	
	@Autowired
	private IGoodsWService goodsWService;
	
	@Autowired
	private IGoodsSService goodsSService;
	
	@Autowired
	private IWarehouseService warehouseService;
	
	@Autowired
	private IShopService shopService;
	
	@Autowired
	private INotificationService notificationService;
	
    @Transactional
    public int saveFull(OrderBackBillVo vo) {
        if (vo.isNew()) {
            return this.insertFull(vo);
        } else {
            return this.updateFull(vo);
        }
    }
	
	@Transactional
	public int insertFull(OrderBackBillVo vo) {
		BigDecimal amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (OrderBackDetailVo d : vo.getDetails()) {
				amount = amount.add(d.getSub_total());
			}
			vo.setAmount(amount);
		}
		int sum = super.insert(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}

	@Transactional
	public OrderBackBillVo findFull(Integer id) {
		OrderBackBillVo vo = super.find(id);
		vo.setDetails(detailService.queryByCondition("bill_id = " + id, null));
		return vo;
	}

	@Transactional
	public int updateFull(OrderBackBillVo vo) {
		BigDecimal amount = BigDecimal.ZERO;
		if (vo.getDetails() != null) {
			for (OrderBackDetailVo d : vo.getDetails()) {
				amount = amount.add(d.getSub_total());
			}
			vo.setAmount(amount);
		}
		int sum = super.update(vo);
		detailService.insertUpdateBatch(vo.getDetails(), vo.getId());
		return sum;
	}
	
	@Transactional
	public int deleteFull(Integer id) {
		detailService.deleteByBill(id);
		return super.delete(id);
	}
	
    @Transactional
    public int deleteFull(Integer[] ids) {
        int sum = 0;
        for (Integer id : ids) {
            detailService.deleteByBill(id);
            sum += super.delete(id);
        }
        return sum;
    }
    
	@Transactional
	public int saveAndSubmit(OrderBackBillVo vo) {
		int sum = 0;
		
		this.insertFull(vo);
		
		//锁定店长商品库存
		if (vo.getDetails() != null) {
			for (OrderBackDetailVo detail : vo.getDetails()) {
				goodsSService.lockStockSumToStockLocked(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		
		sum = this.updateStatus(vo.getId(), OrderBackBillVo.STATUS_SUBMIT);
		
		//给卖家发送新退货单消息
		String message = "您有一笔新店长退货单待处理";
		notificationService.addWMNotification("w", vo.getWarehouse_id(), IOrderBackBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + "submit", message);
		return sum;
	}
	
	@Transactional
	public int receive(Integer id) {
		int sum = 0;
		OrderBackBillVo vo = this.findFull(id);
		if (!OrderBackBillVo.STATUS_SUBMIT.equals(vo.getStatus())) {
			throw new BusinessException("订单状态异常！");
		}
		
		//退货方释放库存，收货方加库存
		if (vo.getDetails() != null) {
			for (OrderBackDetailVo detail : vo.getDetails()) {
				goodsSService.reduceStockLocked(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
				goodsWService.addStockSum(detail.getOrigin_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		
		//清算资金，退款到店长，目前全部退还到账户
		BigDecimal tradeAmount = vo.getAmount();
		
		//城市经理扣款
		String pay_code = DvDateHelper.getJoinedSysDateTime() + String.format("%07d", Math.round(Math.random() * 10000000)) + String.format("%07d", Math.round(Math.random() * 10000000));
		warehouseService.reduceBalance_amount(vo.getWarehouse_id(), vo.getBill_code(), pay_code, tradeAmount, "退货单【" + vo.getBill_code() + "】交易完成，支付退款：" + tradeAmount + "元。", "");
		
		//店长进账
		shopService.addBalance_amount(vo.getShop_id(), vo.getBill_code(), pay_code, tradeAmount, "退货单【" + vo.getBill_code() + "】交易完成，有退款：" + tradeAmount + "元。", "");
		
		vo.setPay_type(OrderBackBillVo.PAY_TYPE_TTPAY);
		vo.setPay_code(pay_code);
		vo.setPay_amount(tradeAmount);
		vo.setPay_time(DvDateHelper.getSysTimestamp());
		vo.setPay_status(OrderBackBillVo.PAY_STATUS_SUCCESS);
		this.updatePayInfo(vo);
		sum = this.updateStatus(id, OrderBackBillVo.STATUS_RECEIVED);
		
		//给买家发送退款消息
		String message = "您有一笔退货单已确认，退款已发至您小鹿账户";
		notificationService.addWMNotification("s", vo.getShop_id(), IOrderBackBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBackBillVo.STATUS_RECEIVED, message);
		
		return sum;
	}
	
	@Transactional
	public int reject(Integer id) {
		int sum = 0;
		OrderBackBillVo vo = this.findFull(id);
		if (!OrderBackBillVo.STATUS_SUBMIT.equals(vo.getStatus())) {
			throw new BusinessException("订单状态异常！");
		}
		
		if (vo.getDetails() != null) {
			for (OrderBackDetailVo detail : vo.getDetails()) {
				goodsWService.turnbackStockLockedToStockSum(detail.getGoods_id(), detail.getQuantity(), vo.getBill_code());
			}
		}
		sum = this.updateStatus(id, OrderBackBillVo.STATUS_REJECTED);
		
		//给卖家发送新退货单消息
		String message = "您有一笔退货单申请被退回";
		notificationService.addWMNotification("w", vo.getWarehouse_id(), IOrderBackBillConstants.TABLE_NAME + "-" + vo.getId() + "-" + OrderBackBillVo.STATUS_REJECTED, message);
		return sum;
	}
	
	public OrderBackBillVo findByCode(String bill_code) {
		return getDao().findByCondition("bill_code = '" + bill_code + "'");
	}
	
	@Transactional
	public int updatePayInfo(OrderBackBillVo vo) {
		return getDao().updatePayInfo(vo);
	}
	
	@Transactional
	public int updateStatus(Integer id, String status) {
		return getDao().update(SQL_UPDATE_STATUS_BY_ID, new Object[]{ status, id });
	}
	
	public List<Map<String, Object>> queryListDetails(String queryCondition) {
		String sql = SQL_LIST_DETAILS;
		if (StringUtils.isNotEmpty(queryCondition)) {
			sql = sql + " AND o." + queryCondition.replaceAll("and ", "AND o.");
		}
		
		sql += " ORDER BY o.id desc";
		
		return (List<Map<String, Object>>) getDao().queryForMaps(sql);
	}

}
