package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.TransactionHistoryDao;
import com.bjpowernode.crm.workbench.domain.TransactionHistory;
import com.bjpowernode.crm.workbench.service.TransactionHistoryService;

public class TransactionHistoryServiceImpl implements TransactionHistoryService {
	private TransactionHistoryDao transactionHistoryDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TransactionHistoryDao.class);
	@Override
	public List<TransactionHistory> getDistory(String transactionId) {
		
		return transactionHistoryDao.getDistory(transactionId);
	}

}
