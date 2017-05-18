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
		UserController.class.get;
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
	

}
