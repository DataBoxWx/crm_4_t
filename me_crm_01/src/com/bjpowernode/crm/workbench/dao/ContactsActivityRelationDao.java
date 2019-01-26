package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;

public interface ContactsActivityRelationDao {
	/**
	 * b保存关系
	 * @param carList
	 * @return
	 */
	int saves(List<ContactsActivityRelation> carList);
	
	/**
	 * 解除关联
	 * @param activityId
	 * @param contactsId
	 * @return
	 */
	Boolean unband(String activityId, String contactsId);

	

}
