package com.bjpowernode.crm.workbench.service;

import java.util.List;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.domain.ActivityRemark;

public interface ActivityRemarkService {

	Activity getActivityById(String id);

	List<ActivityRemark> getAll(String id);

	int remarkSave(ActivityRemark activityRemark);

	int updateActivityById(Activity activity);

	int deleteById(String id);

	int deleteRemarkById(String id);

	int updateRemark(ActivityRemark activityRemark);

}
