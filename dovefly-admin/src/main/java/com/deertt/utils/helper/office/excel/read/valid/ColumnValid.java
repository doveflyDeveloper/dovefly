package com.deertt.utils.helper.office.excel.read.valid;

import java.util.ArrayList;
import java.util.List;


public class ColumnValid {
	private String name;
	private String code;
	private String type;
	private String colIndex;
	private boolean nullable;

	private List<RuleValid> rules;

	public ColumnValid() {
		this.rules = new ArrayList<RuleValid>();
	}

	public ColumnValid(String name, String code, String type, String colIndex, boolean nullable) {
		this.name = name;
		this.code = code;
		this.type = type;
		this.colIndex = colIndex;
		this.nullable = nullable;
		this.rules = new ArrayList<RuleValid>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColIndex() {
		return colIndex;
	}

	public void setColIndex(String colIndex) {
		this.colIndex = colIndex;
	}
	
	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public List<RuleValid> getRules() {
		return rules;
	}

	public void setRules(List<RuleValid> rules) {
		this.rules = rules;
	}

	public void addRule(RuleValid rule) {
		rules.add(rule);
	}

}
