package com.bjpowernode.dao;

public class UserDaoMySQLImpl implements UserDao{
	
	public UserDaoMySQLImpl() {
		super();
		System.out.println("MySql被创建了");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert() {
		System.out.println("执行的是MySQL插入");
	}

}
