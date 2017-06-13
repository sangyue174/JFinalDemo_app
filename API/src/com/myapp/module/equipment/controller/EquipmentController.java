
package com.myapp.module.equipment.controller;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.log.Log4jLog;
import com.myapp.bean.Equipment;
import com.myapp.module.equipment.service.EquipmentService;
import com.myapp.utils.response.DataResponse;
import com.myapp.utils.response.LevelEnum;

/**
 * 类说明:
 * 
 * @ClassName: EquipmentController
 * @author: pengxiuxiao
 * @date: 2017年6月7日 下午10:29:51
 */
public class EquipmentController extends Controller {
	private static Log4jLog log = Log4jLog.getLog(EquipmentController.class);

	/**
	 * 添加设备
	 */
	public void addEquipment() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String userid = getPara("userid");
		if (StringUtils.isEmpty(userid)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "userid为空！", actionKey));
			return;
		}
		String kidid = getPara("kidid");
		if (StringUtils.isEmpty(kidid)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "kidid为空！", actionKey));
			return;
		}
		String number = getPara("number");// 设备编号
		if (StringUtils.isEmpty(number)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "number为空！", actionKey));
			return;
		}
		String maxtime = getPara("maxtime");// 最高温度
		if (StringUtils.isEmpty(maxtime)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "maxtime为空！", actionKey));
			return;
		}
		String mintime = getPara("mintime");// 最低温度
		if (StringUtils.isEmpty(mintime)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "mintime为空！", actionKey));
			return;
		}
		String isalarm = getPara("isalarm");// 是否报警(0:否1:是)
		if (StringUtils.isEmpty(isalarm)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "isalarm为空！", actionKey));
			return;
		}
		Equipment equipment = new Equipment();
		equipment.setUserid(Integer.parseInt(userid));
		equipment.setKidid(Integer.parseInt(kidid));
		equipment.setNumber(number);
		equipment.setMaxtime(new BigDecimal(maxtime));
		equipment.setMintime(new BigDecimal(mintime));
		equipment.setIsnotice(isalarm);
		//添加设备
		boolean suc = EquipmentService.saveEquipment(equipment);
		if (suc) {
			this.renderJson(new DataResponse(LevelEnum.SUCCESS, "设备添加成功.", actionKey));
		} else {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备添加失败.", actionKey));
		}
		return;
	}
	
	/**
	 * 删除设备
	 */
	public void deleteEquipment() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String number = getPara("number");
		if (StringUtils.isEmpty(number)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "number为空！", actionKey));
			return;
		}
		//根据设备编号删除设备
		int result = EquipmentService.deleteEquipment(number);
		if (result == 1) {
			this.renderJson(new DataResponse(LevelEnum.SUCCESS, "设备删除成功.", actionKey));
		} else {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备删除失败.", actionKey));
		}
		return;
		
	}
	
	/**
	 * 根据id查找设备
	 */
	public void findEquipment() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String equipmentId = getPara("equipmentId");
		if (StringUtils.isEmpty(equipmentId)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "equipmentId为空！", actionKey));
			return;
		}
		//根据设备编号删除设备
		Equipment equipment = EquipmentService.findEquipment(Integer.parseInt(equipmentId));
		if (equipment != null) {
			this.renderJson(equipment);
		} else {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "查找设备失败.", actionKey));
		}
		return;
		
	}
}
