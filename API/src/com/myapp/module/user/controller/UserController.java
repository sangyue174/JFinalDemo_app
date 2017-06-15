package com.myapp.module.user.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.myapp.bean.User;
import com.myapp.bean.UserAuth;
import com.myapp.module.user.service.UserService;
import com.myapp.utils.DateUtil;
import com.myapp.utils.IdentityTypeEnum;
import com.myapp.utils.PasswordUtil;
import com.myapp.utils.PhoneFormatCheckUtils;
import com.myapp.utils.PhoneMessage;
import com.myapp.utils.interceptor.ValidateLoginStatusInterceptor;
import com.myapp.utils.response.DataResponse;
import com.myapp.utils.response.LevelEnum;

/**
 * 用户相关controller
 * 
 * @className: UserController
 * @author sangyue
 * @date May 22, 2017 11:32:02 PM
 * @version V1.0
 */
@Clear(ValidateLoginStatusInterceptor.class)
public class UserController extends Controller {
	/**
	 * 用户注册
	 * @title: registerAction
	 * @author sangyue
	 * @date May 21, 2017 4:00:58 PM 
	 * @version V1.0
	 */
	@Before(Tx.class)
	public void registerAction() {
//		System.out.println("class name:"+this.getClass().getName()+",method name:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String identityType = getPara("identityType") == null ? "phone"
				: getPara("identityType").toLowerCase();// 验证类型:phone,qq,weixin
		String identifier = getPara("identifier");// 验证账号
		String credential = getPara("credential");// 验证凭证
		String authCode = getPara("authCode");// 验证码
		if (StringUtils.isEmpty(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "账号不可为空，请填写", actionKey));
			return;
		}
		if (StringUtils.isEmpty(credential)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "密码不可为空，请填写", actionKey));
			return;
		}
		// 校验手机号是否合法
		if(IdentityTypeEnum.PHONE.getValue().equals(identityType) && !PhoneFormatCheckUtils.isChinaPhoneLegal(identifier)){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "手机号不合法，请修改", actionKey));
			return;
		}
		// 校验是否存在用户
		UserAuth checkUser = UserService.checkUserAuth(identityType, identifier);
		if(checkUser != null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "已经存在该用户，请修改账号", actionKey));
			return;
		}
		
		String salt = "";
		if (IdentityTypeEnum.PHONE.getValue().equals(identityType)) {
			// TODO
			// 临时设置验证码
			// testAuthCode(identifier);
			
			// 校验验证码
			if(!validateAuthCode(identifier, authCode, actionKey)){
				return;
			}
			// 生成用户验证盐
			salt = PasswordUtil.getSalt().toString();
			credential = PasswordUtil.md5(credential + salt);
		}

		// 事务保存用户和用户验证信息start
		// 先保存用户基本信息
		User user = new User();
		user.setIsactive("1");
		UserService.saveUser(user);

		// 生成tokenKey
		String tokenKey = PasswordUtil.generalTokenKey();
		// 再保存用户验证信息
		UserAuth userAuth = new UserAuth();
		userAuth.setUserid(user.getId());// 用户id，可以从上一步拿到刚保存的用户信息
		userAuth.setIdentityType(identityType);
		userAuth.setIdentifier(identifier);
		userAuth.setCredential(credential);
		userAuth.setRegisterTime(new Date());
		userAuth.setSalt(salt);
		userAuth.setTokenKey(tokenKey);
		userAuth.setTokenTime(DateUtil.addMonth(new Date(), 1));// token保存时间为1个月
		userAuth.setLoginTime(new Date());
		userAuth.setVerified("1");
		UserService.saveUserAuth(userAuth);
		
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "注册成功", actionKey, tokenKey));
		return;
	}
	
	/**
	 * 用户登录
	 * 
	 * @title: loginAction
	 * @author sangyue
	 * @date May 23, 2017 12:06:50 AM
	 * @version V1.0
	 */
	public void loginAction() {
		// 传相应的登录类型过来，然后查询数据库，如果能找到对应userAuth（其中包含密码+salt的验证）,更新tokenKey过期时间
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		final String identityType = getPara("identityType") == null ? "phone"
				: getPara("identityType").toLowerCase();// 验证类型:phone,qq,weixin
		final String identifier = getPara("identifier");// 验证账号，openid
		String credential = getPara("credential");// 验证凭证
		final String tokenKey = getPara("tokenKey");// access_token，三方使用
		final String expiresIn = getPara("expiresIn");// access_token有效时间，三方使用，单位为秒
		
		if (StringUtils.isEmpty(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "账号不可为空，请填写", actionKey));
			return;
		}
		
		if (IdentityTypeEnum.PHONE.getValue().equals(identityType)) {
			// 校验手机号是否合法
			if(!PhoneFormatCheckUtils.isChinaPhoneLegal(identifier)){
				this.renderJson(new DataResponse(LevelEnum.ERROR, "手机号不合法，请修改", actionKey));
				return;
			}
			// 手机登录密码不可为空
			if(StringUtils.isEmpty(credential)){
				this.renderJson(new DataResponse(LevelEnum.ERROR, "密码不可为空，请填写", actionKey));
				return;
			}
			// 校验是否存在用户
			UserAuth checkUser = UserService.checkUserAuth(identityType, identifier);
			if(checkUser == null){
				this.renderJson(new DataResponse(LevelEnum.ERROR, "不存在该用户，请检查账号", actionKey));
				return;
			}
			// 验证手机密码
			String salt = checkUser.getSalt();// 获取用户盐
			credential = PasswordUtil.md5(credential + salt);
			if(!credential.equals(checkUser.getCredential())){
				this.renderJson(new DataResponse(LevelEnum.ERROR, "密码不正确，请修改", actionKey));
				return;
			}
			// 更新tokenkey和token过期时间,用户登录时间
			String tokenKeyNew = PasswordUtil.generalTokenKey();
			checkUser.setTokenKey(tokenKeyNew);
			checkUser.setTokenTime(DateUtil.addMonth(new Date(), 1));// token保存时间为1个月
			checkUser.setLoginTime(new Date());
			UserService.updateUserAuth(checkUser);
			
			this.renderJson(new DataResponse(LevelEnum.SUCCESS, "登录成功", actionKey, tokenKeyNew));
			return;
		} else {// qq 微信登陆需要判断是否存在用户
			if(StringUtils.isEmpty(tokenKey)){
				this.renderJson(new DataResponse(LevelEnum.ERROR, "access_token为空", actionKey));
				return;
			}
			if(StringUtils.isEmpty(expiresIn)){
				this.renderJson(new DataResponse(LevelEnum.ERROR, "expires_in为空", actionKey));
				return;
			}
			UserAuth checkUser = UserService.checkUserAuth(identityType, identifier);
			if (checkUser == null) {// 未使用三方登录过系统，则直接新增登录信息
				boolean finalFlag = Db.tx(new IAtom() {
					public boolean run() throws SQLException {
						// 先保存用户基本信息
						User user = new User();
						user.setIsactive("1");
						boolean userFlag = UserService.saveUser(user);
						// 再保存userAuth信息
						UserAuth userAuth = new UserAuth();
						userAuth.setUserid(user.getId());// 用户id，可以从上一步拿到刚保存的用户信息
						userAuth.setIdentityType(identityType);
						userAuth.setIdentifier(identifier);
						userAuth.setCredential("");
						userAuth.setRegisterTime(new Date());
						userAuth.setSalt("");
						userAuth.setTokenKey(tokenKey);
						userAuth.setTokenTime(DateUtil.addSecond(new Date(), Integer.valueOf(expiresIn)));// access_token有效时间，三方使用，单位为秒
						userAuth.setLoginTime(new Date());
						userAuth.setVerified("1");
						boolean userAuthFlag = UserService.saveUserAuth(userAuth);
						
						return userFlag && userAuthFlag;
					}
				});
				if (finalFlag) {
					this.renderJson(new DataResponse(LevelEnum.SUCCESS, "登录成功", actionKey));
					return;
				} else {
					this.renderJson(new DataResponse(LevelEnum.ERROR, "登录失败", actionKey));
					return;
				}
			}else{// 使用三方登录过系统，则直接更新expiresIn
				checkUser.setTokenKey(tokenKey);
				checkUser.setTokenTime(DateUtil.addSecond(new Date(), Integer.valueOf(expiresIn)));// access_token有效时间，三方使用，单位为秒)
				UserService.updateUserAuth(checkUser);
				
				this.renderJson(new DataResponse(LevelEnum.SUCCESS, "登录成功", actionKey));
				return;
			}
		}
		
	}
	
	/**
	 * 退出登录
	 * @title: logOutAction
	 * @author sangyue
	 * @date Jun 10, 2017 12:28:30 PM 
	 * @version V1.0
	 */
	public void logOutAction(){
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String identityType = getPara("identityType") == null ? "phone"
				: getPara("identityType").toLowerCase();// 验证类型:phone,qq,weixin
		String identifier = getPara("identifier");// 验证账号，openid
		
		if (StringUtils.isEmpty(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "账号不可为空，请填写", actionKey));
			return;
		}
		
		UserAuth checkUser = UserService.checkUserAuth(identityType, identifier);
		if(checkUser == null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "不存在该用户，请检查账号", actionKey));
			return;
		}
		checkUser.setTokenKey("");
		checkUser.setTokenTime(null);
		UserService.updateUserAuth(checkUser);
		
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "退出登录成功", actionKey));
		return;
	}
	
	/**
	 * 忘记密码
	 * @title: forgetPassAction
	 * @author sangyue
	 * @date Jun 11, 2017 1:12:20 AM 
	 * @version V1.0
	 */
	public void forgetPassAction(){
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String identifier = getPara("identifier");// 验证账号
		String credential = getPara("credential");// 验证凭证
		String authCode = getPara("authCode");// 验证码
		
		if (StringUtils.isEmpty(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "账号不可为空，请填写", actionKey));
			return;
		}
		// TODO
		// 临时设置验证码
		// testAuthCode(identifier);

		// 校验验证码
		if(!validateAuthCode(identifier, authCode, actionKey)){
			return;
		}

		UserAuth checkUser = UserService.checkUserAuth(IdentityTypeEnum.PHONE.getValue(), identifier);
		if(checkUser == null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "不存在该用户，请检查账号", actionKey));
			return;
		}
		// 验证手机密码
		String salt = checkUser.getSalt();// 获取用户盐
		credential = PasswordUtil.md5(credential + salt);
		checkUser.setCredential(credential);
		UserService.updateUserAuth(checkUser);
		
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "设置密码成功", actionKey));
		return;
	}
	
	/**
	 * 修改密码
	 * @title: modifyPassAction
	 * @author sangyue
	 * @date Jun 11, 2017 3:20:10 AM 
	 * @version V1.0
	 */
	public void modifyPassAction(){
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String identifier = getPara("identifier");// 验证账号
		String preCredential = getPara("preCredential");// 原验证凭证
		String newCredential = getPara("newCredential");// 新验证凭证
		
		if (StringUtils.isEmpty(identifier)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "账号不可为空，请填写", actionKey));
			return;
		}
		if (StringUtils.isEmpty(preCredential) || StringUtils.isEmpty(newCredential)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "密码不可为空，请填写", actionKey));
			return;
		}

		UserAuth checkUser = UserService.checkUserAuth(IdentityTypeEnum.PHONE.getValue(), identifier);
		if(checkUser == null){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "不存在该用户，请检查账号", actionKey));
			return;
		}
		// 验证手机密码
		String salt = checkUser.getSalt();// 获取用户盐
		preCredential = PasswordUtil.md5(preCredential + salt);
		if(!checkUser.getCredential().equals(preCredential)){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "原手机号/密码不正确，请检查", actionKey));
			return;
		}
		// 设置新密码
		newCredential = PasswordUtil.md5(newCredential + salt);
		checkUser.setCredential(newCredential);
		UserService.updateUserAuth(checkUser);
		
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "修改密码成功", actionKey));
		return;
	}
	
	/**
	 * 校验验证码
	 * @title: validateAuthCode
	 * @author sangyue
	 * @date Jun 11, 2017 3:03:43 AM
	 * @param identifier
	 * @param authCode
	 * @param actionKey
	 * @return 
	 * @version V1.0
	 */
	public boolean validateAuthCode(String identifier, String authCode, String actionKey){
		if(StringUtils.isEmpty(authCode)){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码不可为空，请填写", actionKey));
			return false;
		}
		// 获取ehcache中的验证码信息
		PhoneMessage pm = CacheKit.get("authCodeCache", identifier);
		if (pm == null || StringUtils.isEmpty(pm.getAuthCode())) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码已过期，请重新获取", actionKey));
			return false;
		}
		String authCodeDB = pm.getAuthCode();
		long sendTime = pm.getSendTime().getTime();
		if(new Date().getTime() - sendTime > 1000 * 60 * 5){// 验证码时限5分钟
			this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码已过期，请重新获取", actionKey));
			return false;
		}
		if(!authCodeDB.equals(authCode)){
			this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码不正确，请检查", actionKey));
			return false;
		}
		return true;
	}
	
	/**
	 * 临时设置验证码
	 * @title: testAuthCode
	 * @author sangyue
	 * @date Jun 11, 2017 11:23:17 AM
	 * @param identifier 
	 * @version V1.0
	 */
	public void testAuthCode(String identifier){
		PhoneMessage pm1 = new PhoneMessage();
		pm1.setAuthCode("1234");
		pm1.setSendTime(DateUtil.addMinute(new Date(), -4));
		pm1.setPhone(identifier);
		CacheKit.put("authCodeCache", identifier, pm1);
	}
	
	public void test(){
		User user = User.dao.findById(1);
		List<UserAuth> ua = user.getUserAuth();
		this.renderJson(ua);
	}

}
