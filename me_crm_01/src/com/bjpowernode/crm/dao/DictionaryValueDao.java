package com.bjpowernode.crm.dao;

import java.util.List;

import com.bjpowernode.crm.domain.DictionaryType;
import com.bjpowernode.crm.domain.DictionaryValue;

public interface DictionaryValueDao {

	int checkValue(String typeCode, String value);

	void save(DictionaryValue dictionaryValue);

	List<DictionaryValue> index();

	DictionaryValue getOne(String id);

	void update(DictionaryValue dictionaryValue);

	void delete(String[] ids);

	List<DictionaryValue> getAll(String code);

	

}
