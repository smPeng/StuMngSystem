package com.xa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.xa.entity.Permission;
import com.xa.entity.Roles;
import com.xa.entity.User;
import com.xa.service.UserService;

public class MyRealm extends AuthorizingRealm {
	//依赖
	private UserService userService=UserService.getUserServiceInstance();
	@Override
	//设置角色跟权限使用的
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取账号
		String account=(String)principals.getPrimaryPrincipal();
		//根据账号获取用户信息
		User user=this.login(account);
		SimpleAuthorizationInfo info=null;
		//判断
		if(user!=null){
			//创建SimpleAuthorizationInfo对象
			info=new SimpleAuthorizationInfo();
			//存储1角色列表，设置给main界面
			List<String> rolesList=new ArrayList<String>();
			//存储权限列表，设置给main界面
			List<String> prmList=new ArrayList<String>();
			//获取查询到当前用户的角色列表
			List<Roles> rolList=user.getRolList();
			//获取查询到当前用户的权限列表
			List<Permission> prmInfoListList=user.getPrmList();
			//遍历rolesList
			for (Roles r : rolList) {
				rolesList.add(r.getrName());
			}
			//将rolesList给info
			info.addRoles(rolesList);
			for (Permission p : prmInfoListList) {
				prmList.add(p.getPname());
			}
			info.addStringPermissions(prmList);
		}
		return info;
	}

	public User login(String account){
		//返回变量
		User user=null;
		//创建map,存储账号
		Map<String,Object> map=new HashMap<String,Object>();
		//存储账号
		map.put("account", account);
		//根据账号获取用户信息
		user=userService.getUserInfo(map);
		return user;
	}

	@Override
	//认证需要的
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//声明一个返回变量
		SimpleAuthenticationInfo info=null;
		//获取账号
		String account=(String)token.getPrincipal();
		//根据账号获取用户信息
		User user=this.login(account);
		if(user!=null){
			info=new SimpleAuthenticationInfo(user.getAccount(),user.getPwd(),getName());
		}
		return info;
	}

}
