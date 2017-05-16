package com.myapp.controller;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.myapp.module.User;
import com.myapp.service.LoginService;
import com.myapp.utils.response.DataResponse;
import com.myapp.utils.response.LevelEnum;

/**
 * 登录动作
 * http://localhost:8080/API/login/LoginAction?username=zhangsan&password=123
 * 
 * @author zhang
 *
 */
public class UserController extends Controller {
	/**
	 * 登录动作
	 */
	public void loginAction() {
		String user = this.getPara("username");
		String pwd = this.getPara("password");
		User userModule = LoginService.getUserInfo(user, pwd);
		JSONObject object = new JSONObject();// 外层json
		JSONObject infos = new JSONObject();// 成功以后的用户信息
		JSONArray data = new JSONArray();// 承载用户信息的array
		if (userModule == null) {// 用户名或密码错误
			object.put("errorCode", 0);
			object.put("msg", "用户名或密码错误");
			object.put("data", data);
			this.renderJson(object);
		} else if (userModule != null
				&& !userModule.get("password").equals(pwd)) {// 密码错误，请核对
			object.put("errorCode", 0);
			object.put("msg", "密码错误，请核对");
			object.put("data", data);
			this.renderJson(object);
		} else {// 登录成功,返回成功登录信息
			object.put("errorCode", 1);
			object.put("msg", "登录成功");
			// 用户信息
			infos.put("username", userModule.get("username"));
			infos.put("nickname", userModule.get("nickname"));
			infos.put("sex", userModule.getInt("sex"));
			infos.put("usertype", userModule.getInt("usertype"));
			infos.put("nickname", userModule.get("nickname"));
			infos.put("mobile", userModule.get("mobile"));
			infos.put("score", userModule.getInt("score"));
			infos.put("token", LoginService.insertTOKEN(user));
			// 添加值data数组中
			data.add(infos);
			object.put("data", data);
			this.renderJson(object);
		}
	}

	public void registerAction() {
		String identityType = getPara("identityType") == null ? "phone"
				: getPara("identityType").toLowerCase();// 验证类型:phone,qq,weixin
		String identifier = getPara("identifier");// 验证账号
		String credential = getPara("credential");// 验证凭证
		String code = getPara("code");// 验证码
		if (StringUtils.isEmpty(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "账号不可为空，请填写"));
			return;
		}
		if(StringUtils.isEmpty(credential)){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "密码不可为空，请填写"));
			return;
		}
		if
		
	}
	
	public void registerAction() {
		String identityType = getPara("identityType") == null ? "phone"
				: getPara("identityType").toLowerCase();// 验证类型:phone,qq,weixin
		String identifier = getPara("identifier");// 验证账号
		String credential = getPara("credential");// 验证凭证
		String code = getPara("code");// 验证码
		if (StringUtils.isEmpty(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "账号不可为空，请填写"));
			return;
		}
		if(StringUtils.isEmpty(credential)){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "密码不可为空，请填写"));
			return;
		}
		if
		
	}
}
