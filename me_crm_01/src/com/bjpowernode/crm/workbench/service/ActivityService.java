package com.bjpowernode.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.domain.ActivityRemark;

public interface ActivityService {

	int save(Activity activity);

	Map<String,Object> doList(Map<String, Object> map);

	Activity edit(String id);

	Boolean update(Activity activity);

	Boolean dodelete(String[] ids);

	List<Activity> getAllActivity();

	List<Activity> getActivityById(String[] ids);

	int saves(List<Activity> activitieList);

	List<Activity> getByClueId(String name,String clueId);

	List<Activity> getAll();

}
