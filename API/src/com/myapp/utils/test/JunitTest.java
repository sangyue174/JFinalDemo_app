package com.myapp.utils.test;

import org.junit.Test;

import com.jfinal.ext.test.ControllerTestCase;
import com.myapp.config.MyAppConfig;
import com.myapp.utils.PasswordUtil;

public class JunitTest extends ControllerTestCase<MyAppConfig> {
	@Test
	public void test3() {
		String url = "";
		url = "/user/registerAction?identityType=phone&identifier=testname&credential=testpassword";
		url = "/user/registerAction?identityType=phone&identifier=18615566651&credential=testpassword";
		// String body = "<root>中文</root>";
		// use(url).post(body).invoke();
		System.out.println(use(url).invoke());
	}
	
	public static void main(String[] args) {
		byte[] saltByte = PasswordUtil.getSalt();
		System.out.println(saltByte.toString());
	}

}
