package com.myapp.utils.test;

import org.junit.Test;

import com.jfinal.ext.test.ControllerTestCase;
import com.myapp.config.MyAppConfig;
import com.myapp.utils.PasswordUtil;

public class JunitTest extends ControllerTestCase<MyAppConfig> {
	// User
	@Test
	public void registerAction() {
		String url = "";
		url = "/user/registerAction?identityType=phone&identifier=18615566651&credential=admin&authCode=1234";
		// String body = "<root>中文</root>";
		// use(url).post(body).invoke();
		System.out.println(use(url).invoke());
	}
	@Test
	public void loginAction() {
		String url = "/user/loginAction?identityType=phone&identifier=18615566651&credential=admin";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void logOutAction() {
		String url = "/user/logOutAction?identityType=phone&identifier=18615566651";
		System.out.println(use(url).invoke());
	}
	@Test
	public void forgetPassAction() {
		String url = "/user/forgetPassAction?identityType=phone&identifier=18615566651&credential=admin1&authCode=1234";
		System.out.println(use(url).invoke());
	}
	@Test
	public void modifyPassAction() {
		String url = "/user/modifyPassAction?identityType=phone&identifier=18615566651&preCredential=admin1&newCredential=admin";
		System.out.println(use(url).invoke());
	}
	
	// 发送验证码
	@Test
	public void sendCodeAction() {
		String url = "/auth/sendCodeAction?identifier=18615566651";
		System.out.println(use(url).invoke());
	}
	
	// 
	
	public static void main(String[] args) {
		byte[] saltByte = PasswordUtil.getSalt();
		System.out.println(saltByte.toString());
	}

}
