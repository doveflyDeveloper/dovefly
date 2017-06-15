package com.deertt.utils.helper.office.excel.write;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

public class DvExcelWriter {
	
	public static void writeExcel(String templateFileName, Map<String, Object> dataMap, String exportFileName) throws ParsePropertyException, IOException, InvalidFormatException{
		XLSTransformer transformer = new XLSTransformer();
		transformer.transformXLS(templateFileName, dataMap, exportFileName);	
	}
	
	public static void writeExcel(String templateFileName, Map<String, Object> dataMap, OutputStream exportStream) throws ParsePropertyException, IOException, InvalidFormatException{
		//XLSTransformer transformer = new XLSTransformer();
		//transformer.transformXLS(templateFileName, dataMap); 
		Workbook workbook = writeExcel(templateFileName, dataMap);
		workbook.write(exportStream);
		if(exportStream != null){
			exportStream.flush();
			exportStream.close();
		}
	}
	
	public static Workbook writeExcel(String templateFileName, Map<String, Object> dataMap) throws ParsePropertyException, IOException, InvalidFormatException{
		XLSTransformer transformer = new XLSTransformer();
		InputStream inputstream = new FileInputStream(new File(templateFileName));
		Workbook workbook = transformer.transformXLS(inputstream, dataMap);
		return workbook;
	}
}
