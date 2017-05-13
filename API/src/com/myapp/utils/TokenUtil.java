package com.myapp.utils;

import java.util.Date;

public class TokenUtil {
	/**
	 * 生成token
	 * @return
	 */
	public static String generalKey() {
		String uuid = Long.toHexString(new Date().getTime()-1300000000000L);
		return uuid;
	}
}
