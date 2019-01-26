package com.bjpowernode.crm.util;

import java.util.UUID;

public class UUIDGenerator {

	private UUIDGenerator() {

	}

	public static String generate() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
