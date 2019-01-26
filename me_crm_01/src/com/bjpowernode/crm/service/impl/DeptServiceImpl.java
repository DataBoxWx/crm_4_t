package com.bjpowernode.crm.service.impl;

import java.util.List;

import com.bjpowernode.crm.dao.DeptDao;
import com.bjpowernode.crm.domain.Dept;
import com.bjpowernode.crm.service.DeptService;
import com.bjpowernode.crm.util.SqlSessionUtil;

public class DeptServiceImpl implements DeptService {
	private DeptDao deptDao = SqlSessionUtil.getCurrentSqlSession().getMapper(DeptDao.class);
	@Override
	public StringBuffer index() {
		List<Dept> list = deptDao.getAll();
		StringBuffer buffer = new StringBuffer();
		//{"list":[{"code":?,"name":"?","manager":"?","phone":"?","description":"?"}]}
		buffer.append("{\"list\":[");
		for(int i=0;i<list.size();i++){
			Dept s = list.get(i);
			buffer.append("{\"code\":");
			buffer.append(s.getCode());
			buffer.append(",\"name\":\"");
			buffer.append(s.getName());
			buffer.append("\",\"manager\":\"");
			buffer.append(s.getManager());
			buffer.append("\",\"phone\":\"");
			buffer.append(s.getPhone());
			buffer.append("\",\"description\":\"");
			buffer.append(s.getDescription());
			buffer.append("\"}");
			if(i<list.size()-1){
				buffer.append(",");
			}
		}
		buffer.append("]}");
		return buffer;
	}
	@Override
	public int checkCode(String code) {
		int count = deptDao.checkCode(code);
		return count;
	}
	@Override
	public int doSave(Dept dept) {
		return deptDao.save(dept);
	}
	@Override
	public Dept doEdit(int code) {
		
		return deptDao.edit(code);
	}
	@Override
	public int update(Dept dept) {
		
		return deptDao.update(dept);
	}
	@Override
	public int doDelete(String[] codesStr) {
		int []codes = new int[codesStr.length];
		for(int i=0;i<codesStr.length;i++){
			codes[i] = Integer.valueOf(codesStr[i]);
		}
		return deptDao.delete(codes);
	}

}
