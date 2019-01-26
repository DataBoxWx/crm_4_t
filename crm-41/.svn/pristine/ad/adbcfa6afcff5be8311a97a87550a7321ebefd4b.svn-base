package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.exception.ChangeStageException;
import com.bjpowernode.crm.workbench.domain.Tran;

public interface TranService {

	/**
	 * 保存交易
	 * @param tran 交易
	 * @param customerName 客户名称
	 * @return
	 */
	boolean save(Tran tran, String customerName);

	/**
	 * 根据id获取交易信息
	 * @param id
	 * @return
	 */
	Tran getById(String id);

	/**
	 * 更新交易
	 * @param tran
	 * @return
	 */
	void update(Tran tran) throws ChangeStageException;

}
