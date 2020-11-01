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
	 * 根据账号，查找用户信息
	 * @param map 要查找的用户
	 * @return User 用户信息
	 */
	public User getUserInfo(Map<String, Object> map){
		//声明返回变量
		User user=null;
		//获取sqlSession
		SqlSession sqlSession=MybatisUtils.getSqlSession();
		try {
			//返回一个关于UserDaoMapper实现类的一个对象
			UserDaoMapper mapper = sqlSession.getMapper(UserDaoMapper.class);
			//获取当前页的部门列表
			user=mapper.getUserInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭
			MybatisUtils.close(sqlSession);
		}
		return user;
	}
}
