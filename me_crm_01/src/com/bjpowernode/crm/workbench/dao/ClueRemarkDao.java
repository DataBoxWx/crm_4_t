package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ClueRemark;

public interface ClueRemarkDao {

	/**
	 * 新增一个备注信息
	 * @param clueRemark
	 * @return
	 */
	int saveRemark(ClueRemark clueRemark);
	
	
	/**
	 * 获得所有的备注信息
	 * @return
	 */
	List<ClueRemark> displayRemark();

	/**
	 * 根据备注ID更新备注
	 * @param clueRemark
	 * @return
	 */
	int updateRemarkById(ClueRemark clueRemark);

	/**
	 * 根据id删除备注
	 * @param id
	 * @return
	 */
	int deleteRemarkById(String id);

	
	/**
	 * 根据线索id删除备注
	 * @param id
	 * @return
	 */
	int deleteRemarkByClueId(String id);

}
