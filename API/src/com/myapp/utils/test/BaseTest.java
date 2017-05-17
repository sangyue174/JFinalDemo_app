package com.myapp.utils.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.myapp.bean._MappingKit;
import com.myapp.config.MyAppConfig;

public class BaseTest {
	protected static ActiveRecordPlugin arp = null;
	protected static DruidPlugin druidPlugin = null;

	@BeforeClass
	public static void initAll() {
		System.out.print("Begin testing init...");
		if (druidPlugin == null) {
			druidPlugin = MyAppConfig.createDruidPlugin();
			druidPlugin.start();
		}

		if (arp == null) {
			arp = new ActiveRecordPlugin(druidPlugin);
			// 打印sql语句
			arp.setShowSql(true);
			// 添加Model类和数据库表的映射,所有映射在 MappingKit 中自动化搞定
			_MappingKit.mapping(arp);
			arp.start();
		}
	}

	@AfterClass
	public static void tearDownAll() {
		System.out.print("End testing...");
		arp.stop();
		druidPlugin.stop();
	}
}
