package com.myapp.utils.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.JFinal;
import com.jfinal.log.Log4jLog;

/**
 * 异常处理日志 全局拦截器
 * 
 * @className: ExceptionIntoLogInterceptor
 * @author sangyue
 * @date May 17, 2017 4:04:38 PM
 * @version V1.0
 */
public class ExceptionIntoLogInterceptor implements Interceptor {
	private static final Log4jLog log = Log4jLog
			.getLog(ExceptionIntoLogInterceptor.class);

	@Override
	public void intercept(Invocation inv) {
		try {
			inv.invoke(); // 一定要注意，把处理放在invoke之后，因为放在之前的话，是会空指针
		} catch (Exception e) {
			// log 处理
			logWrite(inv, e);
		} finally {
			// 记录日志到数据库，暂未实现
		}
	}

	/**
	 * @title: logWrite
	 * @author sangyue
	 * @date May 17, 2017 4:06:50 PM
	 * @param inv
	 * @param e 
	 * @version V1.0
	 */
	private void logWrite(Invocation inv, Exception e) {
		// 开发模式
		if (JFinal.me().getConstants().getDevMode()) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder("\n---Exception Log Begin---\n");
		sb.append("Controller:").append(inv.getController().getClass().getName()).append("\n");
		sb.append("Method:").append(inv.getMethodName()).append("\n");
		sb.append("Exception Type:").append(e.getClass().getName()).append("\n");
		sb.append("Exception Details:");
		log.error(sb.toString(), e);
	}

}
