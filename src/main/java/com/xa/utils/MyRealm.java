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
	//����
	private UserService userService=UserService.getUserServiceInstance();
	@Override
	//���ý�ɫ��Ȩ��ʹ�õ�
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//��ȡ�˺�
		String account=(String)principals.getPrimaryPrincipal();
		//�����˺Ż�ȡ�û���Ϣ
		User user=this.login(account);
		SimpleAuthorizationInfo info=null;
		//�ж�
		if(user!=null){
			//����SimpleAuthorizationInfo����
			info=new SimpleAuthorizationInfo();
			//�洢1��ɫ�б����ø�main����
			List<String> rolesList=new ArrayList<String>();
			//�洢Ȩ���б����ø�main����
			List<String> prmList=new ArrayList<String>();
			//��ȡ��ѯ����ǰ�û��Ľ�ɫ�б�
			List<Roles> rolList=user.getRolList();
			//��ȡ��ѯ����ǰ�û���Ȩ���б�
			List<Permission> prmInfoListList=user.getPrmList();
			//����rolesList
			for (Roles r : rolList) {
				rolesList.add(r.getrName());
			}
			//��rolesList��info
			info.addRoles(rolesList);
			for (Permission p : prmInfoListList) {
				prmList.add(p.getPname());
			}
			info.addStringPermissions(prmList);
		}
		return info;
	}

	public User login(String account){
		//���ر���
		User user=null;
		//����map,�洢�˺�
		Map<String,Object> map=new HashMap<String,Object>();
		//�洢�˺�
		map.put("account", account);
		//�����˺Ż�ȡ�û���Ϣ
		user=userService.getUserInfo(map);
		return user;
	}

	@Override
	//��֤��Ҫ��
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//����һ�����ر���
		SimpleAuthenticationInfo info=null;
		//��ȡ�˺�
		String account=(String)token.getPrincipal();
		//�����˺Ż�ȡ�û���Ϣ
		User user=this.login(account);
		if(user!=null){
			info=new SimpleAuthenticationInfo(user.getAccount(),user.getPwd(),getName());
		}
		return info;
	}

}
