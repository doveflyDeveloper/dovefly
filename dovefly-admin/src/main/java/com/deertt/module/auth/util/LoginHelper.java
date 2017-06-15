package com.deertt.module.auth.util;

import org.apache.shiro.SecurityUtils;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.sys.market.vo.MarketVo;
import com.deertt.module.sys.shop.vo.ShopVo;
import com.deertt.module.sys.user.vo.UserVo;
import com.deertt.module.sys.warehouse.vo.WarehouseVo;

public class LoginHelper {
	
	/** 获取当前用户对象 */
	public static UserVo getUser() {
		return (UserVo) SecurityUtils.getSubject().getSession().getAttribute(IGlobalConstants.DV_USER_VO);
	}
	
	/** 设置当前用户对象 */
	public static void setUser(UserVo user) {
		SecurityUtils.getSubject().getSession().setAttribute(IGlobalConstants.DV_USER_VO, user);
	}
	
	/** 获取当前用户ID */
	public static Integer getUserId() {
		return getUser().getId();
	}
	
	/** 获取当前用户姓名 */
	public static String getUserName() {
		return getUser().getReal_name();
	}
	
	/** 获取当前用户登陆账号 */
	public static String getUserAccount() {
		return getUser().getAccount();
	}
	
	/** 获取当前用户所在城市ID */
	public static Integer getCityId() {
		return getUser().getCity_id();
	}
	
	/** 获取当前用户所在城市名称 */
	public static String getCityName() {
		return getUser().getCity_name();
	}
	
	/** 获取当前用户所在学校ID */
	public static Integer getSchoolId() {
		return getUser().getSchool_id();
	}
	
	/** 获取当前用户所在学校名称 */
	public static String getSchoolName() {
		return getUser().getSchool_name();
	}
	
	/** 获取当前用户的店铺所属的运营主管ID */
	public static Integer getManagerId() {
		ShopVo shop = getShop();
		if (shop != null) {
			return shop.getManager_id();
		}
		return 0;
	}
	
	/** 获取当前用户的店铺所属的运营主管名称 */
	public static String getManagerName() {
		ShopVo shop = getShop();
		if (shop != null) {
			return shop.getManager_name();
		}
		return "";
	}
	
	/** 获取当前用户所在店铺 */
	public static WarehouseVo getWarehouse() {
		return getUser().getWarehouse();
	}
	
	/** 获取当前用户所属城市的城市经理ID */
	public static Integer getWarehouseId() {
		WarehouseVo warehouse = getWarehouse();
		if (warehouse != null) {
			return warehouse.getId();
		}
		return 0;
	}
	
	/** 获取当前用户所属城市的城市经理名称 */
	public static String getWarehouseName() {
		WarehouseVo warehouse = getWarehouse();
		if (warehouse != null) {
			return warehouse.getWarehouse_name();
		}
		return "";
	}
	
	/** 获取当前用户所在店铺 */
	public static ShopVo getShop() {
		return getUser().getShop();
	}
	
	/** 获取当前用户所在店铺ID */
	public static Integer getShopId() {
		ShopVo shop = getShop();
		if (shop != null) {
			return shop.getId();
		}
		return 0;
	}
	
	/** 获取当前用户所在店铺名称 */
	public static String getShopName() {
		ShopVo shop = getShop();
		if (shop != null) {
			return shop.getShop_name();
		}
		return "";
	}
	
	/** 获取当前用户所在超市 */
	public static MarketVo getMarket() {
		return getUser().getMarket();
	}
	
	/** 获取当前用户所在超市ID */
	public static Integer getMarketId() {
		MarketVo market = getMarket();
		if (market != null) {
			return market.getId();
		}
		return 0;
	}
	
	/** 获取当前用户所在超市名称 */
	public static String getMarketName() {
		MarketVo market = getMarket();
		if (market != null) {
			return market.getMarket_name();
		}
		return "";
	}
	
	/** 获取当前用户单位类型 */
	public static String getStoreType() {
		UserVo user = getUser();
		if (user.isCityManagerRole()) {
			return "w";
		} else if (user.isShopkeeperRole()) {
			return "s";
		} else if (user.isMarketSellerRole()) {
			return "m";
		}
		return "";
		
	}
	
	/** 获取当前用户单位ID */
	public static Integer getStoreId() {
		UserVo user = getUser();
		if (user.isCityManagerRole()) {
			return user.getWarehouse().getId();
		} else if (user.isShopkeeperRole()) {
			return user.getShop().getId();
		} else if (user.isMarketSellerRole()) {
			return user.getMarket().getId();
		}
		return 0;
	}
	
	/** 获取当前用户单位名称 */
	public static String getStoreName() {
		UserVo user = getUser();
		if (user.isCityManagerRole()) {
			return user.getWarehouse().getWarehouse_name();
		} else if (user.isShopkeeperRole()) {
			return user.getShop().getShop_name();
		} else if (user.isMarketSellerRole()) {
			return user.getMarket().getMarket_name();
		}
		return "";
	}
	
	public static boolean isSuperAdminRole() {
		return getUser().isSuperAdminRole();
	}
	
	public static boolean isHeadquartersRole() {
		return getUser().isHeadquartersRole();
	}
	
	public static boolean isCityManagerRole() {
		return getUser().isCityManagerRole();
	}
	
	public static boolean isOperationManagerRole() {
		return getUser().isOperationManagerRole();
	}
	
	public static boolean isShopkeeperRole() {
		return getUser().isShopkeeperRole();
	}
	
	public static boolean isMarketSellerRole() {
		return getUser().isMarketSellerRole();
	}
	
}
