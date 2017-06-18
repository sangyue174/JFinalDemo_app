package com.myapp.config;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
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
import com.myapp.module.authcode.controller.AuthCodeController;
import com.myapp.module.equipment.controller.EquipmentController;
import com.myapp.module.index.controller.IndexController;
import com.myapp.module.kid.controller.KidController;
import com.myapp.module.temprecord.controller.TempRecordController;
import com.myapp.module.tip.controller.TipController;
import com.myapp.module.user.controller.UserController;
import com.myapp.utils.interceptor.ExceptionIntoLogInterceptor;
import com.myapp.utils.interceptor.ValidateLoginStatusInterceptor;

/**
 * 配置类
 * 
 * @author zhang
 *
 */
public class MyAppConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		PropKit.use("Config.properties");
		// 设置为开发模式
		me.setDevMode(true);
		// 设置编码为UTF-8
		me.setEncoding("utf-8");
		// 设置View类型为JSP
		me.setViewType(ViewType.JSP);
		// 设置文件默认上传路径
//		me.setBaseUploadPath(PropKit.get("baseUploadPath"));
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/user", UserController.class);
		me.add("/auth", AuthCodeController.class);
		me.add("/equipment", EquipmentController.class);
		me.add("/tempRecord", TempRecordController.class);
		me.add("/tip", TipController.class);
		me.add("/kid", KidController.class);
		me.add("/", IndexController.class);
	}

	@Override
	public void configPlugin(Plugins plugins) {
		// Druid插件
		DruidPlugin druidPlugin = createDruidPlugin();
		plugins.add(druidPlugin);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 打印sql语句
		arp.setShowSql(true);
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
		
		/**增强配置**/
		druidPlugin.setValidationQuery("select 'x'");
		druidPlugin.setTestOnBorrow(false);
		druidPlugin.setTestWhileIdle(false);
		druidPlugin.setTestOnReturn(false);

		WallFilter wallFilter = new WallFilter();
		wallFilter.setDbType("mysql");
		druidPlugin.addFilter(wallFilter);

		Log4jFilter log4jFilter = new Log4jFilter();
		log4jFilter.setDataSourceLogEnabled(false);
		log4jFilter.setConnectionLogEnabled(false);
		log4jFilter.setStatementLogEnabled(false);
		log4jFilter.setStatementExecutableSqlLogEnable(true);
		log4jFilter.setResultSetLogEnabled(false);
		druidPlugin.addFilter(log4jFilter);

		StatFilter statFilter = new StatFilter();
		statFilter.setLogSlowSql(true);
		statFilter.setDbType("mysql");
		statFilter.setSlowSqlMillis(3000);
		druidPlugin.addFilter(statFilter);
		
		return druidPlugin;
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// 全局拦截器，对所有请求拦截
		// 添加控制层全局拦截器
		me.addGlobalActionInterceptor(new ExceptionIntoLogInterceptor());
		me.addGlobalActionInterceptor(new ValidateLoginStatusInterceptor());
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