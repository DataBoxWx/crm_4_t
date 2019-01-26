package com.bjpowernode.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.workbench.domain.Clue;

public interface ClueDao {

	int saveClue(Clue clue);

	List<Clue> display(Map<String, Object> map);

	int getTotal(Map<String, Object> map);

	Clue getClueById(String id);

	List<Clue> getAllClue();
	
	/**
	 * 根據Id取得clue對象
	 * @param id
	 * @return
	 */
	Clue getClueById3(String id);
	
	/**
	 * 根据id更新
	 * @param clue
	 * @return
	 */
	int updateClueById(Clue clue);
	
	/**
	 * 根据id数组删除线索
	 * @param ids
	 * @return
	 */
	int deleteClueById(String[] ids);
	
	/**
	 * 
	 * @param clueList
	 * @return
	 */
	int saves(List<Clue> clueList);
	
	/**
	 * 根据id删除线索
	 * @param id
	 * @return
	 */
	int deleteClueById2(String id);

}
