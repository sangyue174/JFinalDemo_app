package com.myapp.utils.test;

import java.lang.reflect.Field;

import org.junit.Test;

import com.jfinal.ext.kit.Reflect;
import com.jfinal.ext.test.ControllerTestCase;
import com.myapp.bean.User;
import com.myapp.config.MyAppConfig;

public class JunitTest extends ControllerTestCase<MyAppConfig> {
	private int i = 11;
	public String str = "test";
	public static User user;
	// User
	@Test
	public void registerAction() {
		String url = "";
		url = "/user/registerAction?identifier=18605409884&credential=admin&authCode=051043";
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
		String url = "/user/forgetPassAction?identifier=18615566651&credential=admin&authCode=069052";
		System.out.println(use(url).invoke());
	}
	@Test
	public void modifyPassAction() {
		String url = "/user/modifyPassAction?identifier=18615566651&preCredential=admin&newCredential=admin";
		System.out.println(use(url).invoke());
	}
	
	// 发送验证码
	@Test
	public void sendCodeAction() {
//		String url = "/auth/sendCodeAction?identifier=18605409884";
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
		String url = "/tempRecord/findTempRecordDateAction?equipid=1&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
	}
	
	// 贴士相关
	@Test
	public void findTipListAction() {
		String url = "/tip/findTipListAction?tipType=cool&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
	}
	@Test
	public void findTipContentAction() {
		String url = "/tip/findTipContentAction?tipid=1&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
	}
	
	// 孩子相关
	@Test
	public void addKidAction() {
		String url = "/kid/addKidAction?nickname=测试孩子昵称1&sex=1&birthday=2017-6-17&headurl=http://123.com&healthIssue=0&tokenKey=2e0dc1ed76";
//		File imageFile = new File("D:/file/图片test.gif");
		System.out.println(use(url).invoke());
	}
	@Test
	public void findKidAction() {
		String url = "/kid/findKidAction?kidid=1&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void updateKidAction() {
		String url = "/kid/updateKidAction?kidid=1&nickname=测试孩子昵称2&sex=1&birthday=2016-6-17&headurl=http://1234.com&healthIssue=1&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
	}
	
	// 设备相关
	@Test
	public void addEquipmentAction() {
		String url = "/equipment/addEquipmentAction?kidid=1&number=testnum&maxtime=40&mintime=38.5&ismaxalarm=0&isminalarm=0&isnotice=0&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void deleteEquipmentAction() {
		String url = "/equipment/deleteEquipmentAction?number=testnum&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void activeEquipmentAction() {
		String url = "/equipment/activeEquipmentAction?number=testnum&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void updateEquipmentAction() {
		String url = "/equipment/updateEquipmentAction?number=testnum&maxtime=41&mintime=38.6&ismaxalarm=1&isminalarm=1&isnotice=1&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void findEquipmentAction() {
		String url = "/equipment/findEquipmentAction?number=testnum&tokenKey=2e0dc1ed76";
		System.out.println(use(url).invoke());
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
			Field field1 = clazz.getDeclaredField("str");
//			System.out.println(field1);
//			System.out.println(field1.get(jnuit));
//			field1.set(jnuit, 123);
//			Reflect.on(field1.get(jnuit));
			
			user = new User();
			user.setId(123);
			user.setIsactive("1");
			Field field3 = clazz.getDeclaredField("user");
			System.out.println(field3.get(jnuit));
			Reflect.on(field3.get(jnuit));
			
//			Method method1 = clazz.getDeclaredMethod("test", String.class);
//			System.out.println(method1.invoke(jnuit, "sang"));
//			Method method2 = clazz.getDeclaredMethod("test2");
//			method2.invoke(null);
//			
//			Method method3 = clazz.getDeclaredMethod("test3");
//			System.out.println(method3.invoke(null));
//			System.out.println(method3.getClass());
			
			
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
