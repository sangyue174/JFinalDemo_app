package com.myapp.utils;

public class CodeUtil {
	/**
	 * 生成验证码
	 * 
	 * @title: generateCode
	 * @author sangyue
	 * @date Jun 11, 2017 3:16:20 PM
	 * @return
	 * @version V1.0
	 */
	public static String generateCode() {
		int a = (int) (Math.random() * 100) + 1;
		int b = (int) (Math.random() * 100) + 1;
		String aStr = "";
		String bStr = "";
		if (String.valueOf(a).length() == 1) {
			aStr = "00" + a;
		} else if (String.valueOf(a).length() == 2) {
			aStr = "0" + a;
		} else {
			aStr = String.valueOf(a);
		}
		if (String.valueOf(b).length() == 1) {
			bStr = "00" + b;
		} else if (String.valueOf(b).length() == 2) {
			bStr = "0" + b;
		} else {
			bStr = String.valueOf(b);
		}
		String code = aStr + bStr;
		return code;
	}
}
