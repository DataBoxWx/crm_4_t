package com.bjpowernode.crm.workbench.service;

import java.util.List;
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
	
	/**
	 * 根据id获取市场活动
	 * @param id
	 * @return
	 */
	Map<String, Object> getById(String id);

	/**
	 * 更新市场活动
	 * @param a
	 * @return
	 */
	boolean update(Activity a);

	/**
	 * 删除市场活动
	 * @param ids
	 * @return
	 */
	boolean deleteById(String[] ids);

	/**
	 * 根据市场活动id获取市场活动，返回的owner是用户的name
	 * @param id
	 * @return
	 */
	Activity getById2(String id);

	/**
	 * 获取所有的市场活动
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
	boolean save2(List<Activity> activityList);

	/**
	 * 根据市场活动名称以及线索id获取市场活动列表
	 * @param activityName
	 * @param clueId
	 * @return
	 */
	List<Activity> getByNameAndClueId(String activityName, String clueId);

}



