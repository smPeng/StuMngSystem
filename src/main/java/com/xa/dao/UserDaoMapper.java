package com.xa.dao;

import java.util.Map;

import com.xa.entity.User;

public interface UserDaoMapper {
	
	/**
	 * 根据账号，查找用户信息
	 * @param map 要查找的用户
	 * @return User 用户信息
	 */
	User getUserInfo(Map<String, Object> map);
	
}
