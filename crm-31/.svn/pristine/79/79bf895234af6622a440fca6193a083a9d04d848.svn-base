package com.bjpowernode.crm.settings.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

public class DicValueServiceImpl implements DicValueService {

	private DicValueDao dvd = SqlSessionUtil.getCurrentSqlSession().getMapper(DicValueDao.class);

	@Override
	public DicValue getByTypeCodeAndValue(String typeCode, String value) {
		return dvd.getByTypeCodeAndValue(typeCode, value);
	}

	@Override
	public int save(DicValue dv) {
		return dvd.save(dv);
	}

	@Override
	public Map<String, List<DicValue>> getAll() {
		Map<String, List<DicValue>> dicValueMap = new HashMap<>();
		dicValueMap.put("appellationList", dvd.getByTypeCode("appellation"));
		dicValueMap.put("clueStateList", dvd.getByTypeCode("clueState"));
		dicValueMap.put("returnPriorityList", dvd.getByTypeCode("returnPriority"));
		dicValueMap.put("returnStateList", dvd.getByTypeCode("returnState"));
		dicValueMap.put("sourceList", dvd.getByTypeCode("source"));
		dicValueMap.put("stageList", dvd.getByTypeCode("stage"));
		dicValueMap.put("transactionTypeList", dvd.getByTypeCode("transactionType"));
		return dicValueMap;
	}

}
