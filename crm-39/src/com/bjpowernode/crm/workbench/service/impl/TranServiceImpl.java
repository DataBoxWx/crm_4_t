package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDGenerator;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.TranService;

public class TranServiceImpl implements TranService {

	private TranDao tranDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TranDao.class);
	private CustomerDao customerDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerDao.class);
	
	@Override
	public boolean save(Tran tran, String customerName) {
		Customer customer = customerDao.getByName(customerName);
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDGenerator.generate());
			customer.setOwner(tran.getOwner());
			customer.setName(customerName);
			customer.setCreateTime(DateUtil.getSysTime());
			customer.setCreateBy(tran.getCreateBy());
			customerDao.save(customer);
		}
		tran.setCustomerId(customer.getId());
		return tranDao.save(tran) == 1;
	}

	@Override
	public Tran getById(String id) {
		return tranDao.getById(id);
	}

}
