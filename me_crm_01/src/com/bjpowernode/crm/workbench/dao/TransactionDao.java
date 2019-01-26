package com.bjpowernode.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.workbench.domain.Transaction;

public interface TransactionDao {

	int save1(Transaction transaction);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	List<Transaction> getActivityByName(String name);
	
	/**
	 * 得到数据数量
	 * @return
	 */
	Long getCount(Map<String, Object> inquireMap);
	
	/**
	 * 得到列表
	 * @param inquireMap
	 * @return
	 */
	List<Transaction> getList(Map<String, Object> inquireMap);
	
	/**
	 * 根据Id获取交易
	 * @param id
	 * @return
	 */
	Transaction getById(String id);
	
	/**
	 * 更新阶段
	 * @param transaction
	 * @return
	 */
	int update(Transaction transaction);
	
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	List<Transaction> getTransactionBycustomerId(String customerId);
	
	/**
	 * 分组查询得到各阶段的数量
	 * @return
	 */
	List<Map<String, Object>> getCountByStage();
	
	/**
	 * 
	 * @param contactsId
	 * @return
	 */
	List<Transaction> getByContactsId(String contactsId);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	/**
	 * 更新
	 * @param transaction
	 * @return
	 */
	int update2(Transaction transaction);
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	int deletes(String[] ids);
	
	/**
	 * 
	 * @return
	 */
	List<Transaction> getAll();
	

}
