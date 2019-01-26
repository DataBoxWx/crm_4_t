package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.TranHistory;

public interface TranHistoryDao {

	/**
	 * 
	 * @param tranId
	 * @return
	 */
	List<TranHistory> getByTranId(String tranId);

}
