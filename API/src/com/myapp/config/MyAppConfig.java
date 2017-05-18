package com.myapp.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.myapp.bean._MappingKit;
import com.myapp.module.index.controller.IndexController;
import com.myapp.module.user.controller.UserController;
import com.myapp.utils.interceptor.ExceptionIntoLogInterceptor;

/**
 * 配置类
 * 
 * @author zhang
 *
 */
public class MyAppConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// 设置为开发模式
		me.setDevMode(true);
		// 设置编码为UTF-8
		me.setEncoding("utf-8");
		// 设置View类型为JSP
		me.setViewType(ViewType.JSP);
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/user", UserController.class);
		me.add("/", IndexController.class);
	}

	@Override
	public void configPlugin(Plugins plugins) {
		// Druid插件
		DruidPlugin druidPlugin = createDruidPlugin();
		plugins.add(druidPlugin);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 添加Model类和数据库表的映射,所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		plugins.add(arp);
		// ehcache缓存插件
		plugins.add(new EhCachePlugin());
	}

	// 创建Druid插件
	public static DruidPlugin createDruidPlugin() {
		// 配置JDBC连接
		PropKit.use("Config.properties");
		final String jdbcUrl = PropKit.get("jdbcUrl");
		final String username = PropKit.get("username");
		final String password = PropKit.get("password");
		final Integer initialSize = PropKit.getInt("initialSize");
		final Integer minIdle = PropKit.getInt("minIdle");
		final Integer maxActive = PropKit.getInt("maxActive");

		DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, username, password);
		druidPlugin.set(initialSize, minIdle, maxActive);
		druidPlugin.setFilters("stat,wall");
		return druidPlugin;
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// 全局拦截器，对所有请求拦截
		// 添加控制层全局拦截器
		me.addGlobalActionInterceptor(new ExceptionIntoLogInterceptor());
		// 添加业务层全局拦截器
//		me.addGlobalServiceInterceptor(exceptionInt);
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("basePath"));
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub

	}

}