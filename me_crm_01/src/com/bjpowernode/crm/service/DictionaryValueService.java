package com.bjpowernode.crm.service;

import com.bjpowernode.crm.domain.DictionaryValue;

public interface DictionaryValueService {

	int checkValue(String typeCode, String value);

	void save(DictionaryValue dictionaryValue);

	StringBuffer index();

	StringBuffer getOne(String id);

	void update(DictionaryValue dictionaryValue);

	void delete(String[] ids);
}
