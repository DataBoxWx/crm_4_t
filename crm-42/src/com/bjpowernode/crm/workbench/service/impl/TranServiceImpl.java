package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.exception.ChangeStageException;
import com.bjpowernode.crm.utils.DateUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDGenerator;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TranService;

public class TranServiceImpl implements TranService {

	private TranDao tranDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TranDao.class);
	private TranHistoryDao tranHistoryDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TranHistoryDao.class);
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

	@Override
	public void update(Tran tran) throws ChangeStageException{
		String lastStage = tranHistoryDao.getLastStage(tran.getId());
		if(tran.getStage().equals(lastStage)){
			throw new ChangeStageException("当前阶段就是"+tran.getStage()+"，不需要修改！");
		}
		TranHistory tranHistory = new TranHistory();
		tranHistory.setId(UUIDGenerator.generate());
		tranHistory.setStage(tran.getStage());
		tranHistory.setMoney(tran.getMoney());
		tranHistory.setExpectedDate(tran.getExpectedDate());
		tranHistory.setCreateTime(tran.getEditTime());
		tranHistory.setCreateBy(tran.getEditBy());
		tranHistory.setTranId(tran.getId());
		tranHistoryDao.save(tranHistory);
		tranDao.update(tran);
	}

	@Override
	public List<Map<String, Object>> getCountByStage() {
		return tranDao.getCountByStage();
	}

}





















