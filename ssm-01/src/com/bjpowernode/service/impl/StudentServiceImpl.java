package com.bjpowernode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjpowernode.beans.Student;
import com.bjpowernode.dao.StudentDao;
import com.bjpowernode.service.StudentService;
@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao studentDao;
	@Override
	public int addStudent(Student student) {
		
		return studentDao.insertStudent(student);
	}
	@Override
	public List<Student> listStudent() {
		
		return studentDao.selectStudent();
	}

}
