package com.myapp.utils.interceptor;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Log4jLog;
import com.myapp.bean.UserAuth;
import com.myapp.module.user.service.UserService;
import com.myapp.utils.response.DataResponse;
import com.myapp.utils.response.LevelEnum;

/**
 * 验证登录状态 全局拦截器
 * @className: ValidateLoginStatusInterceptor
 * @author sangyue
 * @date Jun 15, 2017 12:58:16 AM
 * @version V1.0
 */
public class ValidateLoginStatusInterceptor implements Interceptor {
	private static final Log4jLog log = Log4jLog
			.getLog(ExceptionIntoLogInterceptor.class);

	@Override
	public void intercept(Invocation inv) {
		Controller con = inv.getController();
		// 验证是否登录
		String tokenKey = con.getPara("tokenKey");
		Date tokenTime = null;
		if(StringUtils.isEmpty(tokenKey)){
			con.renderJson(new DataResponse(LevelEnum.ERROR, "ValidateInterceptor验证不通过，请传入tokenKey", inv.getActionKey()));
			return;
		}else{
			// 根据该tokenKey查询相应userAuth信息
			UserAuth userAuth = UserService.findUserAuthByTokenKey(tokenKey);
			if(userAuth == null){
				con.renderJson(new DataResponse(LevelEnum.ERROR, "ValidateInterceptor验证不通过，tokenKey已失效或不正确，请重新登录", inv.getActionKey()));
				return;
			}else{
				tokenTime = userAuth.getTokenTime();
				if(tokenTime.before(new Date())){// token失效时间小于当前时间，代表已经失效
					con.renderJson(new DataResponse(LevelEnum.ERROR, "ValidateInterceptor验证不通过，tokenKey已失效，请重新登录", inv.getActionKey()));
					return;
				}
			}
		}
		
		log.info("ValidateInterceptor验证通过，tokenKey为[" + tokenKey + "],tokenTime 为[" + tokenTime + "].");
		inv.invoke();
	}
}
