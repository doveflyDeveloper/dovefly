package com.deertt.module.sys.user.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.fund.transition.dao.ITransitionDao;
import com.deertt.module.sys.city.dao.ICityDao;
import com.deertt.module.sys.city.vo.CityVo;
import com.deertt.module.sys.market.dao.IMarketDao;
import com.deertt.module.sys.region.dao.IRegionDao;
import com.deertt.module.sys.resource.dao.IResourceDao;
import com.deertt.module.sys.role.dao.IRoleDao;
import com.deertt.module.sys.role.vo.RoleVo;
import com.deertt.module.sys.shop.dao.IShopDao;
import com.deertt.module.sys.user.dao.IUserDao;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.sys.user.vo.UserVo;
import com.deertt.module.sys.warehouse.dao.IWarehouseDao;
import com.deertt.utils.helper.DvSqlHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class UserService extends DvBaseService<IUserDao, UserVo, Integer> implements IUserService, IUserConstants {
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IResourceDao resourceDao;
	
	@Autowired
	private IRegionDao regionDao;
	
	@Autowired
	private ITransitionDao trasitionDao;
	
	@Autowired
	private ICityDao cityDao;
	
	@Autowired
	private IWarehouseDao warehouseDao;
	
	@Autowired
	private IShopDao shopDao;
	
	@Autowired
	private IMarketDao marketDao;
	
	public UserVo findFull(Integer id) {
		UserVo vo = super.find(id);
		if (vo.getManage_warehouse_id() != null && vo.getManage_warehouse_id() != 0) {
			vo.setWarehouse(warehouseDao.find(vo.getManage_warehouse_id()));
		}
		if (vo.getManage_shop_id() != null && vo.getManage_shop_id() != 0) {
			vo.setShop(shopDao.find(vo.getManage_shop_id()));
		}
		if (vo.getManage_market_id() != null && vo.getManage_market_id() != 0) {
			vo.setMarket(marketDao.find(vo.getManage_market_id()));
		}
		vo.setRoles(roleDao.queryRolesByUserId(id));
		return vo;
	}
	
	@Transactional
	public int saveAndAuthorizeRole(UserVo vo) {
		int sum = this.save(vo);
		if (vo.isSuperAdminRole()) {
			executeAuthorize(vo.getId(), new Integer[]{ RoleVo.ROLE_SUPER_ADMIN });
		} else if (vo.isHeadquartersRole()) {
			executeAuthorize(vo.getId(), new Integer[]{ RoleVo.ROLE_HEADQUARTERS });
		} else if (vo.isCityManagerRole()) {
			//更新城市默认经理
			CityVo city = cityDao.find(vo.getCity_id());
			if (city.getDf_supplier_id() != null && city.getDf_supplier_id() != 0) {
				throw new BusinessException("城市【" + city.getName() + "】已有默认经理：" + city.getDf_supplier_name() + "，一个城市暂时不允许出现多个城市经理！");
			}
			city.setDf_supplier_id(vo.getId());
			city.setDf_supplier_name(vo.getReal_name());
			cityDao.update(city);
			executeAuthorize(vo.getId(), new Integer[]{ RoleVo.ROLE_CITY_MANAGER });
		} else if (vo.isShopkeeperRole()) {
			executeAuthorize(vo.getId(), new Integer[]{ RoleVo.ROLE_SHOPKEEPER });
		} else if (vo.isMarketSellerRole()) {
			executeAuthorize(vo.getId(), new Integer[]{ RoleVo.ROLE_MARKET_SELLER });
		} else if (vo.isOperationManagerRole()) {
			executeAuthorize(vo.getId(), new Integer[]{ RoleVo.ROLE_OPERATION_MANAGER });
		} else if (vo.isCashierRole()) {
			executeAuthorize(vo.getId(), new Integer[]{ RoleVo.ROLE_CASHIER });
		}
		
		return sum;
	}
	
	@Transactional
	public int change(UserVo vo) {
		return getDao().change(vo);
	}
	
	@Transactional
	public int changePwd(UserVo vo) {
		return getDao().changePwd(vo);
	}
	
	@Transactional
	public int enable(Integer[] ids) {
		return getDao().enable(ids);
	}
	
	@Transactional
	public int disable(Integer[] ids) {
		return getDao().disable(ids);
	}
	
	@Transactional
	public int executeAuthorize(Integer user_id, Integer[] role_ids) {
		return getDao().authorizeRoles(user_id, role_ids);
	}
	
	@Transactional
	public int addRoles(Integer user_id, Integer[] role_ids) {
		List<Integer> newRoles = new ArrayList<Integer>();
		List<RoleVo> roles = roleDao.queryRolesByUserId(user_id);
		if (roles != null) {
			for (RoleVo role : roles) {
				newRoles.add(role.getId());
			}
			
			for (Integer r : role_ids) {
				if (!newRoles.contains(r)) {
					newRoles.add(r);
				}
			}
		}
		
		return getDao().authorizeRoles(user_id, newRoles.toArray(new Integer[0]));
	}
	
	public UserVo findUserByAccount(String account) {
		return getDao().findByCondition("account = '" + DvSqlHelper.escapeSqlValue(account) + "'");
	}
	
	public UserVo findUserByWechatUnionId(String wechat_unionid) {
		return getDao().findByCondition("wechat_unionid = '" + DvSqlHelper.escapeSqlValue(wechat_unionid) + "'");
	}
	
	public UserVo findFullUserInfoByAccount(String account){
		UserVo user = getDao().findByCondition("account = '" + DvSqlHelper.escapeSqlValue(account) + "'");
		if(user != null){
			
			if (user.getManage_warehouse_id() != null && user.getManage_warehouse_id() != 0) {
				user.setWarehouse(warehouseDao.find(user.getManage_warehouse_id()));
			}
			if (user.getManage_shop_id() != null && user.getManage_shop_id() != 0) {
				user.setShop(shopDao.find(user.getManage_shop_id()));
			}
			if (user.getManage_market_id() != null && user.getManage_market_id() != 0) {
				user.setMarket(marketDao.find(user.getManage_market_id()));
			}
			
			List<RoleVo> roles = roleDao.queryRolesByUserId(user.getId());
			if(roles != null && roles.size() > 0){
				for (RoleVo role : roles) {
					role.setResources(resourceDao.queryResourcesByRoleId(role.getId()));
				}
			}
			user.setRoles(roles);
		}
		return user;
	}
	
	@Transactional
	public int updateLoginInfo(Integer id) {
		return getDao().update(SQL_UPDATE_LOGIN_BY_ID, new Object[]{ id });
	}
	
	@Transactional
	public int addCoin_quantity(Integer user_id, Integer coin_quantity) {
		UserVo user = this.find(user_id);
		user.setCoin_quantity(user.getCoin_quantity() + coin_quantity);
		return getDao().changeCoin_quantity(user_id, user.getCoin_quantity());
	}
	
	@Transactional
	public int reduceCoin_quantity(Integer user_id, Integer coin_quantity) {
		UserVo user = this.find(user_id);
		user.setCoin_quantity(user.getCoin_quantity() - coin_quantity);
		if (user.getCoin_quantity() < 0) {
			throw new BusinessException("汀豆不足！");
		}
		return getDao().changeCoin_quantity(user_id, user.getCoin_quantity());
	}
	
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal balance = new BigDecimal(100);
		BigDecimal rate = new BigDecimal(0.00065);
		BigDecimal totay_interest = balance.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		if (totay_interest.compareTo(new BigDecimal(0.01)) < 0) {
			totay_interest = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		System.out.println(totay_interest);
		System.out.println(df.format(totay_interest));
	}
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.REPEATABLE_READ)
	public int test(Integer id) {
		long threadId = Thread.currentThread().getId();
		System.out.println("方法开始------------"+threadId);
		UserVo user = this.find(id);
		user.setAlipay_account("1039882808@qq.com"+threadId);
		System.out.println("查询完毕------------"+threadId);
//		getDao().update("update sys_user set status = '1' where id = " + id);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("更新开始------------"+threadId);
		int sum = getDao().update(user);
		System.out.println("方法结束------------"+threadId);
		return sum;
	}

}
