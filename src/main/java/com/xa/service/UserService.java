package com.xa.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.xa.dao.UserDaoMapper;
import com.xa.entity.User;
import com.xa.utils.MybatisUtils;

public class UserService {
	private static UserService userService=null;
	public synchronized static  UserService getUserServiceInstance(){
		if(userService==null){
			userService=new UserService();
		}
		return userService;
	}
	public UserService(){
		
	}
	
	/**
	 * �����˺ţ������û���Ϣ
	 * @param map Ҫ���ҵ��û�
	 * @return User �û���Ϣ
	 */
	public User getUserInfo(Map<String, Object> map){
		//�������ر���
		User user=null;
		//��ȡsqlSession
		SqlSession sqlSession=MybatisUtils.getSqlSession();
		try {
			//����һ������UserDaoMapperʵ�����һ������
			UserDaoMapper mapper = sqlSession.getMapper(UserDaoMapper.class);
			//��ȡ��ǰҳ�Ĳ����б�
			user=mapper.getUserInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر�
			MybatisUtils.close(sqlSession);
		}
		return user;
	}
}
