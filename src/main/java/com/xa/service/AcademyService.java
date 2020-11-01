package com.xa.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.xa.dao.AmyDaoMapper;
import com.xa.entity.Academy;
import com.xa.utils.MybatisUtils;

public class AcademyService {
	
	//单例模式实例化类
	private static AcademyService AmyService=null;
	private AcademyService(){
		
	}
	
	public synchronized static  AcademyService getAmyServiceInstance(){
		if(AmyService==null){
			AmyService=new AcademyService();
		}
		return AmyService;
	}
	/**
	 * 获取当前页的部门列表
	 * @param map 查询条件 
	 * @return List<Dpt> 当前页的部门列表
	 */
	public List<Academy> findAmpInfoWithPage(Map<String, Object> map) {
		//声明返回变量
		List<Academy> rtn=null;
		//获取SqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//返回一个关于DptDaoMapper实现类的一个对象
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//获取当前页的部门列表
			rtn=mapper.findAmyInfoWithPage(map);
			//提交事务
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭
			MybatisUtils.close(sqlSession);
		}
		//返回
		return rtn;
	}
	
	/**
	 * 获取总共的部门个数
	 * @param map
	 * @return
	 */
	public int getAmyCount(Map<String, Object> map) {
		//声明返回变量
		int rtn=0;
		//获取SqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//返回一个关于AmyDaoMapper实现类的一个对象
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//获取当前页的部门列表
			rtn=mapper.getAmyCount(map);
			//提交事务
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭
			MybatisUtils.close(sqlSession);
		}
		//返回
		return rtn;
	}

	public boolean saveAmyInfo(Academy dpt) {
		//声明返回变量
		int rtn=0;
		//获取SqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//返回一个关于DptDaoMapper实现类的一个对象
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//获取当前页的部门列表
			rtn=mapper.saveAmyInfo(dpt);
			//提交事务
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭
			MybatisUtils.close(sqlSession);
		}
		//返回
		return rtn>0;	
		}

	public int delAmyInfo(String[] dids) {
		//声明返回变量
		int rtn=0;
		//获取SqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//返回一个关于DptDaoMapper实现类的一个对象
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			for(int i=0;i<dids.length;i++){
				//根据当前部门编号，删除
				rtn+=mapper.delAmyInfo(Integer.parseInt(dids[i]));
				//提交事务
				sqlSession.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭
			MybatisUtils.close(sqlSession);
		}
		//返回
		return rtn;
	}
	
	/**
	 * 根据部门编号修改部门信息
	 * @param Amy 要修改的部门信息
	 * @return boolean 影响结果集
	 */
	public boolean modifyAmyInfo(Academy dpt) {
		//声明返回变量
		int rtn=0;
		//获取SqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//返回一个关于DptDaoMapper实现类的一个对象
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//根据当前部门编号，修改部门信息
			rtn+=mapper.modifyAmyInfo(dpt);
				//提交事务
				sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭
			MybatisUtils.close(sqlSession);
		}
		//返回
		return rtn>0;
	}
	
	/**
	 * 获取所有的部门列表
	 * @return List<Dpt>  所有的部门列表
	 */
	public List<Academy> findAllAmyInfo() {
		//声明返回变量
		List<Academy> rtn=null;
		//获取SqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//返回一个关于DptDaoMapper实现类的一个对象
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//获取当前页的部门列表
			rtn=mapper.findAllAmyInfo();
			//提交事务
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭
			MybatisUtils.close(sqlSession);
		}
		//返回
		return rtn;
	}
	
}
