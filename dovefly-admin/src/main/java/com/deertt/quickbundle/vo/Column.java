package com.deertt.quickbundle.vo;

public class Column {
	// 所有属性字段都是小写
	private String tableName = "";// 表名
	private String columnName = "";// 列名
	private String columnNameChinese = "";// 汉化列名，从remarks提取
	private String dataType = "";// 字段对应java类型，如果字段为Varchar类型，则对应的是"java.lang.String"等
	private int columnSize;// 列宽
	private int nullable = 1;// 是否可为空
	private String columnDef = "";// 默认值
	private int integerDigits;
	private int decimalDigits;

	private String isBuild = "0";// 是否创建，一些预留字段，和系统自动填充字段，不在此范围内
	private String isBuild_list = "0";// 是否加入列表
	private String inputType = "text";// 表单域类型
	private String dicTypeKeyword = "";//数据字典类型关键词
	private String validateType = "";// 字段校验规则字符串
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnNameChinese() {
		return columnNameChinese;
	}

	public void setColumnNameChinese(String columnNameChinese) {
		this.columnNameChinese = columnNameChinese;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public int getIntegerDigits() {
		return integerDigits;
	}

	public void setIntegerDigits(int integerDigits) {
		this.integerDigits = integerDigits;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public int getNullable() {
		return nullable;
	}

	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	public String getColumnDef() {
		return columnDef;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public String getIsBuild() {
		return isBuild;
	}

	public void setIsBuild(String isBuild) {
		this.isBuild = isBuild;
	}

	public String getIsBuild_list() {
		return isBuild_list;
	}

	public void setIsBuild_list(String isBuild_list) {
		this.isBuild_list = isBuild_list;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getDicTypeKeyword() {
		return dicTypeKeyword;
	}

	public void setDicTypeKeyword(String dicTypeKeyword) {
		this.dicTypeKeyword = dicTypeKeyword;
	}

	public String getValidateType() {
		return validateType;
	}

	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}
	
	public String getELColumnName() {
		return "#{bean." + columnName + "}";
	}

}
