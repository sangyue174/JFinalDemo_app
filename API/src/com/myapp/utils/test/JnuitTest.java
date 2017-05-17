package com.myapp.utils.test;

import org.junit.Test;

import com.jfinal.ext.kit.Reflect;
import com.jfinal.ext.test.ControllerTestCase;

public class JnuitTest extends BaseTest {
	@Test
	public void testRegisterAction() {
		// String url =
		// "/user/registerAction?identityType=phone&identifier=testname&credential=testpassword";
		// String body = "";
		Reflect.on("com.myapp.module.user.controller.UserController").call(
				"registerAction");
	}

	public static void main(String[] args) {
//		System.out.println(JnuitTest.class.getResource(""));
//		System.out.println(JnuitTest.class.getResource("/"));
//
//		System.out.println(JnuitTest.class.getClassLoader().getResource(""));
//		System.out.println(JnuitTest.class.getClassLoader().getResource("/"));

//		Class[] types = types(new Object[0]);
		Reflect.on("com.myapp.module.user.controller.UserController").call(
				"registerAction");
	}

	private static Class<?>[] types(Object[] values) {
		if (values == null) {
			return new Class[0];
		}

		Class[] result = new Class[values.length];

		for (int i = 0; i < values.length; ++i) {
			Object value = values[i];
			result[i] = ((value == null) ? Object.class : value.getClass());
		}

		return result;
	}
}
