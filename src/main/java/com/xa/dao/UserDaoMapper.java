package com.xa.dao;

import java.util.Map;

import com.xa.entity.User;

public interface UserDaoMapper {
	
	/**
	 * �����˺ţ������û���Ϣ
	 * @param map Ҫ���ҵ��û�
	 * @return User �û���Ϣ
	 */
	User getUserInfo(Map<String, Object> map);
	
}
