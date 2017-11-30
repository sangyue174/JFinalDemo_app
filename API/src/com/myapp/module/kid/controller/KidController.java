package com.myapp.module.kid.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;
import com.myapp.bean.Equipment;
import com.myapp.bean.Kid;
import com.myapp.bean.UserAuth;
import com.myapp.module.equipment.service.EquipmentService;
import com.myapp.module.kid.service.KidService;
import com.myapp.module.user.service.UserService;
import com.myapp.utils.interceptor.ValidateLoginStatusInterceptor;
import com.myapp.utils.response.DataResponse;
import com.myapp.utils.response.LevelEnum;

/**
 * 孩子Controller
 * 
 * @className: KidController
 * @author sangyue
 * @date Jun 17, 2017 4:33:04 PM
 * @version V1.0
 */
public class KidController extends Controller {
	/**
	 * 新增孩子信息
	 * 
	 * @title: addKidAction
	 * @author sangyue
	 * @date Jun 17, 2017 4:44:39 PM
	 * @version V1.0
	 * @version V1.1 2017-11-29 modify by sangyue, according the db modify the codes(rebuild the db structure)
	 */
	public void addKidAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取tokenKey查询用户信息
		String nickname = getPara("nickname");// 昵称
		String sex = StringUtils.isEmpty(getPara("sex")) ? "1" : getPara("sex");// 性别(0:女1:男)
		Date birthday = getParaToDate("birthday", new Date());// 生日
		String headurl = getPara("headurl");// 头像url
		String healthIssue = StringUtils.isEmpty(getPara("healthIssue")) ? "0" : getPara("healthIssue");// 健康问题(0:没有1:有)
		// 2017-11-29 add by sangyue
		String maxtime = StringUtils.isEmpty(getPara("maxtime")) ? "37" : getPara("maxtime");// 最高温度
		String mintime = StringUtils.isEmpty(getPara("mintime")) ? "36" : getPara("mintime");// 最低温度
		String ismaxalarm = StringUtils.isEmpty(getPara("ismaxalarm")) ? "1" : getPara("ismaxalarm");// 是否高温报警(0:否1:是)
		String isminalarm = StringUtils.isEmpty(getPara("isminalarm")) ? "1" : getPara("isminalarm");// 是否低温报警(0:否1:是)
		String isnotice = StringUtils.isEmpty(getPara("isnotice")) ? "1" : getPara("isnotice");// 是否接受提醒(0:否1:是)
//		String equipnum = getPara("equipnum");// 设备编号
		
		if(StringUtils.isEmpty(nickname)){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "昵称不可为空，请填写", actionKey));
			return;
		}
		// 根据tokenKey查询相关用户信息
		UserAuth userAuth = UserService.findUserAuthByTokenKey(tokenKey);
		if (userAuth == null) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "未查询到当前tokenKey[" + tokenKey + "]用户，请重试", actionKey));
			return;
		}
		int userid = userAuth.getUserid();
		
		// 查找当前用户下的孩子数量
		List<Kid> kidList = KidService.findKidListByUserid(userid);
		int number = 1;
		if(kidList != null){
			number = kidList.size() + 1;
		}
		
//		if(StringUtils.isNotEmpty(equipnum)){
//			// 根据设备编号当前设备是否存在
//			Equipment equipment = EquipmentService.findEquipmentByNumber(equipnum);
//			if(equipment != null){
//				String isactive = equipment.getIsactive();// 是否有效(0:无效 1:有效)
//				if("0".endsWith(isactive)){
//					this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "001", "当前设备曾经被注销过，激活方可使用", null));
//					return;
//				}
//				if(StringUtils.isNotEmpty(equipment.getKidid())){
//					Kid kidTemp = KidService.findKidByUserAndId(equipment.getKidid(), userid);
//					if(kidTemp != null){
//						this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "003", "当前设备已经和其他孩子["+kidTemp.getNickname()+"]绑定，是否要绑定新的孩子？", null));
//						return;
//					} else {
//						this.renderJson(new DataResponse(actionKey, LevelEnum.ERROR, "004", "当前设备已经和其他孩子绑定，请联系管理员解决", null));
//						return;
//					}
//				}
//			}
//		}
		
		// 保存孩子信息
		Kid kid = new Kid();
		kid.setUserid(userid);
		kid.setNickname(nickname);
		kid.setSex(sex);
		kid.setBirthday(birthday);
		kid.setHeadurl(headurl);
		kid.setHealthIssue(healthIssue);
		kid.setNumber(number);
		kid.setMaxtime(new BigDecimal(maxtime));
		kid.setMintime(new BigDecimal(mintime));
		kid.setIsmaxalarm(ismaxalarm);
		kid.setIsminalarm(isminalarm);
		kid.setIsnotice(isnotice);
//		kid.setEquipnum(equipnum);
		KidService.saveKid(kid);

		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "保存孩子信息成功", actionKey, kid.getId()));
		return;
	}

	/**
	 * 上传孩子头像
	 * @title: uploadKidImageAction
	 * @author sangyue
	 * @date Jun 18, 2017 4:21:44 PM 
	 * @version V1.0
	 */
	@Clear(ValidateLoginStatusInterceptor.class)
	public void uploadKidImageAction() {
		UploadFile imageFile = getFile();
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		if(imageFile == null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "上传孩子头像失败，请重试", actionKey));
			return;
		}
		// 重命名文件名
		try {
			String filePath = imageFile.getUploadPath() + "/" + imageFile.getFileName();
			String imageNewName = FilenameUtils.getBaseName(filePath) + System.currentTimeMillis() + "." + FilenameUtils.getExtension(filePath);
			imageFile.getFile().renameTo(new File(imageFile.getUploadPath() + "/" + imageNewName));
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("imageNewName", imageNewName);
			resultMap.put("imagePath", PropKit.get("imagePath"));
			this.renderJson(new DataResponse(LevelEnum.SUCCESS, "上传孩子头像成功", actionKey, resultMap));
			return;
		} catch (Exception e) {
			e.printStackTrace();
			this.renderJson(new DataResponse(LevelEnum.ERROR, "上传孩子头像失败，请重试", actionKey));
			throw e;
		}
	}

	/**
	 * 查询孩子信息（增加温度报警和设备信息相关）
	 * 
	 * @title: findKidAction
	 * @author sangyue
	 * @date Jun 17, 2017 11:33:41 AM
	 * @version V1.0
	 */
	public void findKidAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		int kidid = getParaToInt("kidid"); // 孩子id

		if (kidid == 0) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "孩子id不可为空，请填写", actionKey));
			return;
		}
		// 根据kidid查找孩子信息
		Kid kid = KidService.findKidById(kidid);

		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询孩子信息成功", actionKey, kid));
		return;
	}
	
	/**
	 * 修改孩子信息
	 * @title: updateKidAction
	 * @author sangyue
	 * @date Jun 18, 2017 4:36:50 PM 
	 * @version V1.0
	 * @version V1.1 2017-11-29 modify by sangyue, according the db modify the codes(rebuild the db structure)
	 */
	public void updateKidAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取tokenKey查询用户信息
		int kidid = getParaToInt("kidid"); // 孩子id
		String nickname = getPara("nickname");// 昵称
		String sex = StringUtils.isEmpty(getPara("sex")) ? "1" : getPara("sex");// 性别(0:女1:男)
		Date birthday = getParaToDate("birthday", new Date());// 生日
		String headurl = getPara("headurl");// 头像url
		String healthIssue = StringUtils.isEmpty(getPara("healthIssue")) ? "0" : getPara("healthIssue");// 健康问题(0:没有1:有)
		// 2017-11-29 add by sangyue
		String maxtime = StringUtils.isEmpty(getPara("maxtime")) ? "37" : getPara("maxtime");// 最高温度
		String mintime = StringUtils.isEmpty(getPara("mintime")) ? "36" : getPara("mintime");// 最低温度
		String ismaxalarm = StringUtils.isEmpty(getPara("ismaxalarm")) ? "1" : getPara("ismaxalarm");// 是否高温报警(0:否1:是)
		String isminalarm = StringUtils.isEmpty(getPara("isminalarm")) ? "1" : getPara("isminalarm");// 是否低温报警(0:否1:是)
		String isnotice = StringUtils.isEmpty(getPara("isnotice")) ? "1" : getPara("isnotice");// 是否接受提醒(0:否1:是)
		String equipnum = getPara("equipnum");// 设备编号

		if (kidid == 0) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "孩子id不可为空，请填写", actionKey));
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
		
		// 保存孩子信息 modify by sangyue for db structure modified
		if(StringUtils.isNotEmpty(nickname)){
			kid.setNickname(nickname);
		}
		if(StringUtils.isNotEmpty(sex)){
			kid.setSex(sex);
		}
		if(birthday != null){
			kid.setBirthday(birthday);
		}
		if(StringUtils.isNotEmpty(headurl)){
			kid.setHeadurl(headurl);
		}
		if(StringUtils.isNotEmpty(healthIssue)){
			kid.setHealthIssue(healthIssue);
		}
		
		kid.setMaxtime(new BigDecimal(maxtime));
		kid.setMintime(new BigDecimal(mintime));
		kid.setIsmaxalarm(ismaxalarm);
		kid.setIsminalarm(isminalarm);
		kid.setIsnotice(isnotice);
		KidService.updateKid(kid);

		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "修改孩子信息成功", actionKey));
		return;
	}
	
	/**
	 * 查询孩子列表
	 * @title: findKidListAction
	 * @author sangyue
	 * @date Jun 20, 2017 9:45:03 PM 
	 * @version V1.0
	 */
	public void findKidListAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取tokenKey查询用户信息
		
		// 根据tokenKey查询相关用户信息
		UserAuth userAuth = UserService.findUserAuthByTokenKey(tokenKey);
		if (userAuth == null) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "未查询到当前tokenKey[" + tokenKey + "]用户，请重试", actionKey));
			return;
		}
		int userid = userAuth.getUserid();
		// 根据userid查找孩子信息
		List<Kid> kidList = KidService.findKidListByUserid(userid);

		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询孩子列表", actionKey, kidList));
		return;
	}

}
