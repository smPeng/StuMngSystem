package com.xa.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.xa.dao.AmyDaoMapper;
import com.xa.entity.Academy;
import com.xa.utils.MybatisUtils;

public class AcademyService {
	
	//����ģʽʵ������
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
	 * ��ȡ��ǰҳ�Ĳ����б�
	 * @param map ��ѯ���� 
	 * @return List<Dpt> ��ǰҳ�Ĳ����б�
	 */
	public List<Academy> findAmpInfoWithPage(Map<String, Object> map) {
		//�������ر���
		List<Academy> rtn=null;
		//��ȡSqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//����һ������DptDaoMapperʵ�����һ������
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//��ȡ��ǰҳ�Ĳ����б�
			rtn=mapper.findAmyInfoWithPage(map);
			//�ύ����
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر�
			MybatisUtils.close(sqlSession);
		}
		//����
		return rtn;
	}
	
	/**
	 * ��ȡ�ܹ��Ĳ��Ÿ���
	 * @param map
	 * @return
	 */
	public int getAmyCount(Map<String, Object> map) {
		//�������ر���
		int rtn=0;
		//��ȡSqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//����һ������AmyDaoMapperʵ�����һ������
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//��ȡ��ǰҳ�Ĳ����б�
			rtn=mapper.getAmyCount(map);
			//�ύ����
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر�
			MybatisUtils.close(sqlSession);
		}
		//����
		return rtn;
	}

	public boolean saveAmyInfo(Academy dpt) {
		//�������ر���
		int rtn=0;
		//��ȡSqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//����һ������DptDaoMapperʵ�����һ������
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//��ȡ��ǰҳ�Ĳ����б�
			rtn=mapper.saveAmyInfo(dpt);
			//�ύ����
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر�
			MybatisUtils.close(sqlSession);
		}
		//����
		return rtn>0;	
		}

	public int delAmyInfo(String[] dids) {
		//�������ر���
		int rtn=0;
		//��ȡSqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//����һ������DptDaoMapperʵ�����һ������
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			for(int i=0;i<dids.length;i++){
				//���ݵ�ǰ���ű�ţ�ɾ��
				rtn+=mapper.delAmyInfo(Integer.parseInt(dids[i]));
				//�ύ����
				sqlSession.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر�
			MybatisUtils.close(sqlSession);
		}
		//����
		return rtn;
	}
	
	/**
	 * ���ݲ��ű���޸Ĳ�����Ϣ
	 * @param Amy Ҫ�޸ĵĲ�����Ϣ
	 * @return boolean Ӱ������
	 */
	public boolean modifyAmyInfo(Academy dpt) {
		//�������ر���
		int rtn=0;
		//��ȡSqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//����һ������DptDaoMapperʵ�����һ������
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//���ݵ�ǰ���ű�ţ��޸Ĳ�����Ϣ
			rtn+=mapper.modifyAmyInfo(dpt);
				//�ύ����
				sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر�
			MybatisUtils.close(sqlSession);
		}
		//����
		return rtn>0;
	}
	
	/**
	 * ��ȡ���еĲ����б�
	 * @return List<Dpt>  ���еĲ����б�
	 */
	public List<Academy> findAllAmyInfo() {
		//�������ر���
		List<Academy> rtn=null;
		//��ȡSqlSession
		SqlSession sqlSession = MybatisUtils.getSqlSession();
		try {
			//����һ������DptDaoMapperʵ�����һ������
			AmyDaoMapper mapper = sqlSession.getMapper(AmyDaoMapper.class);
			//��ȡ��ǰҳ�Ĳ����б�
			rtn=mapper.findAllAmyInfo();
			//�ύ����
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر�
			MybatisUtils.close(sqlSession);
		}
		//����
		return rtn;
	}
	
}
