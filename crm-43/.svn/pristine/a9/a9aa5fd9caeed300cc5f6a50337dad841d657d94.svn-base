package com.bjpowernode.crm.settings.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.utils.TransactionHandler;

public class DicTypeServiceImplTest {

	@Test
	public void testGetByCode() {
		DicTypeService dts = (DicTypeService)new TransactionHandler(new DicTypeServiceImpl()).getProxy();
		DicType dt = dts.getByCode("source");
		System.out.println(dt.getCode());
		System.out.println(dt.getName());
		System.out.println(dt.getDescription());
	}

	@Test
	public void testSave() {
		DicTypeService dts = (DicTypeService)new TransactionHandler(new DicTypeServiceImpl()).getProxy();
		DicType dt = new DicType();
		dt.setCode("orgType");
		dt.setName("机构类型");
		dt.setDescription("机构类型包括一级部门二级部门等.");
		int count = dts.save(dt);
		assertEquals(1, count);
	}

	@Test
	public void testGetAll() {
		DicTypeService dts = (DicTypeService)new TransactionHandler(new DicTypeServiceImpl()).getProxy();
		List<DicType> dtList = dts.getAll();
		for(DicType dt : dtList){
			System.out.println(dt.getCode() + "," + dt.getName() + "," + dt.getDescription());
		}
	}

}



















