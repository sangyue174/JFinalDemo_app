package com.myapp.utils.test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.jfinal.ext.test.ControllerTestCase;
import com.myapp.config.MyAppConfig;

public class JunitTest extends ControllerTestCase<MyAppConfig> {
	private int i = 11;
	public String str = "test";
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
		String url = "/user/forgetPassAction?identityType=phone&identifier=18615566651&credential=admin1&authCode=069052";
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
	
	// tempRecord
	@Test
	public void findTempRecordChartAction() {
		String url = "/tempRecord/findTempRecordChartAction?equipid=1&date=20170614";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void findTempRecordDateAction() {
		String url = "/tempRecord/findTempRecordDateAction?equipid=1&tokenKey=2de4e96238";
		System.out.println(use(url).invoke());
	}
	
	// 贴士相关
	@Test
	public void findTipListAction() {
		String url = "/tip/findTipListAction?tipType=cool&tokenKey=2de4e96238";
		System.out.println(use(url).invoke());
	}
	@Test
	public void findTipContentAction() {
		String url = "/tip/findTipContentAction?tipid=1&tokenKey=2de4e96238";
		System.out.println(use(url).invoke());
	}
	
	// 新增孩子信息
	@Test
	public void addKidAction() {
		String url = "/kid/addKidAction?nickname=测试孩子昵称1&sex=1&birthday=2017-6-17&healthIssue=0&tokenKey=2de4e96238";
		File imageFile = new File("E:/file/QQ图片20170311022435.jpg");
		System.out.println(use(url).post(imageFile).invoke());
	}
	
	public void test(String ss){
		System.out.println(ss);
	}
	public static void test2(){
		System.out.println("test2");
	}
	public static String test3(){
		System.out.println("test3");
		return "test3";
	}
	
	public static void main(String[] args) {
		try {
			JunitTest jnuit = new JunitTest();
			Class clazz = jnuit.getClass();
//			Field field1 = clazz.getDeclaredField("i");
//			System.out.println(field1);
//			System.out.println(field1.get(jnuit));
//			field1.set(jnuit, 123);
//			
//			System.out.println(field1.get(jnuit));
//			
//			Method method1 = clazz.getDeclaredMethod("test", String.class);
//			System.out.println(method1.invoke(jnuit, "sang"));
			Method method2 = clazz.getDeclaredMethod("test2");
			method2.invoke(null);
			
			Method method3 = clazz.getDeclaredMethod("test3");
			System.out.println(method3.invoke(null));
			
			
//			Field[] fields = clazz.getDeclaredFields();// 获得属性
//			for (Field field : fields) {
//				PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
//						clazz);
//				Method getMethod = pd.getReadMethod();// 获得get方法
//				Object o = getMethod.invoke(jnuit);// 执行get方法返回一个Object
//				System.out.println(o);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
