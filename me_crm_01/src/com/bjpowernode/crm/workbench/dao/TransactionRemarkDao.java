package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.TransactionRemark;

public interface TransactionRemarkDao {
	/**
	 * 
	 * @param trList
	 * @return
	 */
	int save1(List<TransactionRemark> trList);
	
	/**
	 * 
	 * @param transactionRemark
	 * @return
	 */
	int save(TransactionRemark transactionRemark);
	
	/**
	 * 
	 * @return
	 */
	List<TransactionRemark> displayRemark();

}
