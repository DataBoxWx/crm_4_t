package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

public interface CustomerDao {

	/**
	 * 根据名称获取客户，客户名称精确匹配
	 * @param company
	 * @return
	 */
	Customer getByName(String name);

	/**
	 * 保存客户信息
	 * @param customer
	 */
	void save(Customer customer);

}
