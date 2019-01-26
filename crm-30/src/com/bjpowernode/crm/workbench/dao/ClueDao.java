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

}
