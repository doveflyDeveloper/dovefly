package com.deertt.utils.helper.office.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deertt.utils.helper.office.excel.read.valid.ColumnValid;
import com.deertt.utils.helper.office.excel.read.valid.ExcelValid;
import com.deertt.utils.helper.office.excel.read.valid.ParseConstans;
import com.deertt.utils.helper.office.excel.read.valid.ParseXMLUtil;
import com.deertt.utils.helper.office.excel.read.valid.RuleValid;
import com.deertt.utils.helper.office.excel.read.valid.SheetValid;
import com.deertt.utils.helper.office.excel.read.valid.ValidResult;


/**
 * 解析excel 工具类
 * 
 * @author PCCW
 * 
 */
public class ParseExcelUtil {
	
	private List<ValidResult> validErrors;
	
	private HSSFWorkbook workbook;
	
	private ExcelValid excelValid;
	
	public ParseExcelUtil(File excelFile, File xmlFile) {
		try {
			this.workbook = new HSSFWorkbook(new FileInputStream(excelFile));
			this.excelValid = ParseXMLUtil.parseXml(xmlFile);
			this.validErrors = new ArrayList<ValidResult>();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public boolean isSuccess(){
		return this.validErrors.size() == 0; 
	}
	
	public String getValidErrorInfo(){
		StringBuffer sb = new StringBuffer();
		for (ValidResult result : this.validErrors) {
			sb.append("工作表“")
			.append(result.getSheetName())
			.append("”，第")
			.append(result.getRowIndex() + 1)
			.append("行，第")
			.append(result.getColIndex() + 1)
			.append("列，“")
			.append(result.getOrigName())
			.append("”")
			.append(result.getMessage())
			.append("\n");
		}
		
		return sb.toString();
	}

	/**
	 * 解析Excel数据
	 * @param excelFile 要解析的Excel文件
	 * @param xmlFile 与Excel对应的XML校验规则文件
	 * @param sheetNames 要解析数据的Sheet页的名称（有些不需要解析的数据，如说明信息等，单独放到一个Sheet页，则这个Sheet页不要放在此参数内。）
	 * @param mark
	 * @return
	 * @throws Exception 
	 */
	public List<Object> parseSheet4Object(String sheetName, BeanWrapper beanWrapper) throws Exception {
		this.validErrors.clear();
		List<Map<String, String>> sheetDatas = parseSheetData(sheetName);
		Map<String, List<Object>> dataMap= new HashMap<String, List<Object>>();
		List<Object> list = new ArrayList<Object>(dataMap.size());
		for (Map<String, String> map : sheetDatas) {
			Object bean = beanWrapper.mapRow(map);
			list.add(bean);
		}
		return list;
	}	
	
	/**
	 * 解析ExcelSheet页数据
	 * @param sheet
	 * @param sheetValid
	 * @param mark
	 * @return
	 */
	public List<Map<String, String>> parseSheetData(String sheetName){
		HSSFSheet sheet = this.workbook.getSheet(sheetName);
		SheetValid sheetValid = this.excelValid.getSheetValid(sheetName);
		StringBuffer sheet_valid_error = new StringBuffer("");
		List<Map<String, String>> listDatas = new ArrayList<Map<String, String>>();
		int rowNumbers = sheet.getPhysicalNumberOfRows();
		if (rowNumbers == 0) {
			sheet_valid_error.append("====excel中数据为空！<br/>");
		}
		int xmlRowNum = sheetValid.getColumnValids().size();
		HSSFRow excelRow = sheet.getRow(0);
		int excelFirstRow = excelRow.getFirstCellNum();
		int excelLastRow = excelRow.getLastCellNum();
		if (xmlRowNum != (excelLastRow - excelFirstRow)) {
			sheet_valid_error.append("====xml列数与excel列数不相符，请检查<br/>");
		}
		
		int excelRowNum = sheet.getLastRowNum();// 行数
		int beginRowIndex = sheetValid.getDataBeginRowIndex();
		for (int rowIndex = beginRowIndex; rowIndex < excelRowNum + 1; rowIndex++) {// 行循环
			HSSFRow row = sheet.getRow(rowIndex);
			Map<String, String> rowData = parseRowData(rowIndex, row, sheetValid);
			listDatas.add(rowData);
		}
		return listDatas;
	}

	/**
	 * 解析Excel行数据
	 * @param row
	 * @param sheetValid
	 * @param mark
	 * @return
	 */
	public Map<String, String> parseRowData(int rowIndex, HSSFRow row, SheetValid sheetValid) {
		Map<String, String> rowData = new HashMap<String, String>();
		int excelLastcell = row.getLastCellNum();// 列数
		for (int colIndex = 0; colIndex < excelLastcell; colIndex++) {// 列循环
			HSSFCell cell = row.getCell(colIndex);
			ColumnValid columnValid = sheetValid.getColumnValid(colIndex + "");
			ValidResult validResult = validateCellData(row.getSheet().getSheetName(), rowIndex, colIndex, cell, columnValid);
			if (validResult.isSuccess()) {
				rowData.put(validResult.getOrigCode(), validResult.getOrigValue());
			}else{
				this.validErrors.add(validResult);
			}
		}
		return rowData;
	}
	
	/**
	 * 验证单元格数据
	 * @param columnValid 对当前列的校验规则
	 * @param cell 当前被校验的单元格
	 * @param mark 是否要对校验失败的单元格进行高亮标注（当前单元格设置为红色前景色，并在此行最后一列（最后一列数据列的后一列）填写错误说明信息）
	 * @return
	 */
	public ValidResult validateCellData(String sheetName, int rowIndex, int colIndex, HSSFCell cell, ColumnValid columnValid) {
		ValidResult validResult = new ValidResult();
		validResult.setSuccess(true);
		validResult.setSheetName(sheetName);
		validResult.setRowIndex(rowIndex);
		validResult.setColIndex(colIndex);
		
		if(columnValid == null){
			validResult.setSuccess(false);
			validResult.setMessage("未找到相关的校验信息。");
			return validResult;
		}
		validResult.setOrigCode(columnValid.getCode());
		validResult.setOrigName(columnValid.getName());
		
		if(cell == null && !columnValid.isNullable()){
			validResult.setSuccess(false);
			validResult.setMessage("不能为空。");
			return validResult;
		}

		String value = getStringCellValue(cell);
		validResult.setOrigValue(value);

		String xmlColType = columnValid.getType();
		if (xmlColType.equals("int")) {
			try {
				Integer.valueOf(value);
			} catch (NumberFormatException e) {
				validResult.setSuccess(false);
				validResult.setMessage("数据类型错误，必须为整数");
				return validResult;
			}
		}


		List<RuleValid> rulList = columnValid.getRules();
		if (rulList != null && rulList.size() > 0) {
			for (int i = 0; i < rulList.size(); i++) {
				RuleValid ruleValid = rulList.get(i);
				String rulName = ruleValid.getName();
				String rulValue = ruleValid.getValue();
				String rulMsg = ruleValid.getMessage();
				if(ParseConstans.MAX_LENGTH.equals(rulName)){
					int maxLength = Integer.valueOf(rulValue);
					if (value.length() > maxLength){
						validResult.setSuccess(false);
						validResult.setMessage(rulMsg);
						return validResult;
					}
				} else {
					// ////这里写其他的验证规则。。。
				}
			}
		}
		return validResult;
	}

	/**
	 * 获得单元格数据的字符串形式
	 * @param cell
	 * @return
	 */
	public String getStringCellValue(HSSFCell cell) {
		if(cell == null){
			return null;
		}
		String cellValue = null;
		switch (cell.getCellType()) {

		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			try {
				cellValue = String.valueOf(cell.getNumericCellValue());
			} catch (Exception e) {
				cellValue = cell.getRichStringCellValue().getString();
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				java.text.SimpleDateFormat TIME_FORMATTER = new java.text.SimpleDateFormat("yyyy-MM-dd");
				cellValue = TIME_FORMATTER.format(cell.getDateCellValue());
			} else {
				double doubleValue = cell.getNumericCellValue();
				cellValue = String.valueOf(doubleValue);
			}
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = null;
			break;
		default:
			cellValue = null;
		}

		return cellValue == null ? null : cellValue.trim();
	}
	/**
	 * 获得单元格数据的字符串形式
	 * @param cell
	 * @return
	 * @throws IOException 
	 */
	public void saveAs(File newFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(newFile);
		this.workbook.write(fos);
		fos.close();
	}	

}
