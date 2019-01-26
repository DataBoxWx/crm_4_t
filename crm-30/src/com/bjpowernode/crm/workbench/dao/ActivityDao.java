package com.bjpowernode.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.workbench.domain.Activity;

public interface ActivityDao {

	/**
	 * 保存市场活动
	 * 
	 * @param activity
	 * @return
	 */
	int save(Activity activity);

	/**
	 * 获取符合条件的总记录条数
	 * 
	 * @param conditionMap
	 * @return
	 */
	Long getTotalByCondition(Map<String, Object> conditionMap);

	/**
	 * 获取符合条件的当前页数据
	 * 
	 * @param conditionMap
	 * @return
	 */
	List<Activity> getDataListByCondition(Map<String, Object> conditionMap);

	/**
	 * 根据id获取市场活动
	 * 
	 * @param id
	 * @return
	 */
	Activity getById(String id);

	/**
	 * 更新市场活动
	 * @param a
	 * @return
	 */
	int update(Activity a);

	/**
	 * 
	 * @param ids
	 * @return
	 */
	int deleteById(String[] ids);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Activity getById2(String id);

	/**
	 * 
	 * @return
	 */
	List<Activity> getAll();

	/**
	 * 
	 * @param ids
	 * @return
	 */
	List<Activity> getById3(String[] ids);

	/**
	 * 保存多个市场活动
	 * @param activityList
	 * @return
	 */
	int save2(List<Activity> activityList);

}
