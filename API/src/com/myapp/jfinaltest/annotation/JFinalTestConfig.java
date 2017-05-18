package com.myapp.jfinaltest.annotation;

import com.jfinal.config.JFinalConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liujunjie on 16/3/17.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JFinalTestConfig {
    Class<? extends JFinalConfig> value();
}
