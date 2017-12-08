
package com.myapp.module.equipment.controller;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
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
	 * 重新绑定设备和孩子信息
	 * @title: rebindEquipmentWithKidAction
	 * @author sangyue
	 * @date Nov 29, 2017 6:19:40 PM 
	 * @version V1.0
	 */
	@Before(Tx.class)
	public void rebindEquipmentWithKidAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取actionKey
		int kidid = getParaToInt("kidid");// 孩子id
		String equipnum = getPara("equipnum");// 设备编号
		if (kidid == 0) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "孩子id不可为空", actionKey));
			return;
		}
		if (StringUtils.isEmpty(equipnum)) {
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
		
		int equipid = 0;
		
		// 获取数据库中保存的equipid和equipnum
		int equipiddb = kid.getEquipid() == null ? 0 : kid.getEquipid();
		String equipnumdb = kid.getEquipnum() == null ? "" : kid.getEquipnum();
		// 数据库中孩子设备信息和参数一致
		if(equipnum.equals(equipnumdb) && equipiddb != 0){
			this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "002", "当前设备已经和孩子绑定，无需再次绑定，请刷新重试", null));
			return;
		}
		Kid kidTemp = KidService.findKidByEquipnum(equipnum);
		if(kidTemp != null){
			int useridTemp = kidTemp.getUserid();
			if(userid != useridTemp){
				this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "004", "当前设备已经和其他用户孩子绑定，无法继续绑定", null));
				return;
			}
			// 清空同用户下原先孩子绑定的设备信息，add by sangyue at 2017-12-8 14:50:02
			kidTemp.setEquipid(null);
			kidTemp.setEquipnum(null);
			boolean flag = KidService.updateKid(kidTemp);
			if (!flag) {
				this.renderJson(new DataResponse(LevelEnum.ERROR, "清空同用户下原先孩子绑定的设备信息失败，设备编号[" + equipnum + "]，请联系管理员", actionKey));
				return;
			}
		}
		
		// 根据设备编号查询设备信息
		Equipment equipment = EquipmentService.findEquipmentByEquipnum(equipnum);
		
		if(equipment != null){
			String isactive = equipment.getIsactive();// 是否有效(0:无效 1:有效)
			if("0".endsWith(isactive)){
				this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "001", "当前设备曾经被注销过，请联系管理员解决", null));
				return;
			}
			equipid = equipment.getId();
		}else{
			// 保存设备信息
			Equipment equipmentNew = new Equipment();
			equipmentNew.setEquipnum(equipnum);
			equipmentNew.setIsactive("1");
			//添加设备
			boolean flag = EquipmentService.saveEquipment(equipmentNew);
			if (!flag) {
				this.renderJson(new DataResponse(LevelEnum.ERROR, "设备添加失败，设备编号[" + equipnum + "]", actionKey));
				return;
			}
			equipid = equipmentNew.getId();
		}
		
		kid.setEquipid(equipid);
		kid.setEquipnum(equipnum);
		boolean flag = KidService.updateKid(kid);
		if (!flag) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "重绑设备信息失败，设备编号[" + equipnum + "]", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "重绑设备信息成功，设备编号[" + equipnum + "]", actionKey));
		return;
	}
	
	/**
	 * 注销设备
	 * @title: deleteEquipmentAction
	 * @author sangyue
	 * @date Dec 4, 2017 3:53:38 PM 
	 * @version V1.0
	 */
	public void deleteEquipmentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取actionKey
		int kidid = getParaToInt("kidid");// 孩子id
		if (kidid == 0) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "孩子id不可为空", actionKey));
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
		kid.setEquipid(null);
		kid.setEquipnum(null);
		
		// 取消该设备
		boolean result = KidService.updateKid(kid);
		if (!result) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备注销失败，请重试", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "设备注销成功", actionKey));
		return;
	}
	
	/**
	 * 激活设备
	 * @title: activeEquipmentAction
	 * @author sangyue
	 * @date Jun 18, 2017 6:37:01 PM 
	 * @version V1.0
	 */
/*	public void activeEquipmentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String equipnum = getPara("equipnum");
		if (StringUtils.isEmpty(equipnum)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备编号不可为空", actionKey));
			return;
		}
		//设备编号激活设备
		int result = EquipmentService.activeEquipment(equipnum);
		if (result == 1) {
			this.renderJson(new DataResponse(LevelEnum.SUCCESS, "设备激活成功", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.ERROR, "设备激活失败，请重试", actionKey));
		return;
	}*/
	
	/**
	 * 修改设备信息
	 * @title: updateEquipmentAction
	 * @author sangyue
	 * @date Jun 18, 2017 5:43:48 PM 
	 * @version V1.0
	 */
	/*public void updateEquipmentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String equipnum = getPara("equipnum");// 设备编号
		String maxtime = StringUtils.isEmpty(getPara("maxtime")) ? "41" : getPara("maxtime");// 最高温度
		String mintime = StringUtils.isEmpty(getPara("mintime")) ? "37" : getPara("mintime");// 最低温度
		String ismaxalarm = getPara("ismaxalarm");// 是否高温报警(0:否1:是)
		String isminalarm = getPara("isminalarm");// 是否低温报警(0:否1:是)
		String isnotice = getPara("isnotice");// 是否接受提醒(0:否1:是)
		if (StringUtils.isEmpty(equipnum)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备编号不可为空", actionKey));
			return;
		}
		// 根据设备编号查询设备信息
		Equipment equipdb = EquipmentService.findEquipmentByNumber(equipnum);
		if(equipdb == null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "当前设备不存在，设备编号[" + equipnum + "]，请刷新重试", actionKey));
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
			this.renderJson(new DataResponse(LevelEnum.ERROR, "修改设备信息失败，设备编号[" + equipnum + "]", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "修改设备信息成功，设备编号[" + equipnum + "]", actionKey));
		return;
	}*/
	
	/**
	 * 根据设备编号查找设备
	 */
	/*public void findEquipmentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String equipnum = getPara("equipnum");// 设备编号
		if (StringUtils.isEmpty(equipnum)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备编号不可为空", actionKey));
			return;
		}
		//根据设备编号查找设备
		Equipment equipment = EquipmentService.findEquipmentByEquipnum(equipnum);
		if(equipment == null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "未查询到该设备，设备编号[" + equipnum + "]", actionKey));
			return;
		}
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询设备信息成功，设备编号[" + equipnum + "]", actionKey, equipment));
		return;
		
	}*/
}
