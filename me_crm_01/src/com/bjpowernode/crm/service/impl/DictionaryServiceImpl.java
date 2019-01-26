package com.bjpowernode.crm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.dao.DictionaryDao;
import com.bjpowernode.crm.dao.DictionaryValueDao;
import com.bjpowernode.crm.domain.DictionaryType;
import com.bjpowernode.crm.domain.DictionaryValue;
import com.bjpowernode.crm.service.DictionaryService;
import com.bjpowernode.crm.util.SqlSessionUtil;

public class DictionaryServiceImpl implements DictionaryService {
	private DictionaryDao dictionaryDao = SqlSessionUtil.getCurrentSqlSession().getMapper(DictionaryDao.class);
	private DictionaryValueDao dictionaryValueDao = SqlSessionUtil.getCurrentSqlSession().getMapper(DictionaryValueDao.class);
	@Override
	public int register(String code) {
		int count = dictionaryDao.register(code);
		return count;
	}
	@Override
	public int save(String code, String name, String discribe) {
		int count = dictionaryDao.save(code,name,discribe);
		return count;
	}
	@Override
	public StringBuffer list() {
		List<DictionaryType> list = dictionaryDao.list();
		StringBuffer buffer = new StringBuffer();
		//{"list":[{"code":"?","name":"?","discription":"?"},]}
		buffer.append("{\"list\":[");
		for(int i = 0;i<list.size();i++){
			DictionaryType d = list.get(i);
			buffer.append("{\"code\":\"");
			buffer.append(d.getCode());
			buffer.append("\",\"name\":\"");
			buffer.append(d.getName());
			buffer.append("\",\"discription\":\"");
			buffer.append(d.getDiscription());
			buffer.append("\"}");
			if(i < list.size()-1){
				buffer.append(",");				
			}
		}
		buffer.append("]}");
		return buffer;
	}
	@Override
	public String edit(String code) {
		DictionaryType dictionaryType = dictionaryDao.edit(code);
		//{"code":"?","name":"?","discription":"?"}
		String str = "{\"code\":\""+dictionaryType.getCode()+"\",\"name\":\""+dictionaryType.getName()+"\",\"discription\":\""+dictionaryType.getDiscription()+"\"}";
		return str;
	}
	@Override
	public void update(String code, String name, String discribe) {
		dictionaryDao.update(code,name,discribe);
		
	}
	@Override
	public void delete(String[] ids) {
		dictionaryDao.deletes(ids);
		
	}
	@Override
	public List<DictionaryType> getAll() {
		List<DictionaryType> list= dictionaryDao.getAll();
		return list;
	}
	@Override
	public Map<String, Object> getAlls() {
		List<DictionaryType> listType = dictionaryDao.getAll();
		System.out.println(listType.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (DictionaryType d : listType) {
			List<DictionaryValue> listValue = dictionaryValueDao.getAll(d.getCode());
			
			System.out.println(listValue.toString());
			
			map.put(d.getCode(), listValue);
		}
		
		return map;
	}

}
