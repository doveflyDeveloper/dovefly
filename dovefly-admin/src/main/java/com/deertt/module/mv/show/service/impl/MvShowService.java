package com.deertt.module.mv.show.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mv.show.dao.IMvShowDao;
import com.deertt.module.mv.show.service.IMvShowService;
import com.deertt.module.mv.show.service.IMvShowSupportService;
import com.deertt.module.mv.show.util.IMvShowConstants;
import com.deertt.module.mv.show.vo.MvShowVo;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.sys.user.vo.UserVo;
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
public class MvShowService extends DvBaseService<IMvShowDao, MvShowVo, Integer> implements IMvShowService, IMvShowConstants {
    
	@Autowired
	private IMvShowSupportService supportservice;
	
	@Autowired
	protected INotificationService notificationService;
	
	@Autowired
	protected IUserService userService;

	@Transactional
	public MvShowVo findFull(Integer id) {
		MvShowVo vo = super.find(id);
		vo.setDetails(supportservice.queryByCondition("show_id = " + id, null));
		return vo;
	}

	@Transactional
	public int deny(Integer id, String reason) {
		MvShowVo bean = this.find(id);
		
		String message = "电影剧本审核不通过，原因：" + reason;
		notificationService.addWMNotification(bean.getUser_id(), IUserConstants.TABLE_NAME + "-" + bean.getUser_id() + "-" + "hint", message);
		
		bean.setStatus(MvShowVo.STATUS_DENYED);
		return this.update(bean);
	}

	@Transactional
	public int agree(Integer id, String reason) {
        MvShowVo bean = this.find(id);
		
		String message = "电影剧本审核通过，原因：" + reason;
		UserVo user = userService.find(bean.getUser_id());
		
/*		 送优惠券 CouponOwner 20元  有效时间1个月
		    
		    int num= (int)(20+Math.random()*(40-20+1));
			CouponOwnerVo coupon = new CouponOwnerVo();
			coupon.setCity_id(user.getCity_id());
			coupon.setCity_name(user.getCity_name());
			coupon.setUser_id(user.getId());
			coupon.setUser_name(user.getReal_name());
			coupon.setCoupon_tpl_id(8);
			coupon.setCoupon_name("电影审核通过优惠券");
			coupon.setCoupon_type("2");
			coupon.setCoupon_rule("{\"discount\":" + num + "}");
			coupon.setCoupon_desc(num + "元优惠券");
			coupon.setCoupon_discount(new BigDecimal(num));
			coupon.setCoupon_start_time(DvDateHelper.getTimestamp(DvDateHelper.formatDate(new Date(), "yyyy-MM-dd")));
			coupon.setCoupon_end_time(new Timestamp(coupon.getCoupon_start_time().getTime() + 1000L*60*60*24*30));
			coupon.setCoupon_use_status("0");
			coupon.setStatus("1");
		couponService.insert(coupon);
		notificationService.addWMNotification(user.getId(), IUserConstants.TABLE_NAME + "-" + user.getId() + "-" + "hint", message);*/
		
		bean.setStatus(MvShowVo.STATUS_SUCCESS);
		return this.update(bean);
	}
	

}
