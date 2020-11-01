package com.xa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.xa.dao.StuDaoMapper;
import com.xa.entity.Stu;
import com.xa.utils.MybatisUtils;

public class StuService {
	//����ģʽʵ������
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
	 * ��ҳ��ȡԱ����Ϣ
	 * @param cond Map<String, Object> ƫ������ÿҳ��ʾ�����������������ű��
	 * @return
	 */
	public List<Stu> findAllStuInfoWithPage(Map<String, Object> cond) {
		//�������ر���
		List<Stu> rtn=new ArrayList<Stu>();
		//�������ݿ�
		SqlSession sqlSession=MybatisUtils.getSqlSession();
		try {
			StuDaoMapper mapper = sqlSession.getMapper(StuDaoMapper.class);
			//ִ��sql
			rtn=mapper.findAllStuInfoWithPage(cond);
			//�����ύ
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return rtn;
	}
	
	/**
	 * ��ȡ���е�Ա������
	 * @param cond Map<String, Object> ƫ������ÿҳ��ʾ�����������������ű��
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
	 * ����Ա����Ϣ
	 * @param emp Ҫ�����Ա����Ϣ
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
	 * ����Ա����ţ�ɾ��Ա��1��Ϣ
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
	 * ����Ա����ţ��޸�Ա����Ϣ
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
