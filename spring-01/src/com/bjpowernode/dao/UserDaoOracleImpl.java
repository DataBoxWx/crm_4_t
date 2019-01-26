package com.bjpowernode.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository(value="userDaoImpl")
public class UserDaoOracleImpl implements UserDao{

	@Override
	public void insert() {
		System.out.println("执行的是Oracle插入");
	}

}
