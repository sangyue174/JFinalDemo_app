package com.myapp.utils.response;

import com.jfinal.log.Log4jLog;


/**
 * @className: DataResponse
 * @author sangyue
 * @date May 16, 2017 9:25:59 PM
 * @version V1.0
 */
public class DataResponse {
	private static final Log4jLog log = Log4jLog.getLog(DataResponse.class);
	/** 发生源 */
	protected String method;

	/** 错误级别 "success":成功，"error":异常 */
	protected String level;

	/** 错误代码 */
	protected String code;

	/** 返回信息 */
	protected String msg;

	/** 业务数据 */
	protected Object data;

	public DataResponse(LevelEnum level, String msg) {
		this("", level, "", msg, null);
	}

	public DataResponse(LevelEnum level, Object data) {
		this(level, "", data);
	}

	public DataResponse(LevelEnum level, String msg, Object data) {
		this("", level, "", msg, data);
	}

	public DataResponse(LevelEnum level, String msg, String method) {
		this(method, level, "", msg, null);
	}

	public DataResponse(LevelEnum level, String msg, String method, Object data) {
		this(method, level, "", msg, data);
	}

	public DataResponse(String method, LevelEnum level, String code,
			String msg, Object data) {
		if (LevelEnum.ERROR.getValue().equals(level.getValue())) {
			log.error("Exist Error,detail:[" + msg + "], method source:[" + method + "].");
		}
		this.method = method;
		this.level = level.getValue();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
