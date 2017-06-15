package com.deertt.quickbundle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import com.deertt.quickbundle.vo.Column;
import com.deertt.quickbundle.vo.Project;
import com.deertt.quickbundle.vo.Table;

import com.deertt.utils.helper.office.excel.write.DvExcelWriter;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freemarker 模板工具
 * 
 * @author Ying-er
 * @time 2010-2-6下午04:07:27
 * @version 1.0
 */
public class FreeMarkerUtil {

	public static String ENCODING = "UTF-8";

	/**
	 * 创建Configuration对象
	 * 
	 * @param templatePath
	 *			模板文件基目录
	 * @return
	 * @throws IOException
	 */
	public static Configuration getConfiguration(String templatePath) {
		// 创建Configuration对象
		Configuration config = new Configuration();

		// 指定模板路径目录，并加载模板文件
		try {
			config.setDirectoryForTemplateLoading(new File(templatePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 设置包装器，并将对象包装为数据模型
		config.setObjectWrapper(new DefaultObjectWrapper());

		return config;
	}

	/**
	 * 获取模板基目录下所有模板文件
	 * 
	 * @param templatePath
	 * @return
	 */
	public static List<String> getfilesPath(String templatePath) {
		List<String> files = new ArrayList<String>();
		inerateDir(templatePath, files, ".ftl");
		return files;
	}

	/**
	 * 迭代目录结果搜索所有的指定后缀名的文件
	 * 
	 * @param path
	 * @param files
	 */
	public static void inerateDir(String path, List<String> filesPath, String endsWithStr) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isFile()) {
				if (file.getName().endsWith(endsWithStr)) {
					filesPath.add(file.getPath().replaceAll("\\\\", "/"));
				}
			} else {
				File[] tmp = file.listFiles();
				if (tmp != null) {
					for (int i = 0; i < tmp.length; i++) {
						inerateDir(tmp[i].getPath(), filesPath, endsWithStr);
					}
				}
			}
		}
	}
	
	public static void generateFiles(Project project, Table table) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("project", project);
		root.put("table", table);

		// 创建Configuration对象
		String templateDir = FreeMarkerUtil.class.getResource("").getPath() + "templates/singleTableStandard";
		Configuration config = getConfiguration(templateDir);
		List<String> filesPath = getfilesPath(templateDir);
		for (String filePath : filesPath) {
			analysisTemplate(config, filePath, root);
		}
		generateXlsTemplateFiles(project, table);
		generateImportListConfigXmlFile(project, table);
	}

	/**
	 * 
	 * @param templateName
	 *			模板文件名称
	 * @param templateEncoding
	 *			模板文件的编码方式
	 * @param root
	 *			数据模型根对象
	 */
	public static void analysisTemplate(Configuration config,
			String templateName, Map<?, ?> root) {
		try {
			// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
			String subTemplateName = templateName.substring(templateName
					.indexOf("singleTableStandard")
					+ "singleTableStandard".length());
			Template template = config.getTemplate(subTemplateName, ENCODING);

			// 合并数据模型与模板
			Table table = (Table) root.get("table");
			Project project = (Project) root.get("project");
			String suffix = templateName.indexOf("java") >= 0 ? ".java" : ".jsp";
			table.getUpperFirstTableFormatName();
			String filePath = "";
			if(suffix.equals(".java")){
				filePath = project.getBaseProjectPath() + "/"
				+ project.getJavaFileRealPath() 
				+ subTemplateName
						.replace("java", table.getLowerAllTableFormatName())
						.replace("TableName", table.getUpperFirstTableFormatName())
						.replace(".ftl", suffix);
			}else{
				filePath = project.getBaseProjectPath() + "/"
				+ project.getWebAppName() + "/" + project.getJspSourcePath()
				+ subTemplateName
						.replace("jsp", table.getLowerAllTableFormatName())
						.replace("TableName", table.getUpperFirstTableFormatName())
						.replace(".ftl", suffix);
			}

			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			// Writer out = new OutputStreamWriter(System.out);//测试查看结果
			template.process(root, out);
			out.flush();
			out.close();
			System.out.println("模板文件：" + templateName);
			System.out.println("生成文件：" + filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void generateXlsTemplateFiles(Project project, Table table) {
		try {
			String templateDir = FreeMarkerUtil.class.getResource("").getPath() + "templates/singleTableStandard/xlsTemplate";
			List<String> files = new ArrayList<String>();
			inerateDir(templateDir, files, ".xls");
			for (String templateFilePath : files) {
				
				String newFilePath = project.getBaseProjectPath()
				+ "/"
				+ project.getWebAppName()
				+ "/"
				+ project.getXlsTemplateBasePath()
				+ "/"
				+ table.getTableFormatName().toLowerCase()
				+ "/"
				+ templateFilePath.substring(templateFilePath.lastIndexOf("TableName")).replace("TableName",
						table.getTableFormatName().toLowerCase());
				
				File file = new File(newFilePath);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("table", table);
				
				DvExcelWriter.writeExcel(templateFilePath, dataMap, new FileOutputStream(file));
				readXlsAndReplaceSpecialChar(newFilePath, table.getTableNameChinese());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void readXlsAndReplaceSpecialChar(String excelFile, String newSheetName){
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelFile));
			int sheetNum = workbook.getNumberOfSheets();
			workbook.setSheetName(0, newSheetName);
			for (int sheetIndex = 0; sheetIndex < sheetNum; sheetIndex++) {// sheet循环
				HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
				if(sheet != null){
					int excelRowNum = sheet.getLastRowNum();// 行数
					for (int rowIndex = 0; rowIndex < excelRowNum + 1; rowIndex++) {// 行循环
						HSSFRow row = sheet.getRow(rowIndex);
						if(row != null){
							int excelLastcell = row.getLastCellNum();// 列数
							for (int colIndex = 0; colIndex < excelLastcell; colIndex++) {// 列循环
								HSSFCell cell = row.getCell(colIndex);
								if(cell != null){
									if(cell.getRichStringCellValue() != null){
										String cellValue = cell.getRichStringCellValue().getString();
										if(cellValue != null){
											cellValue = cellValue.replaceAll("#", "\\$").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
											cell.setCellValue(cellValue);
										}
									}
								}
							}
						}
					}
				}
			}
			FileOutputStream fos = new FileOutputStream(excelFile);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void generateImportListConfigXmlFile(Project project, Table table) {
		try {
			String templateDir = FreeMarkerUtil.class.getResource("").getPath() + "templates/singleTableStandard/xlsTemplate";
			String oldXmlFile = templateDir + "/TableName_import_list_config.xml";
			String newFilePath = project.getBaseProjectPath()
			+ "/"
			+ project.getWebAppName()
			+ "/"
			+ project.getXlsTemplateBasePath()
			+ "/"
			+ table.getTableFormatName().toLowerCase()
			+ "/"
			+ "TableName_import_list_config.xml".replace("TableName",
					table.getTableFormatName().toLowerCase());
			
			File file = new File(newFilePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			} 	
			
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File(oldXmlFile));// 读取XML文件
			List<Element> worksheets = doc.selectNodes("workbook/worksheet");
			for(Element ele : worksheets){
				Attribute att_name = ele.attribute("name");
				att_name.setValue(table.getTableNameChinese());
			}
			List<Element> loops = doc.selectNodes("workbook/worksheet/loop");
			for(Element ele : loops){
				Attribute att_varType = ele.attribute("varType");
				att_varType.setValue(project.getJavaPackageName() + "." + table.getTableFormatName().toLowerCase() + ".vo." + table.getUpperFirstTableFormatName() + "Vo");
			}
			List<Element> sections = doc.selectNodes("workbook/worksheet/loop/section");
			for(Element ele : sections){
				for (int i = 0; i < table.getColumnsIsBuild().size(); i++) {
					Column col = table.getColumnsIsBuild().get(i);
					Element mapping = ele.addElement("mapping");
					mapping.addAttribute("row", "1");
					mapping.addAttribute("col", i + "");
					mapping.setText("bean." + col.getColumnName());
				}
			}
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");// 设置XML文件的编码格式
			XMLWriter writer = new XMLWriter(new FileWriter(newFilePath), format);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		//generateFiles("t_partner_mod_base_info");
		
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			//Configuration configuration = new Configuration();
			String templateDir = FreeMarkerUtil.class.getResource("").getPath() + "templates/singleTableStandard";
			Configuration configuration = getConfiguration(templateDir);
			configuration.setDefaultEncoding("UTF-8");// 这里很重要
			dataMap.put("name", "格格");
			dataMap.put("age", 11);
			Template t = null;
			try {
				// test.ftl为要装载的模板
				t = configuration.getTemplate("excel2003.xml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 输出文档路径及名称
			File outFile = new File("D:/excel2003.xls");
			Writer out = null;
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));// 这里很重要
			t.process(dataMap, out);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
}
