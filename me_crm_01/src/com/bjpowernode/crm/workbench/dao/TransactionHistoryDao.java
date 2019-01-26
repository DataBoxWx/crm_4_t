package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.TransactionHistory;

public interface TransactionHistoryDao {
	
	/**
	 * 得到阶段历史,根据交易id
	 * @param transactionId
	 * @return
	 */
	List<TransactionHistory> getDistory(String transactionId);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	TransactionHistory getTransactionHistory(String id);
	
	/**
	 * 插入
	 * @param transactionHistory
	 */
	int save(TransactionHistory transactionHistory);

}
