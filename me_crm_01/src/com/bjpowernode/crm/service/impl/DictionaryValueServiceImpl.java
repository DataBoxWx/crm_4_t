package com.bjpowernode.crm.service.impl;

import java.util.List;

import com.bjpowernode.crm.dao.DictionaryValueDao;
import com.bjpowernode.crm.domain.DictionaryValue;
import com.bjpowernode.crm.service.DictionaryValueService;
import com.bjpowernode.crm.util.SqlSessionUtil;

public class DictionaryValueServiceImpl implements DictionaryValueService {
	private DictionaryValueDao dictionaryValueDao = SqlSessionUtil.getCurrentSqlSession().getMapper(DictionaryValueDao.class);
	@Override
	public int checkValue(String typeCode, String value) {
		int count = dictionaryValueDao.checkValue(typeCode,value);
		return count;
	}
	@Override
	public void save(DictionaryValue dictionaryValue) {
		dictionaryValueDao.save(dictionaryValue);
		
	}
	@Override
	public StringBuffer index() {
		List<DictionaryValue> list = dictionaryValueDao.index();
		StringBuffer buffer = new StringBuffer();
		//id,value,text,orderNo,typeCode
		//{"list":[{"id":"?","value":"?","text":"?","orderNo":"?","typeCode":"?"}]}
		buffer.append("{\"list\":[");
		for(int i =0; i<list.size();i++){
			DictionaryValue d = list.get(i);
			buffer.append("{\"id\":\"");
			buffer.append(d.getId());
			buffer.append("\",\"value\":\"");
			buffer.append(d.getValue());
			buffer.append("\",\"text\":\"");
			buffer.append(d.getText());
			buffer.append("\",\"orderNo\":\"");
			buffer.append(d.getOrderNo());
			buffer.append("\",\"typeCode\":\"");
			buffer.append(d.getTypeCode());
			buffer.append("\"}");
			if(i<list.size()-1){
				buffer.append(",");
			}
		}
		buffer.append("]}");
		return buffer;
	}
	@Override
	public StringBuffer getOne(String id) {
		DictionaryValue d = dictionaryValueDao.getOne(id);
		StringBuffer buffer = new StringBuffer();
		//{"id":"?","value":"?","text":"?","orderNo":"?","typeCode":"?"}
		buffer.append("{\"id\":\"");
		buffer.append(d.getId());
		buffer.append("\",\"value\":\"");
		buffer.append(d.getValue());
		buffer.append("\",\"text\":\"");
		buffer.append(d.getText());
		buffer.append("\",\"orderNo\":\"");
		buffer.append(d.getOrderNo());
		buffer.append("\",\"typeCode\":\"");
		buffer.append(d.getTypeCode());
		buffer.append("\"}");
		return buffer;
	}
	@Override
	public void update(DictionaryValue dictionaryValue) {
		dictionaryValueDao.update(dictionaryValue);
		
	}
	@Override
	public void delete(String[] ids) {
		dictionaryValueDao.delete(ids);
		
	}

}
