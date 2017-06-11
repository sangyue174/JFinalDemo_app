package com.myapp.utils;

import java.io.Serializable;
import java.util.Date;

public class PhoneMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String phone;
	private String authCode;
	private Date sendTime;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
