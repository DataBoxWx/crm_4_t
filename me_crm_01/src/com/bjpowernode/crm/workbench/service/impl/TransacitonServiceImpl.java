package com.bjpowernode.crm.workbench.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.domain.PaginationVo;
import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.util.ChangeStageException;
import com.bjpowernode.crm.util.Const;
import com.bjpowernode.crm.util.DateUtil;
import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.util.UUIDGenerator;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TransactionDao;
import com.bjpowernode.crm.workbench.dao.TransactionHistoryDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.domain.TransactionHistory;
import com.bjpowernode.crm.workbench.service.TransacitonService;

public class TransacitonServiceImpl implements TransacitonService {
	private TransactionDao tranactionDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TransactionDao.class);
	private TransactionHistoryDao transactionHistoryDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TransactionHistoryDao.class);
	private CustomerDao customerDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerDao.class);
	@Override
	public List<Transaction> getActivityByName(String name) {
		
		return tranactionDao.getActivityByName(name);
	}
	@Override
	public Boolean saveTransaction(Transaction transaction,String customerName) {
		Customer customer = customerDao.getCustomer(customerName);
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDGenerator.generate());
			customer.setName(customerName);
			customer.setOwner(transaction.getOwner());
			customer.setCreatBy(transaction.getCreatBy());
			customer.setCreatTime(DateUtil.getSysTime());
			customerDao.saveCustomer(customer);
		}
		transaction.setCustomerId(customer.getId());
		
		return tranactionDao.save1(transaction) == 1 ;
	}
	@Override
	public Map<String, Object> displayTransaction(Map<String, Object> inquireMap) {
		PaginationVo<Transaction> vo = new PaginationVo<>();
		vo.setTotal(tranactionDao.getCount(inquireMap));
		vo.setDataList(tranactionDao.getList(inquireMap));
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("total", vo.getTotal());
		jsonMap.put("dataList", vo.getDataList());
		return jsonMap;
	}
	@Override
	public Transaction getById(String id) {
		
		return tranactionDao.getById(id);
	}
	@Override
	public Boolean changeStage(Transaction transaction) throws ChangeStageException{
		TransactionHistory transactionHistory = transactionHistoryDao.getTransactionHistory(transaction.getId());
		
		if(transactionHistory != null && transactionHistory.getStage().equals(transaction.getStage()) ){
			throw new ChangeStageException("現階段已存在，不能更新");
		}
		transactionHistory = new TransactionHistory();
		transactionHistory.setId(UUIDGenerator.generate());
		transactionHistory.setMoney(transaction.getMoney());
		transactionHistory.setStage(transaction.getStage());
		transactionHistory.setExpectedTime(transaction.getExpectedTime());
		transactionHistory.setCreatBy(transaction.getEditBy());
		transactionHistory.setCreatTime(DateUtil.getSysTime());
		transactionHistory.setTransactionId(transaction.getId());
		;
		return tranactionDao.update(transaction) + transactionHistoryDao.save(transactionHistory) == 2;
	}
	@Override
	public boolean delete(String id) {
		
		return tranactionDao.delete(id);
	}
	@Override
	public Boolean update(Transaction transaction,String customerName) {
		Customer customer = customerDao.getCustomer(customerName);
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDGenerator.generate());
			customer.setName(customerName);
			customer.setOwner(transaction.getOwner());
			customer.setCreatBy(transaction.getCreatBy());
			customer.setCreatTime(DateUtil.getSysTime());
			customerDao.saveCustomer(customer);
		}
		transaction.setCustomerId(customer.getId());
		
		
		return tranactionDao.update2(transaction) == 1;
	}
	@Override
	public Boolean deletes(String[] ids) {
		
		return tranactionDao.deletes(ids) > 0;
	}
	@Override
	public List<Transaction> getAll() {
		
		return tranactionDao.getAll();
	}

}
