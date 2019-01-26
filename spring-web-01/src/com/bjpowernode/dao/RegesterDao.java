package com.bjpowernode.dao;

import java.util.List;

import com.bjpowernode.bean.Student;

public interface RegesterDao {
	int insertStudent(Student student);
	
	List<Student> selectStudent();
}
