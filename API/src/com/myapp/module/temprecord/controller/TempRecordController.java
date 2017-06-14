package com.myapp.module.temprecord.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.myapp.bean.Equipment;
import com.myapp.bean.TempRecord;
import com.myapp.module.equipment.service.EquipmentService;
import com.myapp.module.temprecord.service.TempRecordService;
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
	/**
	 * 查询温度趋势图
	 * @title: findTempRecordChartAction
	 * @author sangyue
	 * @date Jun 13, 2017 9:44:27 PM 
	 * @version V1.0
	 */
	public void findTempRecordChartAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String equipid = getPara("equipid");// 设备id
		String date = StringUtils.isEmpty(getPara("date")) ? DateUtil.DateToString(new Date(), "yyyyMMdd"):getPara("date");// 日期(yyyyMMdd格式)，默认当前日期
		if (StringUtils.isEmpty(equipid)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "设备id不可为空，请填写", actionKey));
			return;
		}
		// 校验是否存在该设备
		Equipment equipment = EquipmentService.findEquipment(Integer.parseInt(equipid));
		if (equipment == null) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "该设备不存在，设备id【" + equipid + "】，请联系管理员", actionKey));
			return;
		}
		
		// 根据date查询TempRecord
		List<TempRecord> tempRecordList = TempRecordService.findTempRecordByDate(Integer.parseInt(equipid), date, date);
		// 根据date查询最大最小温度记录
		TempRecord maxminRecord = TempRecordService.findMaxMinTempRecordByDate(Integer.parseInt(equipid), date, date);
		// 定义返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("maxTemp", maxminRecord.getMaxTemp());
		resultMap.put("minTemp", maxminRecord.getMinTemp());
		resultMap.put("tempRecordList", tempRecordList);
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询成功", actionKey, resultMap));
		return;
	}

}
