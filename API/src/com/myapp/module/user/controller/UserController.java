package com.myapp.module.user.controller;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.log.Log4jLog;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.ehcache.CacheKit;
import com.myapp.bean.User;
import com.myapp.bean.UserAuth;
import com.myapp.module.user.service.UserService;
import com.myapp.utils.IdentityTypeEnum;
import com.myapp.utils.PasswordUtil;
import com.myapp.utils.PhoneMessage;
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
	private static Log4jLog log = Log4jLog.getLog(UserController.class);
	/**
	 * 登录动作
	 */
	public void loginAction() {
		String user = this.getPara("username");
		String pwd = this.getPara("password");
		User userModule = UserService.getUserInfo(user, pwd);
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
			infos.put("token", UserService.insertTOKEN(user));
			// 添加值data数组中
			data.add(infos);
			object.put("data", data);
			this.renderJson(object);
		}
	}

	/**
	 * 用户注册
	 * @title: registerAction
	 * @author sangyue
	 * @date May 21, 2017 4:00:58 PM 
	 * @version V1.0
	 */
	public void registerAction() {
		final String identityType = getPara("identityType") == null ? "phone"
				: getPara("identityType").toLowerCase();// 验证类型:phone,qq,weixin
		final String identifier = getPara("identifier");// 验证账号
		final String credential = getPara("credential");// 验证凭证
		final String authCode = getPara("authCode");// 验证码
		if (StringUtils.isEmpty(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "账号不可为空，请填写"));
			return;
		}
		if (StringUtils.isEmpty(credential)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "密码不可为空，请填写"));
			return;
		}
//		if (IdentityTypeEnum.PHONE.getValue().equals(identityType)) {
//			// 获取session中的验证码信息
//			PhoneMessage pm = (PhoneMessage)getSessionAttr(identifier);
//			if(pm == null || pm.getAuthCode() == null){
//				this.renderJson(new DataResponse(LevelEnum.ERROR, "请先获取验证码"));
//				return;
//			}
//			if(StringUtils.isEmpty(authCode)){
//				this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码不可为空，请填写"));
//				return;
//			}
//			String authCodeDB = pm.getAuthCode();
//			long sendTime = pm.getSendTime().getTime();
//			if(new Date().getTime() - sendTime > 1000 * 60 * 5){// 验证码时限5分钟
//				this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码已过期，请重新获取"));
//				return;
//			}
//			if(!authCodeDB.equals(authCode)){
//				this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码不正确，请检查"));
//				return;
//			}
//		}
		// 生成用户验证盐
		final String salt = PasswordUtil.getSalt().toString();
		final String md5Pass = PasswordUtil.md5(credential + salt);
		
		// 事务保存用户和用户权限信息
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				// 
				User user = new User();
				user.setIsactive("1");
				boolean userFlag = UserService.saveUser(user);
				
				UserAuth userAuth = new UserAuth();
				userAuth.setUserid(user.getId());// 用户id
				userAuth.setIdentityType(identityType);
				userAuth.setIdentifier(identifier);
				userAuth.setCredential(md5Pass);
				userAuth.setRegisterTime(new Date());
				userAuth.setSalt(salt);
				userAuth.setVerified("1");
				boolean userAuthFlag = UserService.saveUserAuth(userAuth);
				
				// 生成tokenKey，保存在ehcache中
				String tokenKey = PasswordUtil.generalTokenKey();
				CacheKit.put("tokenCache", "userAuth:"+userAuth.getId(), tokenKey);
				return userFlag && userAuthFlag;
			}
		});
		
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "注册成功"));
		return;
	}
	
}
