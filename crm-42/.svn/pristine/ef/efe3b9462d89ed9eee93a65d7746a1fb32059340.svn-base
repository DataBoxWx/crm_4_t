package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerDao.class);
	
	@Override
	public List<String> getByName(String name) {
		return customerDao.getByName2(name);
	}

}
