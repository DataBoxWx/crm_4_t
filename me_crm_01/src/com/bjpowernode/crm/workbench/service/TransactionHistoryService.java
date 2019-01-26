package com.bjpowernode.crm.workbench.service;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.TransactionHistory;

public interface TransactionHistoryService {

	List<TransactionHistory> getDistory(String transactionId);

}
