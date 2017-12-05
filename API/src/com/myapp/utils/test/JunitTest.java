package com.myapp.utils.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.alibaba.druid.filter.config.ConfigTools;
import com.google.common.base.Predicates;
import com.jfinal.ext.kit.Reflect;
import com.jfinal.ext.test.ControllerTestCase;
import com.myapp.bean.User;
import com.myapp.config.MyAppConfig;

import fr.free.jnizet.retry.Retryer;
import fr.free.jnizet.retry.RetryerBuilder;
import fr.free.jnizet.retry.StopStrategies;
import fr.free.jnizet.retry.WaitStrategies;

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
//		String url = "/user/loginAction?identityType=qq&identifier=82C20C3F0156317815D84772F53AA3C6&tokenKey=755B56D28F96C81E6E1D43D8873CEA3B&expiresIn=7776000";
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
		String url = "/user/modifyPassAction?tokenKey=3173ce35d4&preCredential=admin&newCredential=admin";
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
		String url = "/tempRecord/findTempRecordChartAction?kidid=17&date=20171204&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void findTempRecordDateAction() {
		String url = "/tempRecord/findTempRecordDateAction?kidid=17&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void addTempRecordsAction() {
		String url = "/tempRecord/addTempRecordsAction?equipnum=testnum33&kidid=17&records=[{\"time\":\"2017-11-20 10:10:10\",\"temp\":\"36.6\"},{\"time\":\"2017-11-21 13:13:13\",\"temp\":\"37.6\"}]&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	// 贴士相关
	@Test
	public void findTipListAction() {
		String url = "/tip/findTipListAction?tipType=cool&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	@Test
	public void findTipContentAction() {
		String url = "/tip/findTipContentAction?tipid=1&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	// 孩子相关
	@Test
	public void addKidAction() {
		String url = "/kid/addKidAction?nickname=测试新孩子&sex=1&birthday=2017-6-17&headurl=http://123.com&healthIssue=0&tokenKey=3173ce35d4&maxtime=38.3&mintime=35.2&ismaxalarm=1&isminalarm=1&isnotice=1";
//		File imageFile = new File("D:/file/图片test.gif");
		System.out.println(use(url).invoke());
	}
	@Test
	public void findKidAction() {
		String url = "/kid/findKidAction?kidid=17&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void updateKidAction() {
		String url = "/kid/updateKidAction?kidid=17&nickname=测试新孩子2&sex=0&birthday=2016-6-16&headurl=http://1234.com&healthIssue=1&tokenKey=3173ce35d4&maxtime=38.4&mintime=34.2&ismaxalarm=0&isminalarm=0&isnotice=0&equipnum=testnum22";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void findKidListAction() {
		String url = "/kid/findKidListAction?tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	// 设备相关
	@Test
	public void addEquipmentAction() {
		String url = "/equipment/addEquipmentAction?kidid=1&number=testnum1&maxtime=40&mintime=38.5&ismaxalarm=0&isminalarm=0&isnotice=0&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void rebindEquipmentWithKidAction() {
		String url = "/equipment/rebindEquipmentWithKidAction?kidid=17&equipnum=testnum22&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void deleteEquipmentAction() {
		String url = "/equipment/deleteEquipmentAction?kidid=17&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void activeEquipmentAction() {
		String url = "/equipment/activeEquipmentAction?equipnum=testnum&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void updateEquipmentAction() {
		String url = "/equipment/updateEquipmentAction?equipnum=testnum&maxtime=41&mintime=38.6&ismaxalarm=1&isminalarm=1&isnotice=1&tokenKey=3173ce35d4";
		System.out.println(use(url).invoke());
	}
	
	@Test
	public void findEquipmentAction() {
		String url = "/equipment/findEquipmentAction?equipnum=testnum&tokenKey=3173ce35d4";
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
			System.out.println(field1);
			System.out.println(field1.get(jnuit));
			field1.set(jnuit, 123);
			Reflect.on(field1.get(jnuit));
			
			user = new User();
			user.setId(123);
			user.setIsactive("1");
			Field field3 = clazz.getDeclaredField("user");
			System.out.println(field3.get(jnuit));
			Reflect.on(field3.get(jnuit));
			
			Method method1 = clazz.getDeclaredMethod("test", String.class);
			System.out.println(method1.invoke(jnuit, "sang"));
			Method method2 = clazz.getDeclaredMethod("test2");
			method2.invoke(null);
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

			final BlockingQueue<Info> queue = new LinkedBlockingQueue<Info>(100); 
			
			final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(6, 6,
					0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
			List<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
			final Thread t = new Thread(new Runnable(){
				volatile boolean stopFlag = false;
				@Override
				public void run() {
					while(!stopFlag){
						try {
							Thread.sleep(500);
							Info info = queue.poll(1000, TimeUnit.MILLISECONDS);
							
							if(info != null){
								System.out.println("queue value is "+info.getUnitUid());
								boolean retryFlag = testRetry();
								System.out.println("retryFlag is "+ retryFlag);
							}
							if(threadPool.isTerminated()){
								System.out.println("threadPool.isTerminated()" +threadPool.isTerminated());
								if(queue.size() == 0){
									return;
								}
							}
						} catch (Exception e) {
							System.out.println("occur exception "+ e);
							stopFlag = true;
						}
					}
				}
			});
			t.start();
			
			for (final Integer i : list) {
//				if (threadPool.isShutdown()) {
//					break;
//				}
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						System.out.println(Thread.currentThread() + "aaa" + i);
						try {
//							Thread.sleep(2000);
							queue.put(new JunitTest().new Info(i));
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					}
				});
			}
		
			threadPool.shutdown();
			while (!threadPool.isTerminated()) {
				try {
					Thread.sleep(200);
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			while(t.isAlive()){
				Thread.sleep(200);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean testRetry() throws Exception
	{  
	    Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()  
	            .retryIfResult(Predicates.equalTo(false)) // 返回false时重试  
	            .retryIfExceptionOfType(Exception.class) // 抛出Exception时重试  
	            .withWaitStrategy(WaitStrategies.fixedWait(2000, TimeUnit.MILLISECONDS)) // 200ms后重试  
	            .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试3次后停止  
	            .build();  
		return retryer.call(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				System.out.println("call throw exception");
				throw new Exception("throw exception");
			}
		});
	} 
	
	public class Info{
		private Integer unitUid;
		
		Info(Integer unitUid){
			this.unitUid = unitUid;
		}
		/**
		 * @return the unitUid
		 */
		public Integer getUnitUid()
		{
			return unitUid;
		}
		/**
		 * @param unitUid the unitUid to set
		 */
		public void setUnitUid(Integer unitUid)
		{
			this.unitUid = unitUid;
		}
		
	}
	
	/**
	 * 对文字进行解密
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDecrypt() throws Exception {
		// 解密
		String word = "lSvNdkandfwy1YYR8XHKxaVPUbJky9fM3i6D8hkCqnOR8lrlJKoTiu07TGd+u8DqA7DIfTpjykkL5pzghIbwBQ==";
		String decryptword = ConfigTools.decrypt(word);
		System.out.println(decryptword);
	}
	
}
