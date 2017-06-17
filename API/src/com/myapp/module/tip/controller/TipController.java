package com.myapp.module.tip.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.myapp.bean.Tip;
import com.myapp.module.tip.service.TipService;
import com.myapp.utils.response.DataResponse;
import com.myapp.utils.response.LevelEnum;

/**
 * 贴士Controller
 * 
 * @className: TipController
 * @author sangyue
 * @date Jun 17, 2017 12:40:26 AM
 * @version V1.0
 */
public class TipController extends Controller {
	/**
	 * 查询贴士列表
	 * 
	 * @title: findTipListAction
	 * @author sangyue
	 * @date Jun 17, 2017 12:40:59 AM
	 * @version V1.0
	 */
	public void findTipListAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tipType = getPara("tipType") == null ? "fever" : getPara(
				"tipType").toLowerCase();// 贴士类型fever,food,cool
		// 根据tipType查找贴士列表
		List<Tip> tipList = TipService.findTipByTipType(tipType);

		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询贴士列表成功", actionKey, tipList));
		return;
	}
	
	/**
	 * 查询贴士内容
	 * 
	 * @title: findTipContentAction
	 * @author sangyue
	 * @date Jun 17, 2017 11:33:41 AM
	 * @version V1.0
	 */
	public void findTipContentAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tipid = getPara("tipid"); // 贴士id
		
		if (StringUtils.isEmpty(tipid)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "贴士id不可为空，请填写", actionKey));
			return;
		}
		// 根据tipid查找贴士内容
		Tip tip = TipService.findTipContentById(tipid);
		
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询贴士内容成功", actionKey, tip));
		return;
	}

}
