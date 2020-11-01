package com.xa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.xa.dao.StuDaoMapper;
import com.xa.entity.Stu;
import com.xa.utils.MybatisUtils;

public class StuService {
	//单例模式实例化类
	private static StuService stuService=null;
	private StuService(){
		
	}
	
	public synchronized static  StuService getStuServiceInstance(){
		if(stuService==null){
			stuService=new StuService();
		}
		return stuService;
	}
	/**
	 * 按页获取员工信息
	 * @param cond Map<String, Object> 偏移量，每页显示的条数，姓名，部门编号
	 * @return
	 */
	public List<Stu> findAllStuInfoWithPage(Map<String, Object> cond) {
		//声明返回变量
		List<Stu> rtn=new ArrayList<Stu>();
		//连接数据库
		SqlSession sqlSession=MybatisUtils.getSqlSession();
		try {
			StuDaoMapper mapper = sqlSession.getMapper(StuDaoMapper.class);
			//执行sql
			rtn=mapper.findAllStuInfoWithPage(cond);
			//事务提交
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return rtn;
	}
	
	/**
	 * 获取所有的员工个数
	 * @param cond Map<String, Object> 偏移量，每页显示的条数，姓名，部门编号
	 * @return
	 */
	public int getStuCount(Map<String, Object> cond) {
		int count=0;
		SqlSession sqlSession=MybatisUtils.getSqlSession();
		try {
			StuDaoMapper mapper = sqlSession.getMapper(StuDaoMapper.class);
			count=mapper.getStuCount(cond);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return count;
	}
	/**
	 * 保存员工信息
	 * @param emp 要保存的员工信息
	 * @return
	 */
	public boolean saveStuInfo(Stu emp) {
		boolean count=false;
		SqlSession sqlSession=MybatisUtils.getSqlSession();
		try {
			StuDaoMapper mapper = sqlSession.getMapper(StuDaoMapper.class);
			count=mapper.saveStuInfo(emp);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return count;
	}
	/**
	 * 根据员工编号，删除员工1信息
	 * @param eIds
	 * @return
	 */
	public boolean batchStuInfo(String[] eIds) {
		boolean count=false;
		SqlSession sqlSession=MybatisUtils.getSqlSession();
		try {
			StuDaoMapper mapper = sqlSession.getMapper(StuDaoMapper.class);
			for(int i=0;i<eIds.length;i++){
				int	eId=Integer.parseInt(eIds[i]);
				count=mapper.batchStuInfo(eId);
				sqlSession.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return count;
	}
	/**
	 * 根据员工编号，修改员工信息
	 * @param emp
	 * @return
	 */
	public boolean modifyStuInfo(Stu emp) {
		boolean count=false;
		SqlSession sqlSession=MybatisUtils.getSqlSession();
		try {
			StuDaoMapper mapper = sqlSession.getMapper(StuDaoMapper.class);
			count=mapper.modifyStuInfo(emp);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return count;
	}

}
