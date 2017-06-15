package com.deertt.utils.helper.office.excel.read.valid;

import java.util.HashMap;
import java.util.Map;


public class SheetValid {
	private String sheetName;
	private String code;
	private int dataBeginRowIndex;
	private Map<String, ColumnValid> columnValids;
	public SheetValid() {
		this.columnValids = new HashMap<String, ColumnValid>();
	}

	public SheetValid(String sheetName, String code, int dataBeginRowIndex) {
		this.sheetName = sheetName;
		this.code = code;
		this.dataBeginRowIndex = dataBeginRowIndex;
		this.columnValids = new HashMap<String, ColumnValid>();
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getDataBeginRowIndex() {
		return dataBeginRowIndex;
	}

	public void setDataBeginRowIndex(int dataBeginRowIndex) {
		this.dataBeginRowIndex = dataBeginRowIndex;
	}

	public Map<String, ColumnValid> getColumnValids() {
		return columnValids;
	}

	public void setColumnValids(Map<String, ColumnValid> columnValids) {
		this.columnValids = columnValids;
	}

	public void addColumnValid(ColumnValid columnValid) {
		columnValids.put(columnValid.getColIndex(), columnValid);
	}

	public ColumnValid getColumnValid(String colIndex) {
		return columnValids.get(colIndex);
	}

}
