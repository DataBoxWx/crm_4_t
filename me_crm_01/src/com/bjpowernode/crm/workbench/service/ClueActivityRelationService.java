package com.bjpowernode.crm.workbench.service;

import java.util.List;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationService {

	List<Activity> getAllByClueId(String clueId);
	
	/**
	 * 根据id解除市场活动关联
	 * @param id
	 * @return
	 */
	int unbundRelation(String id);
	
	/**
	 * 根据市场活动和线索ID增加关系
	 * @param list
	 * @return
	 */
	int saveByActivityIdAndClueId(List<ClueActivityRelation> list);

}
