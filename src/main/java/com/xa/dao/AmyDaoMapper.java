package com.xa.dao;

import java.util.List;
import java.util.Map;

import com.xa.entity.Academy;


public interface AmyDaoMapper {

	public int saveAmyInfo(Academy dpt);

	public int delAmyInfo(int did);
	
	public int modifyAmyInfo(Academy dpt); 

	public Academy findAmyInfo(int did); 
	
	public int getAmyCount(Map<String, Object> map);
	
	public List<Academy> findAllAmyInfo();

	public List<Academy> findAmyInfoWithPage(Map<String, Object> cond); 


}
