package com.bjpowernode.crm.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.domain.DictionaryType;

public interface DictionaryService {

	int register(String code);

	int save(String code, String name, String discribe);

	StringBuffer list();

	String edit(String code);

	void update(String code, String name, String discribe);

	void delete(String[] ids);
	/**
	 * 获得所有列表
	 */
	List<DictionaryType> getAll();

	Map<String, Object> getAlls();

}
