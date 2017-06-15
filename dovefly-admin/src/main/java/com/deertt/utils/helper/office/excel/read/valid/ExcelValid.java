package com.deertt.utils.helper.office.excel.read.valid;

import java.util.HashMap;
import java.util.Map;

public class ExcelValid {
	private String name;
	private String code;
	private Map<String, SheetValid> sheetValids = new HashMap<String, SheetValid>();

	public ExcelValid() {
		this.sheetValids = new HashMap<String, SheetValid>();
	}

	public ExcelValid(String name, String code) {
		this.name = name;
		this.code = code;
		this.sheetValids = new HashMap<String, SheetValid>();
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

	public Map<String, SheetValid> getSheetValids() {
		return sheetValids;
	}

	public void setSheetValids(Map<String, SheetValid> sheetValids) {
		this.sheetValids = sheetValids;
	}

	public void addSheetValid(SheetValid sheetValid) {
		sheetValids.put(sheetValid.getSheetName(), sheetValid);
	}

	public SheetValid getSheetValid(String sheetName) {
		return sheetValids.get(sheetName);
	}


}
