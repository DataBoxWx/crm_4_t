package com.bjpowernode.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.domain.ActivityRemark;

public interface ActivityDao {
	/**
	 * 保存市场活动
	 * @param activity
	 * @return
	 */
	int save(Activity activity);
	/**
	 * 获取市场活动列表
	 * @param map
	 * @return
	 */
	List<Activity> list(Map<String, Object> map);
	/**
	 * 获取查询的总条数
	 * @param map
	 * @return
	 */
	int pageSum(Map<String, Object> map);
	/**
	 * 编辑时获取列表
	 * @param id
	 * @return
	 */
	Activity edit(String id);
	/**
	 * 编辑列表更新
	 * @param activity
	 * @return
	 */
	int update(Activity activity);
	
	/**
	 * 根据Id删除
	 * @param ids
	 * @return
	 */
	int delete(String[] ids);
	
	/**
	 * 根据ID	取得Activity对象
	 * @param id
	 * @return
	 */
	Activity getActivityById(String id);
	/**
	 * 根据id删除Activity
	 * @param id
	 * @return
	 */
	int deleteById(String id);
	
	/**
	 * 获得所有activity
	 * @return
	 */
	List<Activity> getAll();
	
	/**
	 * 通過數組獲得Activity集合
	 * @param ids
	 * @return
	 */
	List<Activity> getActivityById2(String[] ids);
	
	/**
	 * 
	 * @param activitieList
	 * @return
	 */
	int saves(List<Activity> activitieList);
	
	/**
	 * 根据clueId获得未关联A的ctivity
	 * @param clueId
	 * @return
	 */
	List<Activity> getByClueId(String name ,String clueId);
	
	/**
	 * 查所有
	 * @return
	 */
	List<Activity> getAll2();

}
