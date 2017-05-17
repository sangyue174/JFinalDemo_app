package com.myapp.utils;

public enum IdentityTypeEnum {
	/** 手机 */
	PHONE("phone"),
	/** qq */
	QQ("qq"),
	/** 微信 */
	WEIXIN("weixin");

	private String value;

	private IdentityTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
