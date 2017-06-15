package com.deertt.common.pay.vo;

public class AlipayUserInfo {
	private String avatar;
	private String real_name;
	private String nick_name;
	private String email;
	private String mobile;

	private String body;

	public AlipayUserInfo() {
		super();
	}

	public AlipayUserInfo(String avatar, String real_name, String nick_name,
			String email, String mobile, String body) {
		super();
		this.avatar = avatar;
		this.real_name = real_name;
		this.nick_name = nick_name;
		this.email = email;
		this.mobile = mobile;
		this.body = body;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
