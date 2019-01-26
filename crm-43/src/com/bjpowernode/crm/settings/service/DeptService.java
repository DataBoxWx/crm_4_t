package com.bjpowernode.crm.settings.service;

import java.util.List;

import com.bjpowernode.crm.settings.domain.Dept;

public interface DeptService {

	/**
	 * 保存部门
	 * @param dept
	 * @return
	 */
	int save(Dept dept);

	/**
	 * 获取所有部门
	 * @return
	 */
	List<Dept> getAll();

	/**
	 * 根据编号查询部门
	 * @param code
	 * @return
	 */
	Dept getByCode(String code);

	/**
	 * 更新部门
	 * @param dept
	 * @return
	 */
	int update(Dept dept);

	/**
	 * 根据编号删除部门
	 * @param codes
	 * @return
	 */
	int deleteByCode(String[] codes);

}
