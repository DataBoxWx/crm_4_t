package com.bjpowernode.crm.workbench.service;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.TransactionRemark;

public interface TransacitonRemarkService {
	
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
