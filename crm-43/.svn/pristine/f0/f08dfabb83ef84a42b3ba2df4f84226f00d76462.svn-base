package com.bjpowernode.crm.settings.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.settings.domain.DicValue;

public interface DicValueService {

	/**
	 * 通过字典类型编码和字典值获取字典值对象
	 * @param typeCode 字典类型编码
	 * @param value 字典值
	 * @return null表示该字典值对象不存在
	 */
	DicValue getByTypeCodeAndValue(String typeCode, String value);

	/**
	 * 保存字典值
	 * @param dv
	 * @return
	 */
	int save(DicValue dv);

	/**
	 * 获取所有的字典值（根据字典类型编码typeCode）
	 * @return
	 */
	Map<String, List<DicValue>> getAll();

}
