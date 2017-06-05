package com.myapp.module.user.controller;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.log.Log4jLog;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.myapp.bean.User;
import com.myapp.bean.UserAuth;
import com.myapp.module.user.service.UserService;
import com.myapp.utils.DateUtil;
import com.myapp.utils.IdentityTypeEnum;
import com.myapp.utils.PasswordUtil;
import com.myapp.utils.PhoneFormatCheckUtils;
import com.myapp.utils.PhoneMessage;
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
public class UserController extends Controller {
	private static Log4jLog log = Log4jLog.getLog(UserController.class);

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
		
		// 手机登录密码不可为空
		if (IdentityTypeEnum.PHONE.getValue().equals(identityType)) {
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
			// 更新user token过期时间
			Date tokenTime = checkUser.getTokenTime();
			checkUser.setTokenTime(DateUtil.addDay(tokenTime, 30));
			UserService.updateUserAuth(checkUser);
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
		
		String salt = "", md5Pass = "";
		if (IdentityTypeEnum.PHONE.getValue().equals(identityType)) {
			// 获取session中的验证码信息
			PhoneMessage pm = (PhoneMessage)getSessionAttr(identifier);
			if(pm == null || pm.getAuthCode() == null){
				this.renderJson(new DataResponse(LevelEnum.ERROR, "请先获取验证码", actionKey));
				return;
			}
			if(StringUtils.isEmpty(authCode)){
				this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码不可为空，请填写", actionKey));
				return;
			}
			String authCodeDB = pm.getAuthCode();
			long sendTime = pm.getSendTime().getTime();
			if(new Date().getTime() - sendTime > 1000 * 60 * 5){// 验证码时限5分钟
				this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码已过期，请重新获取", actionKey));
				return;
			}
			if(!authCodeDB.equals(authCode)){
				this.renderJson(new DataResponse(LevelEnum.ERROR, "验证码不正确，请检查", actionKey));
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
		userAuth.setCredential(md5Pass);
		userAuth.setRegisterTime(new Date());
		userAuth.setSalt(salt);
		userAuth.setTokenKey(tokenKey);
		userAuth.setTokenTime(DateUtil.addDay(new Date(), 30));// token保存时间为30天
		userAuth.setLoginTime(new Date());
		userAuth.setVerified("1");
		UserService.saveUserAuth(userAuth);
		
		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "注册成功", actionKey, tokenKey));
		return;
	}

}
