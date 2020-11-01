package com.xa.dao;

import java.util.List;
import java.util.Map;

import com.xa.entity.Stu;

public interface StuDaoMapper {
	/**
	 * ��ҳ��ȡԱ����Ϣ
	 * @param cond Map<String, Object> ƫ������ÿҳ��ʾ�����������������ű��
	 * @return
	 */
	public List<Stu> findAllStuInfoWithPage(Map<String, Object> cond);
	/**
	 * ��ȡ���е�Ա������
	 * @param cond Map<String, Object> ƫ������ÿҳ��ʾ�����������������ű��
	 * @return
	 */
	public int getStuCount(Map<String, Object> cond) ;
	/**
	 * ����Ա����Ϣ
	 * @param emp Ҫ�����Ա����Ϣ
	 * @return
	 */
	public boolean saveStuInfo(Stu emp);
	/**
	 * ����Ա����ţ�ɾ��Ա����Ϣ
	 * @param eid
	 * @return
	 */
	public boolean batchStuInfo(int eId);
	/**
	 * ����Ա����ţ��޸�Ա����Ϣ
	 * @param emp
	 * @return
	 */
	public boolean modifyStuInfo(Stu emp);
}
