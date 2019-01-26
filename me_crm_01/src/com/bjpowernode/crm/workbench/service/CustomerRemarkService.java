package com.bjpowernode.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;

public interface CustomerRemarkService {
	
	/**
	 * 根据客户id获取备注列表
	 * @param customerId
	 * @return
	 */
	List<CustomerRemark> getCustomerRemarkByCustomerId(String customerId);
	
	/**
	 * 插入一条备注,并连表查询
	 * @param customerRemark
	 * @return
	 */
	Map<String, Object>  saveRemark(CustomerRemark customerRemark);
	
	/**
	 * 更新备注
	 * @param customerRemark
	 * @return
	 */
	int updateRemark(CustomerRemark customerRemark);
	
	/**
	 * 删除备注
	 * @param id
	 * @return
	 */
	Boolean deleteRemark(String id);

}
