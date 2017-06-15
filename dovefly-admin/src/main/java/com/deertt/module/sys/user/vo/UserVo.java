package com.deertt.module.sys.user.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.deertt.frame.base.vo.DvBaseVo;
import com.deertt.module.sys.market.vo.MarketVo;
import com.deertt.module.sys.role.vo.RoleVo;
import com.deertt.module.sys.shop.vo.ShopVo;
import com.deertt.module.sys.warehouse.vo.WarehouseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class UserVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//所在城市
	private Integer city_id;
	
	//所在城市
	private String city_name;
	
	//所在学校
	private Integer school_id;
	
	//所在学校
	private String school_name;
	
	//姓名
	private String real_name;
	
	//昵称
	private String nick_name;
	
	//账号
	private String account;
	
	//密码
	private String password;
	
	//手机号码
	private String mobile;
	
	//邮箱
	private String email;
	
	//宿舍地址
	private String dorm_address;
	
	//详细地址
	private String address;
	
	//头像
	private String avatar;
	
	//生日
	private Date birthday;
	
	//会员等级
	private Integer level;
	
	//会员积分
	private Integer point;
	
	//上次登录时间
	private Timestamp last_login_time;
	
	//登录次数
	private Integer login_times;
	
	//密码是否被重置
	private String pwd_reset;
	
	private Integer coin_quantity;
	
	private String wechat_subscribe;
	
	//微信昵称
	private String wechat_account;
	
	//微信
	private String wechat_id;
	
	//微信
	private String wechat_unionid;
	
	//微信头像
	private String wechat_avatar;
	
	//支付宝账号
	private String alipay_account;
	
	//默认关注店铺
	private Integer df_shop;
	
	//收藏店铺ID列表
	private String fav_shops;
	
	private Integer manage_warehouse_id;
	
	private Integer manage_shop_id;
	
	private Integer manage_market_id;
	
	private WarehouseVo warehouse;
	
	private ShopVo shop;
	
	private MarketVo market;
	
	// 用户角色
	private List<RoleVo> roles;
	
	//----------结束vo的属性----------
	@Override
	public boolean isNew() {
		return id == null || id == 0;
	}
	
	//----------开始vo的setter和getter方法----------
	/** 获取id */
	public Integer getId() {
		return id;
	}
	
	/** 设置id */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/** 获取所在城市 */
	public Integer getCity_id() {
		return city_id;
	}
	
	/** 设置所在城市 */
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	/** 获取所在城市 */
	public String getCity_name() {
		return city_name;
	}
	
	/** 设置所在城市 */
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	/** 获取所在学校 */
	public Integer getSchool_id() {
		return school_id;
	}
	
	/** 设置所在学校 */
	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}
	
	/** 获取所在学校 */
	public String getSchool_name() {
		return school_name;
	}
	
	/** 设置所在学校 */
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	
	/** 获取姓名 */
	public String getReal_name() {
		return real_name;
	}
	
	/** 设置姓名 */
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	
	/** 获取昵称 */
	public String getNick_name() {
		return nick_name;
	}
	
	/** 设置昵称 */
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	
	/** 获取账号 */
	public String getAccount() {
		return account;
	}
	
	/** 设置账号 */
	public void setAccount(String account) {
		this.account = account;
	}
	
	/** 获取密码 */
	public String getPassword() {
		return password;
	}
	
	/** 设置密码 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/** 获取手机号码 */
	public String getMobile() {
		return mobile;
	}
	
	/** 设置手机号码 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/** 获取邮箱 */
	public String getEmail() {
		return email;
	}
	
	/** 设置邮箱 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDorm_address() {
		return dorm_address;
	}

	public void setDorm_address(String dorm_address) {
		this.dorm_address = dorm_address;
	}

	/** 获取地址 */
	public String getAddress() {
		return address;
	}
	
	/** 设置地址 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/** 获取头像 */
	public String getAvatar() {
		return avatar;
	}
	
	/** 设置头像 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	/** 获取生日 */
	public Date getBirthday() {
		return birthday;
	}
	
	/** 设置生日 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	/** 获取会员等级 */
	public Integer getLevel() {
		return level;
	}
	
	/** 设置会员等级 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/** 获取会员积分 */
	public Integer getPoint() {
		return point;
	}
	
	/** 设置会员积分 */
	public void setPoint(Integer point) {
		this.point = point;
	}
	
	/** 获取上次登录时间 */
	public Timestamp getLast_login_time() {
		return last_login_time;
	}
	
	/** 设置上次登录时间 */
	public void setLast_login_time(Timestamp last_login_time) {
		this.last_login_time = last_login_time;
	}
	
	/** 获取登录次数 */
	public Integer getLogin_times() {
		return login_times;
	}
	
	/** 设置登录次数 */
	public void setLogin_times(Integer login_times) {
		this.login_times = login_times;
	}
	
	/** 获取密码是否被重置 */
	public String getPwd_reset() {
		return pwd_reset;
	}
	
	/** 设置密码是否被重置 */
	public void setPwd_reset(String pwd_reset) {
		this.pwd_reset = pwd_reset;
	}

	public Integer getCoin_quantity() {
		return coin_quantity;
	}

	public void setCoin_quantity(Integer coin_quantity) {
		this.coin_quantity = coin_quantity;
	}
	
	public String getWechat_subscribe() {
		return wechat_subscribe;
	}

	public void setWechat_subscribe(String wechat_subscribe) {
		this.wechat_subscribe = wechat_subscribe;
	}

	/** 获取微信 */
	public String getWechat_account() {
		return wechat_account;
	}
	
	/** 设置微信 */
	public void setWechat_account(String wechat_account) {
		this.wechat_account = wechat_account;
	}
	
	/** 获取微信 */
	public String getWechat_id() {
		return wechat_id;
	}
	
	/** 设置微信 */
	public void setWechat_id(String wechat_id) {
		this.wechat_id = wechat_id;
	}
	
	/** 获取微信 */
	public String getWechat_unionid() {
		return wechat_unionid;
	}
	
	/** 设置微信 */
	public void setWechat_unionid(String wechat_unionid) {
		this.wechat_unionid = wechat_unionid;
	}
	
	/** 获取微信头像 */
	public String getWechat_avatar() {
		return wechat_avatar;
	}
	
	/** 设置微信头像 */
	public void setWechat_avatar(String wechat_avatar) {
		this.wechat_avatar = wechat_avatar;
	}
	
	/** 获取支付宝账号 */
	public String getAlipay_account() {
		return alipay_account;
	}
	
	/** 设置支付宝账号 */
	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}
	
	public Integer getDf_shop() {
		return df_shop;
	}

	public void setDf_shop(Integer df_shop) {
		this.df_shop = df_shop;
	}

	public String getFav_shops() {
		return fav_shops;
	}

	public void setFav_shops(String fav_shops) {
		this.fav_shops = fav_shops;
	}
	
	public Integer getManage_warehouse_id() {
		return manage_warehouse_id;
	}

	public void setManage_warehouse_id(Integer manage_warehouse_id) {
		this.manage_warehouse_id = manage_warehouse_id;
	}

	public Integer getManage_shop_id() {
		return manage_shop_id;
	}

	public void setManage_shop_id(Integer manage_shop_id) {
		this.manage_shop_id = manage_shop_id;
	}
	
	public Integer getManage_market_id() {
		return manage_market_id;
	}

	public void setManage_market_id(Integer manage_market_id) {
		this.manage_market_id = manage_market_id;
	}
	
	public WarehouseVo getWarehouse() {
		return warehouse;
	}
	
	public void setWarehouse(WarehouseVo warehouse) {
		this.warehouse = warehouse;
	}

	public ShopVo getShop() {
		return shop;
	}

	public void setShop(ShopVo shop) {
		this.shop = shop;
	}

	public MarketVo getMarket() {
		return market;
	}

	public void setMarket(MarketVo market) {
		this.market = market;
	}

	/** 获得用户角色 */
	public List<RoleVo> getRoles() {
		return roles;
	}

	/** 设置用户角色 */
	public void setRoles(List<RoleVo> roles) {
		this.roles = roles;
	}
	
	//----------结束vo的setter和getter方法----------
	
	public boolean isSuperAdminRole() {
		boolean ret = false;
		if (roles != null && roles.size() > 0) {
			for (RoleVo r : roles) {
				ret = ret || r.getId() == RoleVo.ROLE_SUPER_ADMIN;
			}
		}
		return ret;
	}
	
	public boolean isHeadquartersRole() {
		boolean ret = false;
		if (roles != null && roles.size() > 0) {
			for (RoleVo r : roles) {
				ret = ret || r.getId() == RoleVo.ROLE_HEADQUARTERS;
			}
		}
		return ret;
	}
	
	public boolean isCashierRole() {
		boolean ret = false;
		if (roles != null && roles.size() > 0) {
			for (RoleVo r : roles) {
				ret = ret || r.getId() == RoleVo.ROLE_CASHIER;
			}
		}
		return ret;
	}
	
	public boolean isCityManagerRole() {
		boolean ret = false;
		if (roles != null && roles.size() > 0) {
			for (RoleVo r : roles) {
				ret = ret || r.getId() == RoleVo.ROLE_CITY_MANAGER;
			}
		}
		return ret;
	}
	
	public boolean isOperationManagerRole() {
		boolean ret = false;
		if (roles != null && roles.size() > 0) {
			for (RoleVo r : roles) {
				ret = ret || r.getId() == RoleVo.ROLE_OPERATION_MANAGER;
			}
		}
		return ret;
	}
	
	public boolean isShopkeeperRole() {
		boolean ret = false;
		if (roles != null && roles.size() > 0) {
			for (RoleVo r : roles) {
				ret = ret || r.getId() == RoleVo.ROLE_SHOPKEEPER;
			}
		}
		return ret;
	}
	
	public boolean isMarketSellerRole() {
		boolean ret = false;
		if (roles != null && roles.size() > 0) {
			for (RoleVo r : roles) {
				ret = ret || r.getId() == RoleVo.ROLE_MARKET_SELLER;
			}
		}
		return ret;
	}
	
	public Object getStore() {
		if (this.warehouse != null) {
			return this.warehouse;
		} else if (this.shop != null) {
			return this.shop;
		} else if (this.shop != null) {
			return this.shop;
		}
		return null;
	}
	
}