package com.bjpowernode.service;

import java.util.List;

import com.bjpowernode.bean.Student;

public interface RegesterService {
	
	int addStudent(Student student);
	
	List<Student> queryStudents();
}
