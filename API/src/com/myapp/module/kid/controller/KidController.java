package com.myapp.module.kid.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.myapp.bean.Kid;
import com.myapp.bean.UserAuth;
import com.myapp.module.kid.service.KidService;
import com.myapp.module.user.service.UserService;
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
	 */
	public void addKidAction() {
		UploadFile imageFile = getFile();
		String headurl = imageFile.getUploadPath() + imageFile.getOriginalFileName();// 头像url

		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String tokenKey = getPara("tokenKey");// 获取tokenKey查询用户信息
		String nickname = getPara("nickname");// 昵称
		String sex = getPara("sex") == null ? "1" : "0";// 性别(0:女1:男)
		Date birthday = getParaToDate("birthday", new Date());// 生日
		String healthIssue = getPara("healthIssue") == null ? "0" : "1";// 健康问题(0:没有1:有)

		// 根据tokenKey查询相关用户信息
		UserAuth userAuth = UserService.findUserAuthByTokenKey(tokenKey);
		if (userAuth == null) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "未查询到当前tokenKey["
					+ tokenKey + "]用户，请重试", actionKey));
			return;
		}
		int userid = userAuth.getUserid();

		// 保存孩子信息
		Kid kid = new Kid();
		kid.setUserid(userid);
		kid.setNickname(nickname);
		kid.setSex(sex);
		kid.setBirthday(birthday);
		kid.setHeadurl(headurl);
		kid.setHealthIssue(healthIssue);
		KidService.saveKid(kid);

		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "保存孩子信息成功", actionKey, kid.getId()));
		return;
	}

	/**
	 * 查询孩子信息
	 * 
	 * @title: findTipContentAction
	 * @author sangyue
	 * @date Jun 17, 2017 11:33:41 AM
	 * @version V1.0
	 */
	public void findKidAction() {
		String actionKey = getAttr("actionKey").toString();// 获取actionKey
		String kidid = getPara("kidid"); // 孩子id

		if (StringUtils.isEmpty(kidid)) {
			this.renderJson(new DataResponse(LevelEnum.ERROR, "孩子id不可为空，请填写",
					actionKey));
			return;
		}
		// 根据kidid查找孩子信息
		Kid kid = KidService.findKidById(kidid);

		this.renderJson(new DataResponse(LevelEnum.SUCCESS, "查询孩子信息成功", actionKey, kid));
		return;
	}

}
