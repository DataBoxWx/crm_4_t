package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;

public interface CustomerRemarkDao {
	/**
	 * 
	 * @param customerRemarkList
	 * @return
	 */
	int saves(List<CustomerRemark> customerRemarkList);
	
	/**
	 * 根据客户id得到备注列表
	 * @param customerId
	 * @return
	 */
	List<CustomerRemark> getCustomerRemarkByCustomerId1(String customerId);
	
	/**
	 * 
	 * @param customerRemark
	 * @return
	 */
	int save(CustomerRemark customerRemark);
	
	/**
	 * 根据客户与备注id得到连表查询结果
	 * @param id 备注id
	 * @param customerId 客户id
	 * @return
	 */
	CustomerRemark getCustomerRemarkByCustomerId2(String id, String customerId);
	
	/**
	 * 更新
	 * @param customerRemark
	 * @return
	 */
	int update(CustomerRemark customerRemark);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	int delete(String id);

}
