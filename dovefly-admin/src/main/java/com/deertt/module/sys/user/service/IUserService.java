package com.deertt.module.sys.user.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.user.dao.IUserDao;
import com.deertt.module.sys.user.vo.UserVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IUserService extends IDvBaseService<IUserDao, UserVo, Integer> {
	
	public UserVo findFull(Integer id);
	
	/**
	 * 启用
	 * 
	 * @param ids 用于启用的记录的ids
	 * @return 成功启用的记录数
	 */
	public int enable(Integer[] ids);
	
	/**
	 * 禁用
	 * 
	 * @param ids 用于禁用的记录的ids
	 * @return 成功禁用的记录数
	 */
	public int disable(Integer[] ids);
	
	/**
	 * 新增用户时默认授权角色
	 * @param vo
	 * @return
	 */
	public int saveAndAuthorizeRole(UserVo vo);
	
	/**
	 * 给用户授权角色（删除原先所有的角色，全部重新授权）
	 * @param user_id 用户ID
	 * @param role_ids 用户角色授权关系对象
	 * @return 重新授权的角色个数
	 */
	public int executeAuthorize(Integer user_id, Integer[] role_ids);
	
	public int addRoles(Integer user_id, Integer[] role_ids);
	
	/**
	 * 根据用户账户获取用户信息
	 * @param account
	 * @return
	 */
	public UserVo findUserByAccount(String account);
	
	/**
	 * 根据微信UNIONID查询用户
	 * @param wechat_id
	 * @return
	 */
	public UserVo findUserByWechatUnionId(String wechat_unionid);
	
	/**
	 * 根据用户账户获取用户更多信息（角色、权限、员工、部门等）
	 * @param account
	 * @return
	 */
	public UserVo findFullUserInfoByAccount(String account);

	/**
	 * 修改用户部分信息
	 * @param vo
	 * @return
	 */
	public int change(UserVo vo);
	
	/**
	 * 修改用户密码
	 * @param vo
	 * @return
	 */
	public int changePwd(UserVo vo);
	
	/**
	 * 更新用户登录时间及登录次数等
	 * @param id
	 * @return
	 */
	public int updateLoginInfo(Integer id);
	
	/**
	 * 增加汀豆
	 * @param user_id
	 * @param coin_quantity
	 * @return
	 */
	public int addCoin_quantity(Integer user_id, Integer coin_quantity);
	
	/**
	 * 扣除汀豆
	 * @param user_id
	 * @param coin_quantity
	 * @return
	 */
	public int reduceCoin_quantity(Integer user_id, Integer coin_quantity);
	
	public int test(Integer id);
		
}
