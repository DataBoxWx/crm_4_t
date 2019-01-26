package com.bjpowernode.crm.workbench.service;

import java.util.List;

public interface CustomerService {

	/**
	 * 根据客户的名称模糊查询
	 * @param name
	 * @return
	 */
	List<String> getByName(String name);

}
