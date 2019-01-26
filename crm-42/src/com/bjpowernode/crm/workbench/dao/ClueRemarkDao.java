package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ClueRemark;

public interface ClueRemarkDao {

	/**
	 * 根据线索id获取线索对应的备注信息列表
	 * @param clueId
	 * @return
	 */
	List<ClueRemark> getByClueId(String clueId);

	/**
	 * 根据线索id删除备注
	 * @param clueId
	 */
	void deleteByClueId(String clueId);

}
