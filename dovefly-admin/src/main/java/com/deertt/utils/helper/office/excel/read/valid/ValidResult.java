package com.deertt.utils.helper.office.excel.read.valid;

public class ValidResult {
	private String sheetName;
	private int rowIndex;
	private int colIndex;
	private boolean success;
	private String message;
	private String origCode;
	private String origName;
	private String origValue;

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
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

	public String getOrigCode() {
		return origCode;
	}
	
	public void setOrigCode(String origCode) {
		this.origCode = origCode;
	}
	
	public String getOrigName() {
		return origName;
	}

	public void setOrigName(String origName) {
		this.origName = origName;
	}

	public String getOrigValue() {
		return origValue;
	}

	public void setOrigValue(String origValue) {
		this.origValue = origValue;
	}

}
