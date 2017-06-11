package com.myapp.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.log.Log4jLog;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class MessageUtil {
	private static final Log log = Log4jLog.getLog(MessageUtil.class);
	/**
	 * 大于发送验证码
	 * 
	 * @param code
	 *            验证码
	 * @param number
	 *            手机号
	 * @param type
	 *            验证码类型
	 */
	public static boolean sendSupadataCode(String code, String number) {
		boolean result = false;
		PropKit.use("Config.properties");
		String url = PropKit.get("DayuURL");
		String appkey = PropKit.get("AppKey");
		String secret = PropKit.get("AppSecret");
		String signName = PropKit.get("SIGNNAME");
		
		Map<String, String> map = new HashMap<>();
		map.put("code", code);
		map.put("name", "【夜莺科技】");
		String seeJson = JSONObject.toJSON(map).toString();
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsParamString(seeJson);
		req.setRecNum(number);
		req.setSmsFreeSignName(signName);
		req.setSmsTemplateCode("SMS_70585378");// 验证码模板名称
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
			String errCode = rsp.getResult().getErrCode();
			if (errCode.equals("0")) {
				result = true;
			}
		} catch (Exception e) {
			log.error("发送验证码失败", e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		MessageUtil.sendSupadataCode("123", "18615566651");
	}
}
