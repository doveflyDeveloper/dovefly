package com.deertt.frame.base.web.vo;

public class HandleResult {
	private boolean success = false;
	private String message = "操作失败！";
	private Object data;
	
	public HandleResult() {
		super();
	}
	
	public HandleResult(boolean success, String message) {
		this.success = success;
		this.message = message;
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
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	

}
