package com.myapp.module.authcode.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.myapp.utils.CodeUtil;
import com.myapp.utils.MessageUtil;
import com.myapp.utils.PhoneFormatCheckUtils;
import com.myapp.utils.PhoneMessage;
import com.myapp.utils.response.DataResponse;
import com.myapp.utils.response.LevelEnum;

/**
 * 验证码相关Controller
 * 
 * @className: AuthCodeController
 * @author sangyue
 * @date Jun 11, 2017 3:01:21 PM
 * @version V1.0
 */
public class AuthCodeController extends Controller {
	/**
	 * 发送验证码
	 * 
	 * @title: generateCodeAction
	 * @author sangyue
	 * @date Jun 11, 2017 3:02:05 PM
	 * @version V1.0
	 */
	public void sendCodeAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String identifier = getPara("identifier");// 验证账号
		if (StringUtils.isEmpty(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "手机号不可为空，请填写", actionKey));
			return;
		}
		// 校验手机号是否合法
		if (!PhoneFormatCheckUtils.isChinaPhoneLegal(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "手机号不合法，请修改", actionKey));
			return;
		}
		String code = CodeUtil.generateCode();
		boolean flag = MessageUtil.sendSupadataCode(code, identifier);
		if (!flag) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "发送验证码失败，请稍后重试", actionKey));
			return;
		}
		PhoneMessage pm = new PhoneMessage();
		pm.setAuthCode(code);
		pm.setSendTime(new Date());
		pm.setPhone(identifier);
		CacheKit.put("authCodeCache", identifier, pm);

		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "发送验证码成功", actionKey));
		return;
	}

}
