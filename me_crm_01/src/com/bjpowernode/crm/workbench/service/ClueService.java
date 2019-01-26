package com.bjpowernode.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Transaction;

public interface ClueService {

	Map<String, Object> getOwnerClueResource();

	int saveClue(Clue clue);

	Map<String, Object> display(Map<String, Object> map);

	Clue getClueById(String id);

	List<Clue> getAllClue();

	Boolean getClueById2(String clueId,Transaction transaction,String operator);

	Map<String, Object> getClueByIdAndUser(String id);

	int updateClue(Clue clue);

	int deleteClueById(String[] ids);

	int saves(List<Clue> clueList);

	int DeleteClueAndRemarkAndRelationById(String id);

}
