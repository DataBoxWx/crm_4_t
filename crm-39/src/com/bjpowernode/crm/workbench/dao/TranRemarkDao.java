package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.TranRemark;

public interface TranRemarkDao {

	/**
	 * 保存交易备注
	 * @param tranRemarkList
	 */
	void save(List<TranRemark> tranRemarkList);

}
