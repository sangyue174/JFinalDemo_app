package com.myapp.utils.response;

public enum LevelEnum {
	/** 成功 */
	SUCCESS("success"),
	/** 异常 */
	ERROR("error");

	private String value;

	private LevelEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
