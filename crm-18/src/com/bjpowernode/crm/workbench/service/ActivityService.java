package com.bjpowernode.crm.workbench.service;

import java.util.Map;

import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;

public interface ActivityService {

	/**
	 * 保存市场活动
	 * @param activity
	 * @return true表示成功，false表示失败！
	 */
	boolean save(Activity activity);

	/**
	 * 
	 * @param conditionMap
	 * @return
	 */
	PaginationVO<Activity> getPageByCondition(Map<String, Object> conditionMap);

}
