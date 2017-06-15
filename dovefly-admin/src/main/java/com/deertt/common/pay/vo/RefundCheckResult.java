package com.deertt.common.pay.vo;

public class RefundCheckResult {
	private boolean success;
	private String message;
	private String body;
	
	public RefundCheckResult() {
		super();
	}
	
	public RefundCheckResult(boolean success, String message, String body) {
		super();
		this.success = success;
		this.message = message;
		this.body = body;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
