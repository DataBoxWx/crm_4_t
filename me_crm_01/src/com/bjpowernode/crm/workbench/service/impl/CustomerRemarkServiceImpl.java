package com.bjpowernode.crm.workbench.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.CustomerRemarkDao;
import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.service.CustomerRemarkService;

public class CustomerRemarkServiceImpl implements CustomerRemarkService {
	private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerRemarkDao.class);
	@Override
	public List<CustomerRemark> getCustomerRemarkByCustomerId(String customerId) {
		
		return customerRemarkDao.getCustomerRemarkByCustomerId1(customerId);
	}
	@Override
	public Map<String, Object>  saveRemark(CustomerRemark customerRemark) {
		int count = customerRemarkDao.save(customerRemark);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if(count == 1){
			customerRemark = customerRemarkDao.getCustomerRemarkByCustomerId2(customerRemark.getId(),customerRemark.getCustomerId());
			jsonMap.put("customerRemark", customerRemark);
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		return jsonMap;
	}
	@Override
	public int updateRemark(CustomerRemark customerRemark) {
		
		return customerRemarkDao.update(customerRemark);
	}
	@Override
	public Boolean deleteRemark(String id) {
		
		return customerRemarkDao.delete(id) == 1;
	}

}
