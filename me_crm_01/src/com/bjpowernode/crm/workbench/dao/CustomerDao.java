package com.bjpowernode.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.workbench.domain.Customer;

public interface CustomerDao {
	
	/**
	 * 根據公司名稱查詢客戶
	 * @param company
	 * @return
	 */
	Customer getCustomer(String company);
	
	/**
	 * 保存客户
	 * @param customer
	 * @return
	 */
	int saveCustomer(Customer customer);
	/**
	 * 得到总条数
	 * @return
	 */
	long getTotal();
	
	/**
	 * 得到所有数据
	 * @return
	 */
	List<Customer> displayAll(Map<String, Object> pageMap);
	
	/**
	 * 根据Id获取客户
	 * @param id
	 * @return
	 */
	Customer getById(String id);
	
	/**
	 * 更新
	 * @param customer
	 * @return
	 */
	int updateCustomer(Customer customer);
	
	/**
	 * 根据数组id删除
	 * @param ids
	 * @return
	 */
	int deleteCustomer(String[] ids);
	
	/**
	 * 无脑查所有
	 * @return
	 */
	List<Customer> geAll();
	
	/**
	 * 导入时保存
	 * @param customersList
	 * @return
	 */
	int saves(List<Customer> customersList);
	
	/**
	 * 据Id获取客户,链接用户表
	 * @param id
	 * @return
	 */
	Customer getById2(String id);
	
	/**
	 * 查询客户列表名字
	 * @param name
	 * @return
	 */
	List<String> getByName(String name);
	
	/**
	 * 根据客户名字精确匹配
	 * @param customerName
	 * @return
	 */
	Customer getByName2(String customerName);
	


	
	

}
