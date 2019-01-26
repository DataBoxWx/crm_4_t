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

	/**
	 * 保存交易阶段历史
	 * @param tranHistory
	 * @return
	 */
	int save(TranHistory tranHistory);

	/**
	 * 获取最新的阶段
	 * @return
	 */
	String getLastStage(String tranId);

}
