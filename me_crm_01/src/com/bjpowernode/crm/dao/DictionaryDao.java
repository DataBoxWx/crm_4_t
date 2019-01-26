package com.bjpowernode.crm.dao;

import java.util.List;

import com.bjpowernode.crm.domain.DictionaryType;

public interface DictionaryDao {

	int register(String code);

	int save(String code, String name, String discribe);

	List<DictionaryType> list();

	DictionaryType edit(String code);

	void update(String code, String name, String discribe);

	void deletes(String[] ids);

	List<DictionaryType> getAll();

}
