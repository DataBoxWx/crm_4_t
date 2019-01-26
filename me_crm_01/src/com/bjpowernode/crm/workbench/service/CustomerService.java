package com.bjpowernode.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.domain.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.domain.Transaction;

public interface CustomerService {
	/**
	 * 保存客戶
	 * @param customer
	 * @return
	 */
	Boolean saveCustomer(Customer customer);
	/**
	 * 显示所有用户,分页
	 * @return
	 */
	PaginationVo<Customer> displayAll(Map<String, Object> pageMap);
	
	/**
	 * 根据Id获得客户
	 * @param id
	 * @return
	 */
	Map<String, Object> getById(String id);
	
	/**
	 * 更新
	 * @param customer
	 * @return
	 */
	Boolean updateCustomer(Customer customer);
	
	/**
	 * 接受数组删除
	 * @param ids
	 * @return
	 */
	Boolean deleteCustomer(String[] ids);
	
	/**
	 * 得到客户列表
	 * @return
	 */
	List<Customer> getAll();
	
	/**
	 * 导入时保存
	 * @param clueList
	 * @return
	 */
	int saves(List<Customer> clueList);
	/**
	 * 根据id获取客户
	 * @param id
	 * @return
	 */
	Customer getById2(String id);
	
	/**
	 * 自动补齐查询的name
	 * @param name
	 * @return
	 */
	List<String> getByName(String name);
	
	/**
	 * 根据客户id获取交易列表
	 * @param customerId
	 * @return
	 */
	List<Transaction> getTransactionBycustomerId(String customerId);
	
	
	
	

}
