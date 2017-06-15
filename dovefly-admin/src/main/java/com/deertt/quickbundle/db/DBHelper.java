package com.deertt.quickbundle.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


import com.deertt.quickbundle.util.ConfigConstants;
import com.deertt.quickbundle.vo.Column;
import com.deertt.quickbundle.vo.Table;

import com.deertt.utils.helper.string.DvStringHelper;


public class DBHelper {
	
	
	public Connection conn = null;
	
	public String driver = ConfigConstants.default_driver;
	
	public String url = ConfigConstants.default_url;
	
	public String userName = ConfigConstants.default_userName;
	
	public String password = ConfigConstants.default_password;
	
	public DBHelper() {
		super();
	}

	public DBHelper(String driver, String url, String userName, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	String catalog = null;
	String schemaPattern = null;
	String tableNamePattern = null;
	
	/**
	 * 功能: 连接数据库
	 * 
	 * @param driver
	 * @param url
	 * @param userName
	 * @param password
	 */
	public boolean connectDB(String driver, String url, String userName, String password) {
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName(driver); //初始化数据库连接
				conn = DriverManager.getConnection(url, userName, password);
				return true;
			} else {
				return true;
			}
		} catch (UnsupportedClassVersionError e) {
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取当前数据库的所有表名，以表名字典序排序
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<String> getTableNames() throws SQLException{
		List<String> list = new ArrayList<String>();
		if (!connectDB(driver, url, userName, password)) {
			return null;
		}
		ResultSet rs = conn.getMetaData().getTables(catalog, schemaPattern, tableNamePattern, new String[] { "TABLE" });
		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			list.add(toLowerCase(tableName));
		}
		return list;
	}
	
	/**
	 * 获取表的列信息
	 * 
	 * @param table_name
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(String table_name) throws SQLException{
		Table table = new Table();
		if (!connectDB(driver, url, userName, password)) {
			return null;
		}
		//设置表名和中文名
		table.setTableName(toLowerCase(table_name));//表名称
		table.setTableNameChinese(getTableComment4MySql(table_name));//表注释做中文名
//		ResultSet rs = conn.getMetaData().getTables(catalog, schemaPattern, table_name, new String[] { "TABLE" });
//		while (rs.next()) {
//			table.setTableName(toLowerCase(rs.getString("TABLE_NAME")));//表名称
//			table.setTableNameChinese(toLowerCase(rs.getString("REMARKS")));
//			table.setTableComment(toLowerCase(rs.getString("REMARKS")));//表注释
//		}
		
		//设置主键和关键列
		ResultSet rsPk = conn.getMetaData().getPrimaryKeys(null, null, table_name);
		if(rsPk.next()) {
			table.setTablePk(toLowerCase(rsPk.getString("COLUMN_NAME")));
			table.setKeyColumn(toLowerCase(rsPk.getString("COLUMN_NAME")));
		}
		List<Column> columns = getColumns(table_name);
		for (Column column : columns) {
			if (column.getColumnName().equals(table.getTablePk())) {
				table.setTablePkDataType(column.getDataType());
			}
			if(column.getColumnName().endsWith("title") || column.getColumnName().endsWith("name")){
				table.setKeyColumn(column.getColumnName());
			}
		}
		
		//设置表字段
		table.setColumns(columns);
		
		return table;
	}
	
	/**
	 * 获得表的备注(适用Mysql)
	 * @param table_name
	 * @return
	 * @throws SQLException
	 */
	public String getTableComment4MySql(String table_name) throws SQLException{
		String table_comment = "";
		if (!connectDB(driver, url, userName, password)) {
			return "";
		}
		ResultSet rs = conn.prepareStatement("SELECT * FROM information_schema.TABLES WHERE table_name = '" + table_name + "'").executeQuery();
		if (rs.next()) {
			table_comment = rs.getString("table_comment");
		}
		return table_comment;
	}

	/**
	 * 获得表的字段信息
	 * @param table_name
	 * @return
	 * @throws SQLException
	 */
	public List<Column> getColumns(String table_name) throws SQLException{
		List<Column> list = new ArrayList<Column>();
		if (!connectDB(driver, url, userName, password)) {
			return null;
		}
		
		//设置每列属性
		ResultSet rs = conn.getMetaData().getColumns(catalog, schemaPattern, table_name, null);
		Map<String, Column> map = new HashMap<String, Column>();
		while (rs.next()) {
			Column column = new Column();
			
			column.setTableName(toLowerCase(rs.getString("TABLE_NAME")));//列名称 
			column.setColumnName(toLowerCase(rs.getString("COLUMN_NAME")));//列名称 
			column.setColumnNameChinese(toLowerCase(rs.getString("REMARKS")));//将备注作为中文名
//		  column.setDataType(rs.getInt("DATA_TYPE"));//来自 java.sql.Types 的 SQL 类型 
//		  column.setDataTypeDb(toLowerCase(rs.getString("TYPE_NAME")));//数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
//		  column.setTypeName(toLowerCase(rs.getString("TYPE_NAME")));//
			column.setColumnSize(rs.getInt("COLUMN_SIZE"));//COLUMN_SIZE 列表示给定列的指定列大小。对于数值数据，这是最大精度。对于字符数据，这是字符长度。对于日期时间数据类型，这是 String 表示形式的字符长度（假定允许的最大小数秒组件的精度）。对于二进制数据，这是字节长度。对于 ROWID 数据类型，这是字节长度。对于列大小不适用的数据类型，则返回 Null。 
			column.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));//小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。 
			column.setNullable(rs.getInt("NULLABLE"));//是否允许使用 NULL。 
//		  column.setRemarks(toLowerCase(rs.getString("REMARKS")));//列注释
			column.setColumnDef(toLowerCase(rs.getString("COLUMN_DEF")));//该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
			
			//设置字段校验关键词
			if(DvStringHelper.arrayContainString(ConfigConstants.default_no_build_fields, column.getColumnName().toLowerCase())){
				column.setIsBuild("0");
				column.setIsBuild_list("0");
			} else {
				column.setIsBuild("1");
				column.setIsBuild_list("1");
			}

			column.setValidateType(column.getNullable() == 1 ? "" : "notNull");//设置字段校验关键词
			
			list.add(column);
			map.put(column.getColumnName(), column);
		}
		
		//设置字段数据类型
		ResultSetMetaData rsmd = conn.prepareStatement("SELECT * FROM " + table_name).executeQuery().getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			String columnName = rsmd.getColumnName(i);//获取指定列的名称。
			String ColumnClassName = rsmd.getColumnClassName(i);//如果调用方法 ResultSet.getObject 从列中获取值，则返回构造其实例的 Java 类的完全限定名称。
			if(map.get(columnName) != null) {
				String dataType = ColumnClassName.substring(ColumnClassName.lastIndexOf(".") + 1);
				map.get(columnName).setDataType(dataType);
			}
		}
		return list;
	}
	
	/**
	 * 小写形式
	 * @param str
	 * @return
	 */
	public String toLowerCase(String str){
		return str == null ? "" : str.toLowerCase();
	}
	
	public static void main(String[] args){
		DBHelper db = new DBHelper();
		try {
			//System.out.println(db.getTableNames());
			System.out.println(db.getTable("t_notice"));
			//System.out.println(db.getColumns("t_notice"));
			//System.out.println(db.getTableComment4MySql("t_notice"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
