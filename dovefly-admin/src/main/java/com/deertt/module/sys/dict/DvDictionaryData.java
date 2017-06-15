package com.deertt.module.sys.dict;

public class DvDictionaryData {
	private String dic_type_keyword;
	private String dic_key;
	private String dic_value;
	private String dic_order;
	
	public DvDictionaryData() {
		super();
	}
	
	public DvDictionaryData(String dic_key, String dic_value) {
		super();
		this.dic_key = dic_key;
		this.dic_value = dic_value;
	}

	public DvDictionaryData(String dic_type_keyword, String dic_key,
			String dic_value, String dic_order) {
		super();
		this.dic_type_keyword = dic_type_keyword;
		this.dic_key = dic_key;
		this.dic_value = dic_value;
		this.dic_order = dic_order;
	}

	public String getDic_type_keyword() {
		return dic_type_keyword;
	}

	public void setDic_type_keyword(String dic_type_keyword) {
		this.dic_type_keyword = dic_type_keyword;
	}

	public String getDic_key() {
		return dic_key;
	}

	public void setDic_key(String dic_key) {
		this.dic_key = dic_key;
	}

	public String getDic_value() {
		return dic_value;
	}

	public void setDic_value(String dic_value) {
		this.dic_value = dic_value;
	}

	public String getDic_order() {
		return dic_order;
	}

	public void setDic_order(String dic_order) {
		this.dic_order = dic_order;
	}

}
