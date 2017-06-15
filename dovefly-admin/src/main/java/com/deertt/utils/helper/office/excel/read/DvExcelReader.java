package com.deertt.utils.helper.office.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.xml.sax.SAXException;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;

public class DvExcelReader {
	
	public static Map<String, Object> readExcel(File excelFile, File xmlFile, MapContainer container) throws IOException, SAXException, InvalidFormatException {

		FileInputStream dataStream = new FileInputStream(excelFile);
		FileInputStream xmlConfig = new FileInputStream(xmlFile);
		// 绑定xml文件
		XLSReader xlsReader = ReaderBuilder.buildFromXML(xmlConfig);

		Map<String, Object> beans = container.makeDataContainerMap();
		
		// 通过XSLReader 的read方法，它会自动映射pojo类，得到数据集合
		xlsReader.read(dataStream, beans);
		
		return beans;
	}

}