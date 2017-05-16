package com.myapp.utils;

import java.util.Date;

public class TokenUtil {
	/**
	 * 生成token
	 * @return
	 */
	public static String generalTokenKey() {
		String tokenKey = Long.toHexString(new Date().getTime()-1300000000000L);
		return tokenKey;
	}
}
