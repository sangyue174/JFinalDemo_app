
package com.myapp.module.equipment.controller;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.myapp.bean.Equipment;
import com.myapp.bean.Kid;
import com.myapp.bean.UserAuth;
import com.myapp.module.equipment.service.EquipmentService;
import com.myapp.module.kid.service.KidService;
import com.myapp.module.user.service.UserService;
import com.myapp.utils.response.DataResponse;
import com.myapp.utils.response.LevelEnum;

/**
 * 设备相关controller
 * 
 * @ClassName: EquipmentController
 * @author: pengxiuxiao
 * @date: 2017年6月7日 下午10:29:51
 */
public class EquipmentController extends Controller {
	/**
	 * 添加设备信息
	 */
	public void addEquipmentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取actionKey
		int kidid = getParaToInt("kidid");// 孩子id
		String number = getPara("number");// 设备编号
		String maxtime = StringUtils.isEmpty(getPara("maxtime")) ? "41" : getPara("maxtime");// 最高温度
		String mintime = StringUtils.isEmpty(getPara("mintime")) ? "37" : getPara("mintime");// 最低温度
		String ismaxalarm = getPara("ismaxalarm");// 是否高温报警(0:否1:是)
		String isminalarm = getPara("isminalarm");// 是否低温报警(0:否1:是)
		String isnotice = getPara("isnotice");// 是否接受提醒(0:否1:是)
		if (kidid == 0) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "孩子id不可为空", actionKey));
			return;
		}
		if (StringUtils.isEmpty(number)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备编号不可为空", actionKey));
			return;
		}
		// 根据tokenKey查询相关用户信息
		UserAuth userAuth = UserService.findUserAuthByTokenKey(tokenKey);
		if (userAuth == null) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "未查询到当前tokenKey[" + tokenKey + "]用户，请重试", actionKey));
			return;
		}
		int userid = userAuth.getUserid();
		
		// 查询当前用户下是否包含此孩子
		Kid kid = KidService.findKidByUserAndId(kidid, userid);
		if(kid == null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "未查询到当前tokenKey[" + tokenKey + "]相关的孩子信息，请重试", actionKey));
			return;
		}
		
		// 根据设备编号当前设备是否存在
		Equipment equipment = EquipmentService.findEquipmentByNumber(number);
		if(equipment != null){
			String isactive = equipment.getIsactive();// 是否有效(0:无效 1:有效)
			if("0".endsWith(isactive)){
				this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "001", "当前设备曾经被注销过，激活方可使用", null));
				return;
			}
			if(kidid == equipment.getKidid()){
				this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "002", "当前设备已经和孩子绑定，无需再次绑定，请刷新重试", null));
				return;
			}
			if(kidid != equipment.getKidid()){
				Kid kidTemp = KidService.findKidByUserAndId(equipment.getKidid(), userid);
				if(kidTemp != null){
					this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "003", "当前设备已经和其他孩子["+kidTemp.getNickname()+"]绑定，是否要绑定新的孩子？", null));
					return;
				} else {
					this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "004", "当前设备已经和其他孩子绑定，请联系管理员解决", null));
					return;
				}
			}
		}
		
		Equipment equipmentNew = new Equipment();
		equipmentNew.setUserid(userid);
		equipmentNew.setKidid(kidid);
		equipmentNew.setNumber(number);
		equipmentNew.setMaxtime(new BigDecimal(maxtime));
		equipmentNew.setMintime(new BigDecimal(mintime));
		equipmentNew.setIsmaxalarm(ismaxalarm);
		equipmentNew.setIsminalarm(isminalarm);
		equipmentNew.setIsnotice(isnotice);
		equipmentNew.setIsactive("1");
		//添加设备
		boolean flag = EquipmentService.saveEquipment(equipmentNew);
		if (!flag) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备添加失败，设备编号[" + number + "]", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "设备添加成功，设备编号[" + number + "]", actionKey));
		return;
	}
	
	/**
	 * 注销设备
	 */
	public void deleteEquipmentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String number = getPara("number");
		if (StringUtils.isEmpty(number)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备编号不可为空", actionKey));
			return;
		}
		// 取消该设备
		int result = EquipmentService.positiveEquipment(number);
		if (result == 1) {
			this.renderJson(new DataResponse(LevelEnum.SUCCESS, "设备注销成功", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.ERROR, "设备注销失败，请重试", actionKey));
		return;
	}
	
	/**
	 * 激活设备
	 * @title: activeEquipmentAction
	 * @author sangyue
	 * @date Jun 18, 2017 6:37:01 PM 
	 * @version V1.0
	 */
	public void activeEquipmentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String number = getPara("number");
		if (StringUtils.isEmpty(number)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备编号不可为空", actionKey));
			return;
		}
		//设备编号激活设备
		int result = EquipmentService.activeEquipment(number);
		if (result == 1) {
			this.renderJson(new DataResponse(LevelEnum.SUCCESS, "设备激活成功", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.ERROR, "设备激活失败，请重试", actionKey));
		return;
	}
	
	/**
	 * 修改设备信息
	 * @title: updateEquipmentAction
	 * @author sangyue
	 * @date Jun 18, 2017 5:43:48 PM 
	 * @version V1.0
	 */
	public void updateEquipmentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String number = getPara("number");// 设备编号
		String maxtime = StringUtils.isEmpty(getPara("maxtime")) ? "41" : getPara("maxtime");// 最高温度
		String mintime = StringUtils.isEmpty(getPara("mintime")) ? "37" : getPara("mintime");// 最低温度
		String ismaxalarm = getPara("ismaxalarm");// 是否高温报警(0:否1:是)
		String isminalarm = getPara("isminalarm");// 是否低温报警(0:否1:是)
		String isnotice = getPara("isnotice");// 是否接受提醒(0:否1:是)
		if (StringUtils.isEmpty(number)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备编号不可为空", actionKey));
			return;
		}
		// 根据设备编号查询设备信息
		Equipment equipdb = EquipmentService.findEquipmentByNumber(number);
		if(equipdb == null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "当前设备不存在，设备编号[" + number + "]，请刷新重试", actionKey));
			return;
		}
		int equipid = equipdb.getId();// 获取db中的设备id
		
		Equipment equipment = new Equipment();
		equipment.setId(equipid);
		equipment.setMaxtime(new BigDecimal(maxtime));
		equipment.setMintime(new BigDecimal(mintime));
		equipment.setIsmaxalarm(ismaxalarm);
		equipment.setIsminalarm(isminalarm);
		equipment.setIsnotice(isnotice);
		//添加设备
		boolean flag = EquipmentService.updateEquipment(equipment);
		if (!flag) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "修改设备信息失败，设备编号[" + number + "]", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "修改设备信息成功，设备编号[" + number + "]", actionKey));
		return;
	}
	
	/**
	 * 根据设备编号查找设备
	 */
	public void findEquipmentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String number = getPara("number");// 设备编号
		if (StringUtils.isEmpty(number)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备编号不可为空", actionKey));
			return;
		}
		//根据设备编号查找设备
		Equipment equipment = EquipmentService.findEquipmentByNumber(number);
		if(equipment == null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "未查询到该设备，设备编号[" + number + "]", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询设备信息成功，设备编号[" + number + "]", actionKey, equipment));
		return;
		
	}
}
