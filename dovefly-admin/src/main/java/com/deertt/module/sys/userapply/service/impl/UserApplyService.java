package com.deertt.module.sys.userapply.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.role.vo.RoleVo;
import com.deertt.module.sys.school.service.ISchoolService;
import com.deertt.module.sys.school.vo.SchoolVo;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.shop.vo.ShopVo;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.sys.user.vo.UserVo;
import com.deertt.module.sys.userapply.dao.IUserApplyDao;
import com.deertt.module.sys.userapply.service.IUserApplyService;
import com.deertt.module.sys.userapply.util.IUserApplyConstants;
import com.deertt.module.sys.userapply.vo.UserApplyVo;
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
public class UserApplyService extends DvBaseService<IUserApplyDao, UserApplyVo, Integer> implements IUserApplyService, IUserApplyConstants {
	
	@Autowired
	protected IUserService userService;
	
	@Autowired
	protected ISchoolService schoolService;
	
	@Autowired
	protected IShopService shopService;
	
	@Autowired
	protected INotificationService notificationService;
	
	public UserApplyVo findFull(Integer id) {
		UserApplyVo vo = super.find(id);
		vo.setUser(userService.findFull(vo.getUser_id()));
		return vo;
	}
	
	@Transactional
	public int pass(Integer id) {
		UserApplyVo apply = this.find(id);
		if (!UserApplyVo.STATUS_NO.equals(apply.getStatus())) {
			throw new BusinessException("申请记录已处理，不能重复处理！");
		}
		
		UserVo user = userService.findFull(apply.getUser_id());
		if (user.getShop() != null) {
			throw new BusinessException("此用户之前已开通店铺，不能重复申请开店！");
		}
		
		SchoolVo school = schoolService.find(apply.getSchool_id());
		
		ShopVo shop = new ShopVo();
		shop.setCity_id(school.getCity_id());
		shop.setCity_name(school.getCity_name());
		shop.setWarehouse_id(school.getWarehouse_id());
		shop.setWarehouse_name(school.getWarehouse_name());
		shop.setManager_id(school.getManager_id());
		shop.setManager_name(school.getManager_name());
		shop.setSchool_id(school.getId());
		shop.setSchool_name(school.getSchool_name());
		shop.setShopkeeper_id(user.getId());
		shop.setShopkeeper_name(user.getReal_name());
		shop.setShop_name(apply.getUser_name() + "的宿舍店");
		shop.setShop_desc("");
		shop.setShop_area("");
		shop.setShop_status(ShopVo.WORK_STATUS_N);
		shop.setShop_sort(10000);
		shop.setStart_amount(new BigDecimal(5));
		shop.setBalance_amount(new BigDecimal(0));
		shop.setLocked_amount(new BigDecimal(0));
		shop.setHalfway_amount(new BigDecimal(0));
		shop.setLoanable_amount(BigDecimal.ZERO);
		shop.setLoan_amount(BigDecimal.ZERO);
		shop.setInterest_amount(BigDecimal.ZERO);
		shop.setCreate_at(DvDateHelper.getSysTimestamp());
		shop.setCreate_by(LoginHelper.getUserId());
		
		shopService.insert(shop);
		
		//用户送20汀豆
		user.setCoin_quantity(user.getCoin_quantity() + 20);
		user.setManage_shop_id(shop.getId());
		user.setCity_id(school.getCity_id());
		user.setCity_name(school.getCity_name());
		user.setSchool_id(school.getId());
		user.setSchool_name(school.getSchool_name());
		userService.update(user);
		
		//给用户追加授权店长角色
		userService.addRoles(user.getId(), new Integer[]{ RoleVo.ROLE_SHOPKEEPER });
		
	/*	if (apply.getCity_id() != 3) {//郑州新店长注册，不再发送优惠券
			CouponTemplateVo t = couponTemplateService.find(8);
			int[] discount = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
			List<CouponOwnerVo> coupons = new ArrayList<CouponOwnerVo>();
			for (int num : discount) {
				CouponOwnerVo coupon = new CouponOwnerVo();
				coupon.setCity_id(user.getCity_id());
				coupon.setCity_name(user.getCity_name());
				coupon.setUser_id(user.getId());
				coupon.setUser_name(user.getReal_name());
				coupon.setIssuer_id(t.getIssuer_id());
				coupon.setIssuer_name(t.getIssuer_name());
				coupon.setCoupon_tpl_id(t.getId());
				coupon.setCoupon_name(t.getName());
				coupon.setCoupon_type(t.getType());
				coupon.setCoupon_scope(t.getScope());
				coupon.setCoupon_rule("{\"line\":0,\"discount\":" + num + "}");
				coupon.setCoupon_desc(num + "元优惠券");
				coupon.setCoupon_discount(new BigDecimal(num));
				coupon.setCoupon_start_time(DvDateHelper.getTimestamp(DvDateHelper.formatDate(new Date(), "yyyy-MM-dd")));
				coupon.setCoupon_end_time(new Timestamp(coupon.getCoupon_start_time().getTime() + 1000L*60*60*24*30));
				coupon.setCoupon_use_status("0");
				coupon.setStatus("1");
				if (apply.getCity_id() == 7) {//遵义，新店长优惠经理自付
					coupon.setCoupon_scope(CouponOwnerVo.SCOPE_PERSONAL);
				}
				coupons.add(coupon);
			}
			couponService.insert(coupons.toArray(new CouponOwnerVo[0]));
			
			//赠送汀豆及优惠券通知
			String coupon_message = "感谢申请小鹿汀汀店长，我们赠送了您一份大礼包，请点击查收";
			notificationService.addWNotification(user.getId(), IUserConstants.TABLE_NAME + "-" + user.getId() + "-" + "coupon", coupon_message);
		}*/
		
		//申请通过通知
//		String message = "【小鹿汀汀】" + user.getReal_name() + "你好，您申请的店长账户已通过审核，您的账号如下（账号：" + user.getAccount() + "，密码：" + pwd + "），请及时登录后台管理系统" + ApplicationConfig.PROJECT_CONTEXT_PATH + " 修改您的密码！（非本人操作请忽略）";
		String message = "【小鹿汀汀】" + user.getReal_name() + "你好，您的开店申请已通过审核，请……";
		notificationService.addWMNotification(user.getId(), IUserApplyConstants.TABLE_NAME + "-" + apply.getId() + "-" + "pass", message);

		apply.setNotify_msg(message);
		apply.setNotify_status(UserApplyVo.NOTIFY_STATUS_NO);
		apply.setStatus(UserApplyVo.STATUS_PASS);
		return this.update(apply);
	}
	
	@Transactional
	public int deny(Integer id, String reason) {
		UserApplyVo apply = this.find(id);
		if (!UserApplyVo.STATUS_NO.equals(apply.getStatus())) {
			throw new BusinessException("申请记录已处理，不能重复处理！");
		}
		
		UserVo user = userService.find(apply.getUser_id());
		
		//发送通知
		String message = "【小鹿汀汀】" + user.getReal_name() + "你好，您的开店申请未通过审核，原因如下：" + reason;
		notificationService.addWMNotification(user.getId(), IUserApplyConstants.TABLE_NAME + "-" + apply.getId() + "-" + "deny", message);

		apply.setNotify_msg(message);
		apply.setNotify_status(UserApplyVo.NOTIFY_STATUS_NO);
		apply.setStatus(UserApplyVo.STATUS_DENY);
		return this.update(apply);
	}
	
}
