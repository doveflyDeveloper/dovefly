package com.deertt.utils.helper.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.deertt.utils.helper.string.DvString;

public class DvFileHelper {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args){
		//createFile("a.txt");
		//createFolder("\\11\\11\\");
		//copyFile("G:\\test1", "G:\\test2\\test1.jpg");
		//copyFolder("G:\\test1", "G:\\test2");
		//moveFolder("G:\\test2", "G:\\test1");
		findRoots();
//		try {
//			System.out.println(fileToString(new File("G:\\�ĵ�")));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 创建文件
	 * 
	 * @param filePath 文件路径
	 * @return 
	 */
	public static File createFile(String filePath) {
		File targetFile = new File(filePath);
		try {
			if (targetFile !=null && !targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(targetFile.getAbsolutePath());
		return targetFile;
	}
	
	/**
	 * 创建文件夹
	 * 
	 * @param folderPath 文件夹路径
	 * @return boolean
	 */
	public static File createFolder(String folderPath) {
		File targetFolder = new File(folderPath);
		if (!targetFolder.exists()) {
			targetFolder.mkdirs();
		}
		return targetFolder;
	}

	/**
	 * 复制文件
	 * 
	 * @param oldPath String 原文件路径
	 * @param newPath String 新文件路径
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			File oldFile = new File(oldPath);
			if (oldFile.exists()) { // �ļ�����ʱ
				File newFile = createFile(newPath);				
				InputStream is = new FileInputStream(oldFile);
				FileOutputStream os = new FileOutputStream(newFile);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					os.write(bytes, 0, len);
				}
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制文件夹
	 * 
	 * @param oldPath String 原文件夹路径
	 * @param newPath String 新文件夹路径
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			File oldFolder = new File(oldPath);
			if(oldFolder.exists()){
				createFolder(newPath);
				File[] file = oldFolder.listFiles();
				for (int i = 0; i < file.length; i++) {
					if (file[i].isFile()) {
						copyFile(file[i].getAbsolutePath(), newPath + File.separator + file[i].getName());
					} else if (file[i].isDirectory()) {
						copyFolder(file[i].getAbsolutePath(), newPath + File.separator + file[i].getName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName String 被删除的文件路径
	 * @param fileContent String
	 * @return boolean
	 */
	public static void delFile(String filePath) {
		try {
			File myDelFile = new File(filePath);
			if(myDelFile.exists()){
				myDelFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName String 被删除的文件夹路径
	 * @param fileContent String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {   
		try {
			File oldFolder = new File(folderPath);
			if(oldFolder.exists()){
				File[] file = oldFolder.listFiles();
				for (int i = 0; i < file.length; i++) {
					if (file[i].isFile()) {
						delFile(file[i].getAbsolutePath());
					} else if (file[i].isDirectory()) {
						delFolder(file[i].getAbsolutePath());
					}
				}
				oldFolder.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param oldPath String
	 * @param newPath String
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动文件夹
	 * 
	 * @param oldPath String
	 * @param newPath String
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}
	
	/**
	 * 重命名文件
	 * @param path 
	 * @param oldname 
	 * @param newname 
	 */
	public static void renameFile(String oldname,String newname){
		if(!oldname.equals(newname)){
			File oldfile=new File(oldname);
			File newfile=new File(newname);
			if(newfile.exists())
				System.out.println(newname+"已经存在。");
			else{
				oldfile.renameTo(newfile);
			}
		}
	}
	public static void findRoots() {
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			System.out.println(roots[i]);
			System.out.println("Free space = " + roots[i].getFreeSpace());
			System.out.println("Usable space = " + roots[i].getUsableSpace());
			System.out.println("Total space = " + roots[i].getTotalSpace());
			System.out.println();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 读取XML文件
	 * 
	 * @param path 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Document readXml(String path) throws DocumentException, IOException{
		File file=new File(path);
		BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
		SAXReader saxreader = new SAXReader();
		Document document = (Document)saxreader.read(bufferedreader);
		bufferedreader.close();
		return document;
	}
	  
	/**
	 * 功能: 从ruleXml读取到Document
	 * 
	 * @param ruleXml
	 * @return
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public static Document parseXml(String ruleXml) throws MalformedURLException, DocumentException {
		if (ruleXml == null || ruleXml.length() == 0) {
			throw new NullPointerException("xml路径是空!");
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		if (ruleXml.startsWith("file:")) {
			document = reader.read(new URL(ruleXml));
		} else if(ruleXml.startsWith("http://")) { 
			document = reader.read(new URL(ruleXml));
		} else {
			document = reader.read(new File(ruleXml));
		}

		return document;
	}

	/**
	 * 从文件读取字符串
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readStringFromFile(File file) throws IOException {
		String rtStr = "";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String tempStr = "";
		while ((tempStr = in.readLine()) != null) {
			rtStr += tempStr + "\n";
		}
		in.close();
		return rtStr;
	}
	
	/**
	 * 文件目录结构字符串
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String fileToString(File file) throws IOException {
		String str = "";
		long totalSize = 0;
		str += "卷（" + file.toString() + "）的文件夹 PATH 列表" + "\n";
		str +="卷信息\t" + " 存在:" + file.exists() + "  是文件夹:" + file.isDirectory() + "  能读:" + file.canRead() + "  能写:" + file.canWrite() + "  是否隐藏:" + file.isHidden() + "  最后修改时间:" + new Timestamp(file.lastModified()).toString().substring(0, 19) + "\n";
		str += file.getAbsoluteFile() + "\n";
		DvString rmstr = listFileRecursive(file, "│├─", totalSize);
		str += rmstr;
		str += "\n总大小:" + ((Long)rmstr.getAttribute("totalSize")).longValue() / 1024 + " k, " + ((Long)rmstr.getAttribute("totalSize")).longValue() + "B.";
		return str;
	}
	
	/**
	 * ���Ŀ¼�µ�Ŀ¼�ṹ����С�����ַ���ʽ����
	 * @param file
	 * @param sign
	 * @param totalSize
	 * @return
	 * @throws IOException
	 */
		
	private static DvString listFileRecursive(File file, String sign, long totalSize) throws IOException {
		DvString rmstr = new DvString();
		if(rmstr.getAttribute("totalSize") == null) {
			rmstr.addAttribute("totalSize", new Long(0));
		}
		String str = "";
		File[] fileChild = file.listFiles();
		if(file.getName().equals("各种版本")) {
			int a= 0;
		}
		if(fileChild != null) {
			int fileSum = 0, folderSum = 0;
			{  //计算文件和文件夹个数
				for(int i=0; i<fileChild.length; i++) {
					if(fileChild[i].isFile()) {
						fileSum ++;
					} else if(fileChild[i].isDirectory()) {
						folderSum ++;
					}
				}  
			}
			for(int i=0; i<fileChild.length; i++) {
				if(fileChild[i].isFile()) {
					fileSum ++;
					if(folderSum > 0) {
						str += sign.replaceAll("│├─", "│  ") +  fileChild[i].getName() + "\n";					  
					} else {
						str += sign.replaceAll("│├─", "   ") +  fileChild[i].getName() + "\n";
					}
					{  //计算大小
						long currentSize = ((Long)rmstr.getAttribute("totalSize")).longValue() + fileChild[i].length();
						rmstr.addAttribute("totalSize", new Long(currentSize));
					}
				}
			}
			if(fileSum > 0 && folderSum > 0) {
				str += sign.replaceAll("│├─", "│  ") + "\n";				
			}
			int tempFolderIndex = 0;
			for(int i=0; i<fileChild.length; i++) {
				if(fileChild[i].isDirectory()) {
					DvString tempRmstr = null;
					if(tempFolderIndex == folderSum-1) {
						str += sign.replaceAll("│├─", "└──") +  fileChild[i].getName() + "\n";
						tempRmstr = listFileRecursive(fileChild[i], (sign + "   ").replaceAll("│├─   ", "   │├─"), totalSize);
					} else {
						str += sign.replaceAll("│├─", "├──") +  fileChild[i].getName() + "\n";
						tempRmstr = listFileRecursive(fileChild[i], (sign + "   ").replaceAll("│├─   ", "│  │├─"), totalSize);
					}
					{
						long currentSize = ((Long)rmstr.getAttribute("totalSize")).longValue() + ((Long)tempRmstr.getAttribute("totalSize")).longValue();
						rmstr.addAttribute("totalSize", new Long(currentSize));
						str += tempRmstr;
					}
					tempFolderIndex ++;
				}
			} 
		}
		rmstr.setValue(str);
		return rmstr;
	}
}