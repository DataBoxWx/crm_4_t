package com.bjpowernode.crm.dao;

import java.util.List;

import com.bjpowernode.crm.domain.Dept;

public interface DeptDao {

	List<Dept> getAll();

	int checkCode(String code);
	/**
	 * 新建dept
	 * @param dept
	 * @return
	 */
	int save(Dept dept);

	Dept edit(int code);
	/**
	 * 更新部门信息
	 * @param dept
	 * @return
	 */
	int update(Dept dept);
	/**
	 * 删除操作
	 * @param codes
	 * @return
	 */
	int delete(int[] codes);

}
