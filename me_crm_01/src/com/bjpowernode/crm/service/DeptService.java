package com.bjpowernode.crm.service;

import com.bjpowernode.crm.domain.Dept;

public interface DeptService {

	StringBuffer index();

	int checkCode(String code);

	int doSave(Dept dept);

	Dept doEdit(int code);

	int update(Dept dept);

	int doDelete(String[] codesStr);

}
