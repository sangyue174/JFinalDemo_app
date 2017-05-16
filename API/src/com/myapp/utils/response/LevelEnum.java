package com.myapp.utils.response;

public enum LevelEnum {
	/** 成功 */
	SUCCESS("Success"),
	/** 异常 */
	ERROR("Error");

	private String value;

	private LevelEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
