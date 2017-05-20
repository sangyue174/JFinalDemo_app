package com.myapp.utils.test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
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
public class JunitTest1 extends JFinalTest {
	private UserController usercon;

	@Before
	public void init() {
//		UserController.class.get;
//		getController("/account");
//		
//        Map<String, Class<? extends Model<?>>> modelMap = new HashMap<String, Class<? extends Model<?>>>(); //建立表与实体的映射
//        MockRoutes mockRoutes = new MockRoutes();
//        modelMap.put("account", Account.class); 
//        mockRoutes.add("/account", AccountController.class); //url与Controller的映射
//        init(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim(), modelMap, mockRoutes); //JFinalTest的初始化类
	}

	public static <T> void testMethod(T obj) {
		if (obj instanceof String) {
			System.out.println("String " + obj.toString());
		} else if (obj instanceof Integer) {
			System.out.println("Integer " + ((Integer) obj + 1));
		}
	}

	public static <T> T getMiddle(T[] test) {
		System.out.println("泛型方法");
		return test[test.length / 2];
	}

	public static <T> T getMiddle2(T t) {
		System.out.println("泛型方法");
		return null;
	}

	public static void main(String[] args) {
		// String[] s = {"AAA","BBB","CCC"};
		// System.out.println(JunitTest1.<String> getMiddle(s));// 在方法名前指定类型
		// System.out.println(<String>getMiddle(s));//不能这样用，虽然调用的是处在同一个类中静态方法,语法问题，<>不能加在方法名前
		// Date[] d = {new Date(),new Date(),new Date()};
		// System.out.println(getMiddle(d));//其实可以不指定参数，编译器有足够的信息推断出要调用的方法
		//
		// // int[] is = {100,200,300};
		// // System.out.println(getMiddle(is));// 这个是错误的，泛型T不支持基本数据类型int
		// char，要用他们的包装类
		// Integer[] is = new Integer[]{100,200,300};
		// System.out.println(getMiddle(is));// 这个是正确的，使用了包装类
		//

		// List<? extends Number> intList = new ArrayList<Integer>();
		// intList.add(234);
		// intList.get(0);

	}

	static <T> void show(List<T> list) {
		for (Object object : list) {
			System.out.println(object);
		}
	}

}
