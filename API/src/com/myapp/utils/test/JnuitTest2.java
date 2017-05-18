package com.myapp.utils.test;

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
}
