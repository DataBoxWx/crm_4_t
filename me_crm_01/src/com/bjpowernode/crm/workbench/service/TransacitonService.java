package com.bjpowernode.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.util.ChangeStageException;
import com.bjpowernode.crm.workbench.domain.Transaction;

public interface TransacitonService {
	
	/**
	 * 根据名称模糊查询
	 * @param name
	 * @return
	 */
	List<Transaction> getActivityByName(String name);
	
	/**
	 * 保存交易
	 * @param transaction
	 * @return
	 */
	Boolean saveTransaction(Transaction transaction,String customerName);
	
	/**
	 * 分页查询
	 * @param inquireMap
	 * @return
	 */
	Map<String, Object> displayTransaction(Map<String, Object> inquireMap);
	
	/**
	 * 根据id获得交易
	 * @param id
	 * @return
	 */
	Transaction getById(String id);
	
	/**
	 * 更改阶段
	 * @param transaction
	 * @return
	 */
	Boolean changeStage(Transaction transaction) throws ChangeStageException;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	/**\
	 * 更新
	 * @param transaction
	 * @return
	 */
	Boolean update(Transaction transaction,String customerName);
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	Boolean deletes(String[] ids);
		
	/**
	 * 列表所有
	 * @return
	 */
	List<Transaction> getAll();
}
