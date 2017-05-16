package com.myapp.utils.response;

import java.util.Map;

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
}
