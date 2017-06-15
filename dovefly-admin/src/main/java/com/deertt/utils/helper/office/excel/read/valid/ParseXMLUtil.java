package com.deertt.utils.helper.office.excel.read.valid;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 解析xml工具类
 * 
 * @author PCCW-80352891
 * 
 */
public class ParseXMLUtil {

	@SuppressWarnings("unchecked")
	public static ExcelValid parseXml(File xmlFile){
		ExcelValid excelValid = new ExcelValid();
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new FileInputStream(xmlFile));
			Element root = doc.getRootElement();
			Iterator<Element> itSheets = root.elements("sheet").iterator();
			while (itSheets.hasNext()) {
				Element sheet = itSheets.next();
				String sheetName = sheet.attributeValue("name");
				String sheetCode = sheet.attributeValue("code");
				int dataBeginRowIndex = Integer.valueOf(sheet.attributeValue("dataBeginRowIndex"));
				SheetValid sheetValid = new SheetValid(sheetName, sheetCode, dataBeginRowIndex);
				excelValid.addSheetValid(sheetValid);
				Iterator<Element> itColumns = sheet.elements("column").iterator();
				while (itColumns.hasNext()) {
					Element column = itColumns.next();
					String colName = column.attributeValue("name");
					String colCode = column.attributeValue("code");
					String colType = column.attributeValue("type");
					String colIndex = column.attributeValue("colIndex");
					boolean nullable = Boolean.valueOf(column.attributeValue("nullable"));
					ColumnValid columnValid = new ColumnValid(colName, colCode, colType, colIndex, nullable);
					sheetValid.addColumnValid(columnValid);
					
					Iterator<Element> itRules = column.elements("rule").iterator(); // 获得 rule
					while (itRules.hasNext()) {
						Element rule = (Element) itRules.next(); // 获得每一行rule
						String ruleName = rule.attributeValue("name");
						String ruleMsg = rule.attributeValue("message");
						String ruleValue = rule.attributeValue("value");
						RuleValid ruleValid = new RuleValid(ruleName, ruleValue, ruleMsg);
						columnValid.addRule(ruleValid);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excelValid;
	}


	// 主方法
	public static void main(String[] args) {
		File file = new File("src/user.xml");
		ExcelValid excelValid = parseXml(file);
		System.out.print(excelValid);
	}


}
