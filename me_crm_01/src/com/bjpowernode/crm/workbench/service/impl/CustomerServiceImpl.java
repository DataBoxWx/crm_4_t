package com.bjpowernode.crm.workbench.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.dao.UserDao;
import com.bjpowernode.crm.domain.PaginationVo;
import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TransactionDao;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.service.CustomerService;

public class CustomerServiceImpl implements CustomerService{
	private CustomerDao customerDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerDao.class);
	private UserDao userDao = SqlSessionUtil.getCurrentSqlSession().getMapper(UserDao.class);
	private TransactionDao transactionDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TransactionDao.class);
	
	@Override
	public Boolean saveCustomer(Customer customer) {
		if(customerDao.saveCustomer(customer) ==1){
			return  true;
		}
		return  false;
	}
	@Override
	public PaginationVo<Customer> displayAll(Map<String, Object> pageMap) {
		PaginationVo<Customer> vo = new PaginationVo<>();
		vo.setTotal(customerDao.getTotal());
		vo.setDataList(customerDao.displayAll(pageMap));
		return vo;
	}
	@Override
	public Map<String, Object> getById(String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("userList", userDao.list());
		map.put("customer", customerDao.getById(id));
		return map;
	}
	@Override
	public Boolean updateCustomer(Customer customer) {
		
		return customerDao.updateCustomer(customer) == 1;
	}
	@Override
	public Boolean deleteCustomer(String[] ids) {
		
		return customerDao.deleteCustomer(ids) != 0;
	}
	@Override
	public List<Customer> getAll() {
		
		return customerDao.geAll();
	}
	
	@Override
	public int saves(List<Customer> customersList) {
		
		return customerDao.saves(customersList);
	}
	
	@Override
	public Customer getById2(String id) {
		
		return customerDao.getById2(id);
	}
	@Override
	public List<String> getByName(String name) {
		
		return customerDao.getByName(name);
	}
	@Override
	public List<Transaction> getTransactionBycustomerId(String customerId) {
		
		return transactionDao.getTransactionBycustomerId(customerId);
	}

	

}
