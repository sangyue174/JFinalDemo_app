package com.myapp.utils.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.jfinal.ext.test.ControllerTestCase;
import com.myapp.config.MyAppConfig;
public class JnuitTest2 extends ControllerTestCase<MyAppConfig> {
    @Test
    public void test3() {
        String url = "/user/registerAction?identityType=phone&identifier=testname&credential=testpassword";
//        String body = "<root>中文</root>";
//        use(url).post(body).invoke(); 
        use(url).invoke(); 
    }
    
    public static <T> T getMiddle(T[] t){
        System.out.println("泛型方法");
        return t[t.length/2];
    }
    public static <T> T getMiddle2(T t){
    	System.out.println("泛型方法");
    	return null;
    }
    
    public static void main(String[] args) {
    	 String[] s = {"AAA","BBB","CCC"};
         System.out.println(JnuitTest2.<String>getMiddle(s));//在方法名前指定类型
//       System.out.println(<String>getMiddle(s));//不能这样用，虽然调用的是处在同一个类中静态方法,语法问题，<>不能加在方法名前
         Date[] d = {new Date(),new Date(),new Date()};
         System.out.println(getMiddle(d));//其实可以不指定参数，编译器有足够的信息推断出要调用的方法
         
//         int[] is = {100,200,300};
//         System.out.println(getMiddle(is));// 这个是错误的，泛型T不支持基本数据类型int char，要用他们的包装类
         Integer[] is = new Integer[]{100,200,300};
         System.out.println(getMiddle(is));// 这个是正确的，使用了包装类
         
         List<T> list=new ArrayList<T>();
         list.add(4.0);//编译错误
         list.add(3.0);//编译错误
	}
    
	static <T> void show(List<T> list) {
		for (Object object : list) {
			System.out.println(object);
		}
	}
}
