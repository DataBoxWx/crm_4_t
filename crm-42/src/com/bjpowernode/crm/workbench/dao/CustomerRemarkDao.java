package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;

public interface CustomerRemarkDao {

	/**
	 * 保存客户备注列表
	 * @param customerRemarkList
	 */
	void save(List<CustomerRemark> customerRemarkList);

}
