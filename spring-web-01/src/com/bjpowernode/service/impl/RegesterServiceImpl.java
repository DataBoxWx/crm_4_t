package com.bjpowernode.service.impl;

import java.util.List;

import com.bjpowernode.bean.Student;
import com.bjpowernode.dao.RegesterDao;
import com.bjpowernode.service.RegesterService;

public class RegesterServiceImpl implements RegesterService {
	
	private RegesterDao regesterDao;
	
	public void setRegesterDao(RegesterDao regesterDao) {
		this.regesterDao = regesterDao;
	}

	@Override
	public int addStudent(Student student) {
		
		return regesterDao.insertStudent(student);
	}

	@Override
	public List<Student> queryStudents() {
		
		return regesterDao.selectStudent();
	}

}
