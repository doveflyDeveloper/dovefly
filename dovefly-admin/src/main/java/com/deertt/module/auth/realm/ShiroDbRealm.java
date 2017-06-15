package com.deertt.module.auth.realm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.deertt.module.sys.resource.vo.ResourceVo;
import com.deertt.module.sys.role.vo.RoleVo;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.vo.UserVo;

public class ShiroDbRealm extends AuthorizingRealm {
	
	final public static String[] ignorePermissions = new String[]{
		"transitionController:read", 
		"interestController:read",
		"applyController:*", 
		"rechargeController:*"
	}; 

	@Autowired
	protected IUserService userService;
	
	/**
	 * 认证回调函数,登录时调用.
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//System.out.println("doGetAuthenticationInfo----------------------------------------------------------------");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		UserVo user = userService.findUserByAccount(token.getUsername());
		if (user != null) {
			if ("0".equals(user.getStatus())) {
				throw new LockedAccountException();
			}
			return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用. 
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("doGetAuthorizationInfo----------------------------------------------------------------");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取当前登录的用户名
		String account = (String) super.getAvailablePrincipal(principals);
		//跟下面的方法，哪个好？
		//UserVo user1 = (UserVo) principals.getPrimaryPrincipal();
		
		List<String> roles = new ArrayList<String>();  
		List<String> permissions = new ArrayList<String>();
		UserVo user = userService.findFullUserInfoByAccount(account);
		
		if(user != null){
			List<ResourceVo> resList = new ArrayList<ResourceVo>();
			if (user.getRoles() != null && user.getRoles().size() > 0) {
				for (RoleVo role : user.getRoles()) {
					roles.add(role.getId().toString());
					if (role.getResources() != null && role.getResources().size() > 0) {
						for (ResourceVo res : role.getResources()) {
							resList.add(res);
							if(StringUtils.isNotEmpty(res.getPermission())){
//								String[] perms = res.getPermission().split(";");
//								permissions.addAll(Arrays.asList(perms));
								permissions.add(res.getPermission());
							}
						}
					}
				}
			}
			//公共基础全局权限
			permissions.addAll(Arrays.asList(ignorePermissions));
		}else{
			throw new AuthorizationException();
		}
		//给当前用户设置角色
		info.addRoles(roles);
		//给当前用户设置权限
		info.addStringPermissions(permissions);
		//System.out.println(info.getStringPermissions());
		return info;
	}
}

