package com.bjpowernode.crm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private DateUtil() {
	}

	public static String getSysTime() {
		return new SimpleDateFormat(Const.DATE_ALL_FORMAT).format(new Date());
	}
}
