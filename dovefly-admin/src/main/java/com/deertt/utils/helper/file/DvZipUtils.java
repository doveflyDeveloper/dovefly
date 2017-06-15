package com.deertt.utils.helper.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 
 * 功能： 
 * 1 、实现把指定文件夹下的所有文件压缩为指定文件夹下指定 zip 文件 
 * 2 、实现把指定文件夹下的 zip 文件解压到指定目录下
 * 
 * @author ffshi
 */
public class DvZipUtils {

	public static void main(String[] args) {

		// 把 E 盘正则表达式文件夹下的所有文件压缩到 E 盘 stu 目录下，压缩后的文件名保存为 正则表达式 .zip
		//zip ("G:\\test1", "G:\\test1.zip");
		// 把 E 盘 stu 目录下的正则表达式 .zip 压缩文件内的所有文件解压到 E 盘 stu 目录下面
		unZip("G:\\格格2.zip", "G:\\格格22.jpg");
	}

	/**
	 * 
	 * 功能：把 sourceDir 目录下的所有文件进行 zip 格式的压缩，保存为指定 zip 
	 * 文件 create date:2009- 6- 9
	 * author:Administrator
	 * 
	 * @param sourceDir
	 *			E:\\ 我的备份
	 * @param zipFile
	 *			格式： E:\\stu \\zipFile.zip 注意：加入 zipFile 我们传入的字符串值是
	 *			： "E:\\stu \\" 或者 "E:\\stu "
	 *			如果 E 盘已经存在 stu 这个文件夹的话，那么就会出现 java.io.FileNotFoundException:
	 *			E:\stu
	 *			( 拒绝访问。 ) 这个异常，所以要注意正确传参调用本函数哦
	 * 
	 */
	public static void zip(String source, String zipFile) {
		try {
			OutputStream os = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(os);
			File file = new File(source);
			String basePath = null;
			if (file.isDirectory()) {
				basePath = file.getPath();
			} else {
				basePath = file.getParent();
			}
			zipFile(file, basePath, zos);
			zos.closeEntry();
			zos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param source
	 * @param basePath
	 * @param zos
	 */
	private static void zipFile(File source, String basePath, ZipOutputStream zos) {
		try {
			File[] files = new File[0];
			if (source.isDirectory()) {
				files = source.listFiles();
			} else {
				files = new File[1];
				files[0] = source;
			}
			String pathName;
			byte[] buf = new byte[1024];
			int length = 0;
			for (File file : files) {
				if (file.isDirectory()) {
					pathName = file.getPath().substring(basePath.length() + 1) + File.separator;
					zos.putNextEntry(new ZipEntry(pathName));
					zipFile(file, basePath, zos);
				} else {
					pathName = file.getPath().substring(basePath.length() + 1);
					InputStream is = new FileInputStream(file);
					zos.putNextEntry(new ZipEntry(pathName));
					while ((length = is.read(buf)) > 0) {
						zos.write(buf, 0, length);
					}
					is.close();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 解压 zip 文件，注意不能解压 rar 文件哦，只能解压 zip 文件 解压 rar 文件 会出现 java.io.IOException:
	 * Negative
	 * seek offset 异常 create date:2009- 6- 9 author:Administrator
	 * 
	 * @param zipfile
	 *			zip 文件，注意要是正宗的 zip 文件哦，不能是把 rar 的直接改为 zip 这样会出现
	 *			java.io.IOException:
	 *			Negative seek offset 异常
	 * @param destDir
	 * 
	 * @throws IOException
	 */
	public static void unZip(String zipfile, String destDir) {
		destDir = destDir.endsWith("\\") ? destDir : destDir + "\\";
		byte b[] = new byte[1024];
		int length;
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(new File(zipfile));
			Enumeration enumeration = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				File loadFile = new File(destDir + zipEntry.getName());
				if (zipEntry.isDirectory()) {
					// 这段都可以不要，因为每次都貌似从最底层开始遍历的
					loadFile.mkdirs();
				} else {
					if (!loadFile.getParentFile().exists())
						loadFile.getParentFile().mkdirs();
					OutputStream outputStream = new FileOutputStream(loadFile);
					InputStream inputStream = zipFile.getInputStream(zipEntry);
					while ((length = inputStream.read(b)) > 0)
						outputStream.write(b, 0, length);
				}
			}
			System.out.println(" 文件解压成功 ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
