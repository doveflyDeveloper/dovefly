package com.deertt.quickbundle.vo;

import java.util.ArrayList;
import java.util.List;

public class Table {
	//所有属性字段都是小写
	private String tableName = "";/**表名*/
	private String tableNameChinese = "";//汉化表名
	private String tableComment = "";//表描述
	private String tablePk = "";//表的主键
	private String tablePkDataType = "";//表的主键数据类型
	private String keyColumn = "";//关键列
	private String hasImpExp = "0";//是否导入导出功能
	private String hasFiles = "0";//是否带附件
	private String hasSubTabs = "0";//是否带子页签
	
	private List<Column> columns;//表中所有列	
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableNameChinese() {
		return tableNameChinese;
	}
	
	public void setTableNameChinese(String tableNameChinese) {
		this.tableNameChinese = tableNameChinese;
	}
	
	public String getTablePk() {
		return tablePk;
	}
	
	public void setTablePk(String tablePk) {
		this.tablePk = tablePk;
	}
	
	public String getTablePkDataType() {
		return tablePkDataType;
	}
	
	public void setTablePkDataType(String tablePkDataType) {
		this.tablePkDataType = tablePkDataType;
	}
	
	public String getKeyColumn() {
		return keyColumn;
	}
	
	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}
	
	public String getTableComment() {
		return tableComment;
	}
	
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	
	public String getHasImpExp() {
		return hasImpExp;
	}

	public void setHasImpExp(String hasImpExp) {
		this.hasImpExp = hasImpExp;
	}

	public String getHasFiles() {
		return hasFiles;
	}

	public void setHasFiles(String hasFiles) {
		this.hasFiles = hasFiles;
	}
	
	public String getHasSubTabs() {
		return hasSubTabs;
	}

	public void setHasSubTabs(String hasSubTabs) {
		this.hasSubTabs = hasSubTabs;
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	/**
	 * 功能: 获得Java规范后的表名，例如：t_notice_msg --> TNoticeMsg
	 *
	 * @return
	 */
	public String getTableFormatName() {
		if (tableName == null || tableName.length() == 0) {
			return tableName;
		}
		String str = "";
		String[] aName = tableName.split("_");
		for (int i = 0; i < aName.length; i++) {
			str += toUpperFirst(aName[i]);
		}
		return str;
	}
	
	/**
	 * 功能: 获得Java规范后的表名（首字母小写），例如：t_notice_msg --> tNoticeMsg
	 *
	 * @return
	 */
	public String getLowerFirstTableFormatName() {
		String tableName= getTableFormatName();
		return tableName.substring(0,1).toLowerCase() + tableName.substring(1);  
	}
	
	/**
	 * 功能: 获得Java规范后的表名（首字母大写），例如：t_notice_msg --> tNoticeMsg
	 *
	 * @return
	 */
	public String getUpperFirstTableFormatName() {
		String tableName= getTableFormatName();
		return tableName.substring(0,1).toUpperCase() + tableName.substring(1);  
	}
	
	/**
	 * 功能: 获得Java规范后的表名（全部小写），例如：t_notice_msg --> tNoticeMsg
	 *
	 * @return
	 */
	public String getLowerAllTableFormatName() {
		String tableName= getTableFormatName();
		return tableName.toLowerCase();  
	}
	
	/**
	 * 获得主键列以外的所有列
	 * @return
	 */
	public List<Column> getColumnsWithoutPk() {
		List<Column> columnsWithoutPk = new ArrayList<Column>();
		if (columns != null && columns.size() > 0) {
			for (int i = 0; i < columns.size(); i++) {
				Column column = columns.get(i);
				if (!column.getColumnName().equals(tablePk)) {
					columnsWithoutPk.add(column);
				}
			}
		}
		return columnsWithoutPk;
	}
	
	/**
	 * 获得要创建的列，不包含主键列
	 * @return
	 */
	public List<Column> getColumnsIsBuild() {
		List<Column> columnsIsBuild = new ArrayList<Column>();
		if (columns != null && columns.size() > 0) {
			for (int i = 0; i < columns.size(); i++) {
				Column column = columns.get(i);
				if (!column.getColumnName().equals(tablePk) && "1".equals(column.getIsBuild())) {
					columnsIsBuild.add(column);
				}
			}
		}
		return columnsIsBuild;
	}
	
	/**
	 * 获得不创建的列，不包含主键列
	 * @return
	 */
	public List<Column> getColumnsNotBuild() {
		List<Column> columnsNotBuild = new ArrayList<Column>();
		if (columns != null && columns.size() > 0) {
			for (int i = 0; i < columns.size(); i++) {
				Column column = columns.get(i);
				if (!column.getColumnName().equals(tablePk) && "0".equals(column.getIsBuild())) {
					columnsNotBuild.add(column);
				}
			}
		}
		return columnsNotBuild;
	}
	
	/**
	 * 获得要在列表显示的列，不包含主键列
	 * @return
	 */
	public List<Column> getColumnsIsBuildList(){
		List<Column> columnsIsBuildList = new ArrayList<Column>();
		if (columns != null && columns.size() > 0) {
			for (int i = 0; i < columns.size(); i++) {
				Column column = columns.get(i);
				if (!column.getColumnName().equals(tablePk) && "1".equals(column.getIsBuild_list())) {
					columnsIsBuildList.add(column);
				}
			}
		}
		return columnsIsBuildList;
	}

	/**
	 * 功能: 将字符串首字母大写，其余全部变小写
	 * 
	 * @param str 原字符串
	 * @return
	 */
	private static String toUpperFirst(String str) {
		if(str == null || str.length() == 0) {
			return str;
		} else {
			return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();			
		}
	}

}
