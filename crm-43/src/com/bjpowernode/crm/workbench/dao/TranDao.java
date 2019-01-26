package com.bjpowernode.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.workbench.domain.Tran;

public interface TranDao {

	/**
	 * 保存交易
	 * @param tran
	 */
	int save(Tran tran);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Tran getById(String id);

	/**
	 * 更新交易阶段
	 * @param tran
	 * @return
	 */
	int update(Tran tran);

	/**
	 * 
	 * @return
	 */
	List<Map<String, Object>> getCountByStage();

}
