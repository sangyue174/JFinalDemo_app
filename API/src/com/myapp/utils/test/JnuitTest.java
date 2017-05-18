package com.myapp.utils.test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Model;
import com.myapp.config.MyAppConfig;
import com.myapp.jfinaltest.annotation.JFinalTestConfig;
import com.myapp.jfinaltest.core.JFinalTest;
import com.myapp.module.user.controller.UserController;
@JFinalTestConfig(value = MyAppConfig.class)
public class JnuitTest extends JFinalTest {
	private UserController usercon;
	@Before
	public void init() {
		usercon = UserController.getInstance();
		getController("/account");
		
        Map<String, Class<? extends Model<?>>> modelMap = new HashMap<String, Class<? extends Model<?>>>(); //建立表与实体的映射
        MockRoutes mockRoutes = new MockRoutes();
        modelMap.put("account", Account.class); 
        mockRoutes.add("/account", AccountController.class); //url与Controller的映射
        init(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim(), modelMap, mockRoutes); //JFinalTest的初始化类
	}

	@Test
	public void testRegisterAction() {
		// String url =
		// "/user/registerAction?identityType=phone&identifier=testname&credential=testpassword";
		// String body = "";
	}
	
	

	public static void main(String[] args) {
		// System.out.println(JnuitTest.class.getResource(""));
		// System.out.println(JnuitTest.class.getResource("/"));
		//
		// System.out.println(JnuitTest.class.getClassLoader().getResource(""));
		// System.out.println(JnuitTest.class.getClassLoader().getResource("/"));

		// Class[] types = types(new Object[0]);
		// Reflect.on("com.myapp.module.user.controller.UserController").call(
		// "registerAction");
		Class<?> c;
		try {
			c = Class.forName("com.myapp.module.user.controller.UserController");
			Method m = c.getMethod("registerAction", new Class[] {});
			// Object obj=c.newInstance();
			m.invoke(c.newInstance(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
