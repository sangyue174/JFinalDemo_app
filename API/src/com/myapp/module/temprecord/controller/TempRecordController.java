package com.myapp.module.temprecord.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.core.Controller;
import com.jfinal.log.Log4jLog;
import com.jfinal.plugin.activerecord.Db;
import com.myapp.bean.Equipment;
import com.myapp.bean.Kid;
import com.myapp.bean.TempRecord;
import com.myapp.bean.UserAuth;
import com.myapp.module.equipment.service.EquipmentService;
import com.myapp.module.kid.service.KidService;
import com.myapp.module.temprecord.service.TempRecordService;
import com.myapp.module.user.service.UserService;
import com.myapp.utils.DateStyle;
import com.myapp.utils.DateUtil;
import com.myapp.utils.response.DataResponse;
import com.myapp.utils.response.LevelEnum;

/**
 * 温度记录Controller
 * @className: TempRecordController
 * @author sangyue
 * @date Jun 13, 2017 9:35:46 PM
 * @version V1.0
 */
public class TempRecordController extends Controller {
	private static final Log4jLog LOG = Log4jLog.getLog(TempRecordController.class);
	private final String TIME = "time";
	private final String TEMP = "temp";
	/**
	 * 查询当前孩子温度趋势图
	 * @title: findTempRecordChartAction
	 * @author sangyue
	 * @date Jun 13, 2017 9:44:27 PM 
	 * @version V1.0
	 */
	public void findTempRecordChartAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取actionKey
		int kidid = getParaToInt("kidid");// 孩子id
		String date = StringUtils.isEmpty(getPara("date")) ? DateUtil.DateToString(new Date(), "yyyyMMdd"):getPara("date");// 日期(yyyyMMdd格式)，默认当前日期
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
		
		// 根据date查询TempRecord
		List<TempRecord> tempRecordList = TempRecordService.findTempRecordByDateAndKid(kidid, date, date);
		// 根据date查询最大最小温度记录
		TempRecord maxminRecord = TempRecordService.findMaxMinTempRecordByDateAndKid(kidid, date, date);
		// 定义返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("maxTemp", maxminRecord.getMaxTemp());
		resultMap.put("minTemp", maxminRecord.getMinTemp());
		resultMap.put("tempRecordList", tempRecordList);
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询成功", actionKey, resultMap));
		return;
	}
	
	/**
	 * 查询当前孩子涉及的所有日期
	 * @title: findTempRecordDateAction
	 * @author sangyue
	 * @date Jun 15, 2017 12:39:24 AM 
	 * @version V1.0
	 */
	public void findTempRecordDateAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取actionKey
		int kidid = getParaToInt("kidid");// 孩子id
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
		
		// 根据kidid查询设备涉及的所有日期
		List<TempRecord> tempRecordList = TempRecordService.findTempRecordDateByKidGroupbyRecordTime(kidid);
		// 循环将tempRecord取出并存在list中返回给app
		List<String> recordList = new ArrayList<String>();
		for (TempRecord tempRecord : tempRecordList) {
			recordList.add(DateUtil.DateToString(tempRecord.getRecordTime(), "yyyyMMdd"));
		}
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询成功", actionKey, recordList));
		return;
	}
	
	/**
	 * 新增当前设备温度记录
	 * @title: addTempRecordsAction
	 * @author sangyue
	 * @date Nov 16, 2017 6:11:54 PM 
	 * @version V1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addTempRecordsAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取actionKey
		String equipnum = getPara("equipnum");// 设备编号
		int kidid = getParaToInt("kidid");// 孩子id
		String records = getPara("records");// 温度记录[{"time":"yyyy-MM-dd HH:mm:ss","temp":"36.6"},{"time":"yyyy-MM-dd HH:mm:ss","temp":"37.6"}]
		if (StringUtils.isEmpty(equipnum)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备编号不可为空，请填写", actionKey));
			return;
		}
		if (kidid == 0) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "孩子id不可为空，请填写", actionKey));
			return;
		}
		if (StringUtils.isEmpty(records)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "温度记录不可为空，请填写", actionKey));
			return;
		}
		// 校验是否存在该设备
		Equipment equipment = EquipmentService.findEquipmentByEquipnum(equipnum);
		if (equipment == null) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "该设备不存在，设备编号[" + equipnum + "]，请联系管理员", actionKey));
			return;
		}
		String isactive = equipment.getIsactive();// 是否有效
		int equipid = equipment.getId();// 获取设备id
		if("0".equals(isactive)){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "当前设备未激活，设备编号[" + equipnum + "]，请联系管理员", actionKey));
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
		
		List<Map> recordsList = JSONArray.parseArray(records, Map.class);
		if(recordsList.size() == 0){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "温度记录不可为空，请填写", actionKey));
			return;
		}
		List<TempRecord> modelList = new ArrayList<TempRecord>();
		for(Map<String, String> record : recordsList){
			TempRecord model = new TempRecord();
			model.setKidid(kidid);
			model.setEquipid(equipid);
			model.setEquipnum(equipnum);
			model.setRecordTime(DateUtil.StringToDate(record.get(TIME), DateStyle.YYYY_MM_DD_HH_MM_SS));
			model.setTemperature(new BigDecimal(record.get(TEMP)));
			modelList.add(model);
		}
		int[] result = Db.batchSave(modelList, 300);
		if (result.length == recordsList.size()) {
			LOG.warn("数据未全部保存，请联系管理员, actionKey is " + actionKey
					+ ", tokenKey is " + tokenKey + ", equipnum is " + equipnum
					+ ", kidid is " + kidid + ", records is " + records);
		}
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "新增温度记录成功", actionKey));
		return;
	}

}
