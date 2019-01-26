package com.bjpowernode.crm.settings.dao;

import java.util.List;

import com.bjpowernode.crm.settings.domain.Dept;

public interface DeptDao {

	/**
	 * 
	 * @param dept
	 * @return
	 */
	int save(Dept dept);

	/**
	 * 
	 * @return
	 */
	List<Dept> getAll();

	/**
	 * 
	 * @param code
	 * @return
	 */
	Dept getByCode(String code);

	/**
	 * 
	 * @param dept
	 * @return
	 */
	int update(Dept dept);

	/**
	 * 
	 * @param codes
	 * @return
	 */
	int deleteByCode(String[] codes);

}
