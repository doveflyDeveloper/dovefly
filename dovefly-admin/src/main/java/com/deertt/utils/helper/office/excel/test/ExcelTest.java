package com.deertt.utils.helper.office.excel.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.xml.sax.SAXException;

import com.deertt.utils.helper.office.excel.read.DvExcelReader;
import com.deertt.utils.helper.office.excel.read.MapContainer;
import com.deertt.utils.helper.office.excel.read.ParseExcelUtil;
import com.deertt.utils.helper.office.excel.read.PropertyBeanWrapper;
import com.deertt.utils.helper.office.excel.test.User;
import com.deertt.utils.helper.office.excel.write.DvExcelWriter;

public class ExcelTest {

	/** 主方法 
	 * @throws Exception **/
	public static void main(String[] args) throws Exception {

//		testParseExcelUtil();
//		testDvExcelReader();
		testDvExcelWriter();
	}
	
	/** 主方法 
	 * @throws Exception **/
	public static void testParseExcelUtil() throws Exception {
		
		File excelFile = new File("WebContent/files/templates/excel/user/user.xls");
		File xmlFile = new File("WebContent/files/templates/excel/user/user.xml");
		ParseExcelUtil parseExcelUtil = new ParseExcelUtil(excelFile, xmlFile);
		List<Object> sheetData = parseExcelUtil.parseSheet4Object("用户表", new PropertyBeanWrapper(User.class));
		
		if(parseExcelUtil.isSuccess()){
			for (Object obj : sheetData) {
				User user = (User) obj;
				System.out.println(user.getUsername());
			}
		} else {
			System.out.println(parseExcelUtil.getValidErrorInfo());
		}
	}
	
	
	public static void testDvExcelReader() throws IOException, SAXException, InvalidFormatException {
		File excelFile = new File("WebContent/files/templates/excel/user/user.xls");
		File xmlFile = new File("WebContent/files/templates/excel/user/user_import_config.xml");
		
		Map<String, Object> dataMap = DvExcelReader.readExcel(excelFile, xmlFile, new MapContainer() {
			
			@Override
			public Map<String, Object> makeDataContainerMap() {
				Map<String, Object> beans = new HashMap<String, Object>();
				List<User> users = new ArrayList<User>();
				beans.put("users", users);
				return beans;
			}
		});
		List<User> users = (List<User>) dataMap.get("users");
		for (User user : users) {
			System.out.println(user.getUsername());
		}
		
	}
	
	
	public static void testDvExcelWriter() throws IOException, SAXException, InvalidFormatException {
		
		File excelFile = new File("WebContent/files/templates/excel/user/user.xls");
		File xmlFile = new File("WebContent/files/templates/excel/user/user_import_config.xml");
		
		Map<String, Object> dataMap = DvExcelReader.readExcel(excelFile, xmlFile, new MapContainer() {
			
			@Override
			public Map<String, Object> makeDataContainerMap() {
				Map<String, Object> beans = new HashMap<String, Object>();
				List<User> users = new ArrayList<User>();
				beans.put("users", users);
				return beans;
			}
		});
		
		
		String templateFileName = "WebContent/files/templates/excel/user/user_export_template.xls";
		String exportFileName = "WebContent/files/templates/excel/user/user_new.xls";
		//Map<String, Object> dataMap = new HashMap<String, Object>();
		DvExcelWriter.writeExcel(templateFileName, dataMap, exportFileName);
		
		
//		List<User> users = (List<User>) dataMap.get("users");
//		for (User user : users) {
//			System.out.println(user.getUsername());
//		}
		
	}
}
