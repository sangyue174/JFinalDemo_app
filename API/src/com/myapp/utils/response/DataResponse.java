package com.myapp.utils.response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

/**
 * @className: DataResponse
 * @author sangyue
 * @date May 16, 2017 9:25:59 PM
 * @version V1.0
 */
public class DataResponse {
	/** 发生源 */
	protected String method;

	/** 错误级别 "Success":成功，"Error":异常 */
	protected String level;

	/** 错误代码 */
	protected String code;

	/** 错误描述 */
	protected String description;

	/** 业务数据 */
	protected Map<String, Object> data;

	public DataResponse(LevelEnum level, String description) {
		this(level, description, null);
	}

	public DataResponse(LevelEnum level, Map<String, Object> data) {
		this(level, "", data);
	}

	public DataResponse(LevelEnum level, String description,
			Map<String, Object> data) {
		this("", level, "", description, data);
	}

	public DataResponse(String method, LevelEnum level, String code,
			String description, Map<String, Object> data) {
		this.method = method;
		this.level = level.getValue();
		this.code = code;
		this.description = description;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the data
	 */
	public Map<String, Object> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
