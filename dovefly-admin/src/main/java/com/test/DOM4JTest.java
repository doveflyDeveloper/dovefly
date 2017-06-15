package com.test;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class DOM4JTest {

	/**
	 * DOM4J读写XML示例
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		//test();
		//filePath = "TableName_import_list_config.xml";
		test2("D:\\TableName_import_list_config.xml", "D:\\TableName.xml");
	}
	
	
	public static void test2(String oldXmlFile, String newXmlFile) {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File(oldXmlFile));// 读取XML文件
			List<Element> worksheets = doc.selectNodes("workbook/worksheet");
			for(Element ele : worksheets){
				Attribute att_name = ele.attribute("name");
				att_name.setValue("表中文名");
			}
			List<Element> loops = doc.selectNodes("workbook/worksheet/loop");
			for(Element ele : loops){
				Attribute att_varType = ele.attribute("varType");
				att_varType.setValue("类包全名");
			}
			List<Element> sections = doc.selectNodes("workbook/worksheet/loop/section");
			for(Element ele : sections){
				Element mapping = ele.addElement("mapping");
				mapping.addAttribute("row", "100");
				mapping.addAttribute("col", "100");
				mapping.setText("bean.partner_mdm_code");
			}
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");// 设置XML文件的编码格式
			XMLWriter writer = new XMLWriter(new FileWriter(newXmlFile), format);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static void test() {
		try {
			XMLWriter writer = null;// 声明写XML的对象
			SAXReader reader = new SAXReader();

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");// 设置XML文件的编码格式

			String filePath = "d:\\student.xml";
			File file = new File(filePath);
			if (file.exists()) {
				Document document = reader.read(file);// 读取XML文件
				Element root = document.getRootElement();// 得到根节点
				boolean bl = false;
				for (Iterator i = root.elementIterator("学生"); i.hasNext();) {
					Element student = (Element) i.next();
					if (student.attributeValue("sid").equals("001")) {
						// 修改学生sid=001的学生信息
						student.selectSingleNode("姓名").setText("王五");
						student.selectSingleNode("年龄").setText("25");

						writer = new XMLWriter(new FileWriter(filePath), format);
						writer.write(document);
						writer.close();
						bl = true;
						break;
					}
				}
				if (bl) {
					// 添加一个学生信息
					Element student = root.addElement("学生");
					student.addAttribute("sid", "100");
					Element sid = student.addElement("编号");
					sid.setText("100");
					Element name = student.addElement("姓名");
					name.setText("嘎嘎");
					Element sex = student.addElement("性别");
					sex.setText("男");
					Element age = student.addElement("年龄");
					age.setText("21");

					writer = new XMLWriter(new FileWriter(filePath), format);
					writer.write(document);
					writer.close();
				}
			} else {
				// 新建student.xml文件并新增内容
				Document _document = DocumentHelper.createDocument();
				Element _root = _document.addElement("学生信息");
				Element _student = _root.addElement("学生");
				_student.addAttribute("sid", "001");
				Element _id = _student.addElement("编号");
				_id.setText("001");
				Element _name = _student.addElement("姓名");
				_name.setText("灰机");
				Element _age = _student.addElement("年龄");
				_age.setText("18");

				writer = new XMLWriter(new FileWriter(file), format);
				writer.write(_document);
				writer.close();
			}
			System.out.println("操作结束! ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
	private void getAllNodes(String xml) {
		try {
			Document authtmp = DocumentHelper.parseText(xml);
			List<Element> list = authtmp.selectNodes("//sms/node");
			for (int j = 0; j < list.size(); j++) {
				Element node = (Element) list.get(j);
				nodeByNodes(node);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void nodeByNodes(Element node) {
		if (node.element("node") != null) {
			String id = node.attributeValue("id");
			String name = node.attributeValue("name");
			System.out.print(id + "-------");
			System.out.println(name);
			for (Iterator i = node.elementIterator("node"); i.hasNext();) {
				Element newNode = (Element) i.next();
				nodeByNodes(newNode);
			}
		} else {
			String id = node.attributeValue("id");
			String name = node.attributeValue("name");
			System.out.print(id + "-------");
			System.out.println(name);
		}
	}

}
