package com.xa.dao;

import java.util.List;
import java.util.Map;

import com.xa.entity.Stu;

public interface StuDaoMapper {
	/**
	 * 按页获取员工信息
	 * @param cond Map<String, Object> 偏移量，每页显示的条数，姓名，部门编号
	 * @return
	 */
	public List<Stu> findAllStuInfoWithPage(Map<String, Object> cond);
	/**
	 * 获取所有的员工个数
	 * @param cond Map<String, Object> 偏移量，每页显示的条数，姓名，部门编号
	 * @return
	 */
	public int getStuCount(Map<String, Object> cond) ;
	/**
	 * 保存员工信息
	 * @param emp 要保存的员工信息
	 * @return
	 */
	public boolean saveStuInfo(Stu emp);
	/**
	 * 根据员工编号，删除员工信息
	 * @param eid
	 * @return
	 */
	public boolean batchStuInfo(int eId);
	/**
	 * 根据员工编号，修改员工信息
	 * @param emp
	 * @return
	 */
	public boolean modifyStuInfo(Stu emp);
}
