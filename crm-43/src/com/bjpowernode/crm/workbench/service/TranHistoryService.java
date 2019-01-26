package com.bjpowernode.crm.workbench.service;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.TranHistory;

public interface TranHistoryService {

	/**
	 * 根据交易id获取交易对应的阶段历史列表
	 * @param tranId 交易id
	 * @return 阶段历史列表
	 */
	List<TranHistory> getByTranId(String tranId);

}
