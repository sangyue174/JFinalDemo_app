
package com.myapp.module.equipment.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log4jLog;

/**
 * 类说明: 
 * @ClassName: EquipmentController
 * @author: pengxiuxiao
 * @date: 2017年6月7日 下午10:29:51
*/
public class EquipmentController extends Controller{
	private static Log4jLog log = Log4jLog.getLog(EquipmentController.class);
	
	/**
	 * 添加设备
	 */
	public void addEquipment() {
		String userid = getPara("userid");
		String kidid = getPara("kidid");
		String number = getPara("number");//设备编号
		String maxtime = getPara("maxtime");//最高温度
		String mintime = getPara("mintime");//最低温度
		String isalarm = getPara("isalarm");//是否报警(0:否1:是)
		
	}
	
	
	
	
}
