package com.deertt.utils.helper.office.excel.read.valid;

public class RuleValid {

	private String name;
	private String message;

	public RuleValid() {
		super();
	}

	public RuleValid(String name, String value, String message) {
		this.name = name;
		this.value = value;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	private String value;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
