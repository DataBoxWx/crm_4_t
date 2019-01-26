package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Clue;

public interface ClueDao {

	/**
	 * 
	 * @param clue
	 * @return
	 */
	int save(Clue clue);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Clue getById(String id);

	/**
	 * 根据线索id获取线索对象，线索的所有者是id形式。
	 * @param clueId
	 * @return
	 */
	Clue getById2(String clueId);

	/**
	 * 根据id删除线索
	 * @param clueId
	 */
	void deleteById(String clueId);

}
