package com.deertt.utils.helper.file;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class DvAntZipHelper {
	
	public final static String encoding = "GBK";  
	  
	// 压缩   
	public static void zip(String srcPathName, String zipFilePath) {  
		File file = new File(srcPathName);  
		if (!file.exists())  
			throw new RuntimeException(srcPathName + "不存在！");
  
		Project proj = new Project();  
		FileSet fileSet = new FileSet();  
		fileSet.setProject(proj);  
		// 判断是目录还是文件   
		if (file.isDirectory()) {  
			fileSet.setDir(file);  
			// ant中include/exclude规则在此都可以使用   
			// 比如:   
			// fileSet.setExcludes("**/*.txt"); //排除哪些文件或文件夹
			// fileSet.setIncludes("**/*.xls"); //包括哪些文件或文件夹  
		} else {  
			fileSet.setFile(file);  
		}  
  
		Zip zip = new Zip();  
		zip.setProject(proj);  
		zip.setDestFile(new File(zipFilePath));  
		zip.addFileset(fileSet);  
		zip.setEncoding(encoding);  
		zip.execute();  
	}  
  
	// 解压缩   
	public static void unZip(String zipFilePath, String destFilePath) {  
		File file = new File(zipFilePath);  
		if (!file.exists())  
			throw new RuntimeException(zipFilePath + "不存在！");
  
		Project proj = new Project();  
		Expand expand = new Expand();  
		expand.setProject(proj);  
		expand.setTaskType("unzip");  
		expand.setTaskName("unzip");  
		expand.setEncoding(encoding);  
  
		expand.setSrc(new File(zipFilePath));  
		expand.setDest(new File(destFilePath));  
		expand.execute();  
	}

	public static void main(String[] args) {
//		AntZipHelper.zip("G:\\test1", "G:\\test2.zip");  
//		AntZipHelper.unZip("G:\\test2.zip", "G:\\test3"); 
//		
//		AntZipHelper.zip("G:\\格格1.jpg", "G:\\格格2.zip");  
//		AntZipHelper.unZip("G:\\格格2.zip", "G:\\格格111.jpg"); 
//		
//		AntZipHelper.zip("G:\\格格1.jpg", "G:\\格格2.zip");  
		DvAntZipHelper.unZip("G:\\格格2.zip", "G:\\格格111.jpg"); 
	}
}
