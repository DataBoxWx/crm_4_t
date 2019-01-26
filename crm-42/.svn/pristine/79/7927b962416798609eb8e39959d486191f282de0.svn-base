package com.bjpowernode.crm.settings.dao;

import java.util.List;

import com.bjpowernode.crm.settings.domain.DicValue;

public interface DicValueDao {

	/**
	 * 
	 * @param typeCode
	 * @param value
	 * @return
	 */
	DicValue getByTypeCodeAndValue(String typeCode, String value);

	/**
	 * 
	 * @param dv
	 * @return
	 */
	int save(DicValue dv);

	/**
	 * 根据字典类型编码获取对应的字典值
	 * @param typeCode
	 * @return
	 */
	List<DicValue> getByTypeCode(String typeCode);

}
