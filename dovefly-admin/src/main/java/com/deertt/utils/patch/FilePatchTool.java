package com.deertt.utils.patch;

import com.deertt.utils.helper.string.DvStringHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.file.DvFileHelper;

/**
 * 补丁包打包程序
 * @author fengcm
 *
 */
public class FilePatchTool {
	
	/**
	 * 工程根路径
	 */
	public static final String project_path = getProjectPath();
	
	/**
	 * 工程名
	 */
	public static final String project_name = getProjectName();
	
	/**
	 * 上下文根目录名
	 */
	public static final String context_name = getContextName();
	
	/**
	 * 打包文件列表文件路径
	 */
	public static final String document_path = project_path + "/patch/document.txt";
	
	/**
	 *  补丁文件存放处
	 */
	public static final String default_dir_root = project_path + "/patch/";

	/**
	 *  生成的源文件存放处
	 */
	public static final String dir_generated_source = project_path + "/patch/source/";

	/**
	 *  生成的目标文件存放处
	 */
	public static final String dir_generated_target = project_path + "/patch/" + context_name + "/";

	/**
	 *  需要额外打入补丁包的文件夹
	 */
	public static final String dir_to_patch = "D:/patch/to_patch/";
	
	/**
	 *  需要打入补丁包的文件所在路径
	 */
	public static final String zip_name = context_name + ".zip";

	/**
	 *  classes所在的路径
	 */
	public static final String web_inf_classes = "/" + context_name + "/WEB-INF/classes/";
	
	/**
	 *  补丁包用户名
	 */
	public static final String user_name = "FENGCM-";

	/**
	 *  生成的补丁包拷贝到的目标路径，即本地SVN路径（用以直接上传SVN）
	 */
	public static final String svn_patches_dir = project_path + "/patch/svn/";;

	/**
	 *  是否拷贝生成的补丁包到本地SVN路径
	 */
	public static boolean is_copy_patch = true;

	/**
	 * 过滤不打包的文件，即使在打包列表内
	 */
	public static String[] files_cannot_be_patched = { "db.xml", "ranmin.xml", "web.xml" };

	public static void main(String[] args) throws Exception {
		patch();
		//getProjectName();
	}
	
	public static boolean patch() throws Exception {
		// 清除历史文件
		deleteOldFiles();

		// 拷贝文件
		doService();

		// 如果文件挺多，生成编译后文件补丁包，便于部署
		log("\n【创建补丁包】");
		createZipFile(new File[] { new File(dir_generated_target) }, zip_name);

		// 创建用于放到svn的补丁包
		log("\n【搬迁补丁包到SVN目录】");
		File[] files = new File[] { new File(dir_generated_source),
				new File(dir_generated_target), new File(document_path) };
		String zipFile = createZipFile(files, getGeneratedFileName());

		// 拷贝补丁包到本地SVN路径
		copyPatch(zipFile);

		return true;
	}
	
	/**
	 * 获取当前工程的绝对路径，
	 * 根据当前类路径（D:/IDE/workspace/deertt-admin/WebContent/WEB-INF/classes）
	 * 必然包含WEB-INF目录，则其父目录的父目录即为工程根路径
	 * @return
	 */
	public static String getProjectPath() {
		String path = ClassLoader.getSystemResource("").getPath();
		path = path.substring(1, path.indexOf("/WEB-INF"));
		path = path.substring(0, path.lastIndexOf("/"));
		log("\n【工程根路径】：" + path);
		return path;
	}
	
	/**
	 * 获取当前工程的绝对路径，
	 * 根据当前类路径（D:/IDE/workspace/deertt-admin/WebContent/WEB-INF/classes）
	 * 必然包含WEB-INF目录，则其父目录的父目录即为工程根路径
	 * @return
	 */
	public static String getProjectName() {
		String path = ClassLoader.getSystemResource("").getPath();
		path = path.substring(1, path.indexOf("/WEB-INF"));
		path = path.substring(0, path.lastIndexOf("/"));
		path = path.substring(path.lastIndexOf("/") + 1);
		log("\n【工程根目录】：" + path);
		return path;
	}
	
	/**
	 * 获取当前工程的上下文根目录名称
	 * 根据当前类路径（D:/IDE/workspace/deertt-admin/WebContent/WEB-INF/classes）
	 * 必然包含WEB-INF目录，则其父目录即为工程根路径
	 * @return
	 */
	public static String getContextName() {
		String path = ClassLoader.getSystemResource("").getPath();
		path = path.substring(1, path.indexOf("/WEB-INF"));
		path = path.substring(path.lastIndexOf("/") + 1);
		log("\n【工程上下文】：" + path);
		return path;
	}

	/**
	 * 删除已存在的source和cncpur文件夹
	 */
	public static void deleteOldFiles() {
		log("\n【清除已存在的文件夹】：");
		log("\t删除文件夹：" + dir_generated_source);
		deleteFile(new File(dir_generated_source));
		log("\t删除文件夹：" + dir_generated_target);
		deleteFile(new File(dir_generated_target));
	}
	
	/**
	 * 递归删除文件夹及其所有子文件
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		if(file != null && file.exists()){
			if (file.isDirectory()) {
				File[] childrens = file.listFiles();
				for (File file2 : childrens) {
					deleteFile(file2);
				}
			} else {
				file.delete();
			}
		}
		return true;
	}
	
	/**
	 * 拷贝源文件和目标文件到打包目录
	 * @return
	 * @throws Exception
	 */
	public static boolean doService() throws Exception {
		
		// 工程路径
		List<String> filePathList = getFilePaths(document_path);

		int sourceFileSucc = 0;
		int sourceFileFail = 0;
		boolean tempSourceFileFlag = true;

		log("\n【复制源文件】");
		// 拷贝源文件
		for (String path : filePathList) {
			if (path.endsWith("/*")) { // 目录文件
				String parentDir = path.substring(0, path.length() - "/*".length());// 源文件上级目录
				tempSourceFileFlag = copyAllFilesInThisDir(parentDir, project_path, dir_generated_source);
			} else {// 非目录文件
				tempSourceFileFlag = copyFiles(path, project_path, dir_generated_source, true);
			}
			if (tempSourceFileFlag) {
				sourceFileSucc++;
			} else {
				sourceFileFail++;
			}
		}

		log("\t成功复制个数：" + sourceFileSucc + " , 失败复制个数：" + sourceFileFail);

		// 拷贝编译文件
		log("\n【复制编译文件】");
		List<String> newFileList = null;
		int su = 0;
		int fa = 0;
		int updateSu = 0;
		int updatefa = 0;
		try {
			boolean fileFailFlag = false;
			for (String path : filePathList) {
				fileFailFlag = false;
				newFileList = null;
				if (path.endsWith("/*")) { // 目录文件
					newFileList = getAllDestFilePathInThisDir(path, project_path);
				} else {// 非目录文件
					newFileList = getDestFilePath(path, project_path);// 得到新的路径
				}
				
				if (newFileList != null) {
					for (int j = 0; j < newFileList.size(); j++) {
						boolean temp = copyCompareFiles(newFileList.get(j), project_path, default_dir_root, true);
						if (temp) {
							su += 1;
						} else {
							fa += 1;
							fileFailFlag = true;
						}
					}
					if (fileFailFlag) {
						updatefa += 1;
					} else {
						updateSu += 1;
					}
				} else {
					updatefa += 1;
				}
			}
			log("\t成功文件个数：" + updateSu + " , 失败文件个数：" + updatefa);
			log("\t成功复制个数：" + su + " , 失败复制个数：" + fa);
		} catch (Exception e) {
			log("错误时成功文件行数：" + updateSu);
			log("错误时失败文件行数：" + updatefa);
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 
	 * 读取document.txt里的要打包的文件路径
	 * @param cocument_path
	 * @return
	 * @throws Exception
	 */
	public static List<String> getFilePaths(String cocument_path) throws Exception {
		List<String> list = new ArrayList<String>();
		File file = new File(cocument_path);

		if (!file.exists()) {
			log("WARN: 打包列表文件：" + cocument_path + "不存在！请首先创建该文件！");
			return list;
		}

		if (file.isFile()) {
			log("\n【读取文件列表】：" + cocument_path);
		}

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (StringUtils.isBlank(line) || line.startsWith("#")) {//空行和注释行忽略
				continue;
			}
			if(isIgnored(line)){				
				continue;
			}
			
			if (!line.startsWith("/")) {
				log("WARN: 注释必须以#开始。无效的文件路径或注释: " + line);
				
			}
			log("\t" + line);
			list.add(line);
		}
		reader.close();
		return list;
	}

	/**
	 * 检查文件是否在忽略打包的序列内
	 * @param line
	 * @return
	 */
	public static boolean isIgnored(String line) {
		boolean ignored = false;
		for (int i = 0; i < files_cannot_be_patched.length; i++) {
			if (line.endsWith(files_cannot_be_patched[i])) {
				log("WARN: " + files_cannot_be_patched[i] + "不能放入补丁包！该文件已被过滤！");
				ignored = true;
				break;
			}
		}
		return ignored;
	}
	
	/**
	 * 
	 * 递归拷贝目录下（包括子目录）所有的文件到目标目录，过滤掉SVN文件
	 * @param file 源文件路径，形如：/src/test/*，最后两个字符的/*
	 * @param srcDir 源文件位置
	 * @param destDir 目标位置
	 * @return
	 */
	public static boolean copyAllFilesInThisDir(String file, String srcDir, String destDir) {
		String srcDirName = srcDir + file;
		boolean tempSourceFileFlag = true;
		File srcDirFile = new File(srcDirName);
		// 判断源文件是否存在
		if (!srcDirFile.exists()) {
			log("WARN: 源文件目录：" + srcDirName + "不存在！");
			return false;
		} else if (!srcDirFile.isDirectory()) {
			log("WARN: 源文件目录：" + srcDirName + "不是一个目录！");
			return false;
		}
		File[] listFiles = srcDirFile.listFiles();
		String tempAbsolutePath = null;
		String tempNewFilePath = null;
		for (int i = 0; i < listFiles.length; i++) {
			tempAbsolutePath = listFiles[i].getAbsolutePath();
			tempNewFilePath = tempAbsolutePath.substring(tempAbsolutePath.indexOf(srcDir) + srcDir.length() + 1, tempAbsolutePath.length());
			// 判断是否是svn文件
			if (!tempAbsolutePath.endsWith(".svn")) {
				if (listFiles[i].isDirectory()) {// 是目录循环这个方法
					tempSourceFileFlag = copyAllFilesInThisDir(tempNewFilePath, srcDir, destDir);
				} else {// 是文件，直接拷贝
					tempSourceFileFlag = copyFiles(tempNewFilePath, srcDir, destDir, true);
				}
				if (!tempSourceFileFlag) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 拷贝文件
	 * @param file 文件名
	 * @param srcDir 原目录
	 * @param destDir 目标目录
	 * @param overlay 如果目标文件已经存在，是否覆盖
	 * @return
	 */
	public static boolean copyFiles(String file, String srcDir, String destDir, boolean overlay) {
		String srcFileName = srcDir + file;
		String destFileName = destDir + file;

		File srcFile = new File(srcFileName);
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			log("WARN: 复制文件失败，源文件：" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			log("WARN: 复制文件失败，源文件：" + srcFileName + "不是一个文件！");
			return false;
		}

		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				if (!destFile.delete()) {
					log("WARN: 替换文件" + destFile + "失败！");
					return false;
				}
			} else {
				log("WARN: 目标文件" + destFileName + "已存在！");
				return false;
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				if (!destFile.getParentFile().mkdirs()) {
					log("WARN: 复制文件失败：创建" + destFile.getParent() + "失败！");
					return false;
				}
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			// log("复制单个文件" + srcFileName + "至" + destFileName
			// + "成功！");
			return true;

		} catch (FileNotFoundException e) {
			log("WARN: 复制文件失败：" + e.getMessage());
			return false;
		} catch (IOException e) {
			log("WARN: 复制文件失败：" + e.getMessage());
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 递归获得原目录下（包括子目录）所有的文件编译后所在目标路径的所有文件路径
	 * @param filePath 形如：/src/test/*，最后两个字符的/*
	 * @param srcDir 当前工程路径，即基路经
	 * @return
	 */
	public static List<String> getAllDestFilePathInThisDir(String filePath, String srcDir) {
		List<String> returnList = new ArrayList<String>();
		String newFilePath = "";
		String firstRoot = "";
		String secondRoot = "";
		String tempStr = "";
		String srcDirName = srcDir + filePath;
		srcDirName = srcDirName.substring(0, srcDirName.length() - 2);// 源文件目录
		//boolean tempSourceFileFlag = true;
		File srcDirFile = new File(srcDirName);
		// 判断源文件是否存在
		if (!srcDirFile.exists()) {
			log("WARN: 源文件目录：" + srcDirName + "不存在！");
			return null;
		} else if (!srcDirFile.isDirectory()) {
			log("WARN: 源文件目录：" + srcDirName + "不是一个目录！");
			return null;
		}
		File[] listFiles = srcDirFile.listFiles();
		String tempAbsolutePath = null;
		String tempNewFilePath = null;
		List<String> tempList = null;
		if (listFiles != null) {
			for (int i = 0; i < listFiles.length; i++) {
				tempAbsolutePath = listFiles[i].getAbsolutePath();
				tempNewFilePath = tempAbsolutePath.substring(tempAbsolutePath.indexOf(srcDir) + srcDir.length() + 1, tempAbsolutePath.length());
				// String isSvn =
				// tempAbsolutePath.substring(tempAbsolutePath.lastIndexOf("\\")+1,tempAbsolutePath.length());
				// 判断是否是svn文件
				if (!tempAbsolutePath.substring(tempAbsolutePath.lastIndexOf("\\") + 1, tempAbsolutePath.length()).equals(".svn")) {
					if (listFiles[i].isDirectory()) {// 是目录循环这个方法
						tempList = getAllDestFilePathInThisDir(tempNewFilePath + "\\*", srcDir);
						if (tempList != null) {
							for (int j = 0; j < tempList.size(); j++) {
								returnList.add(tempList.get(j));
							}
						}
					} else {// 是文件，直接拷贝
						String fileName = listFiles[i].getName();// 文件名，带后缀名
						if (fileName.indexOf("quartz.properties")!=-1) {
							log("quartz.properties");
						}
						String fileSortName = fileName.substring(0, fileName.lastIndexOf('.'));// 文件名，不带后缀名
						String fileType = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
						newFilePath = listFiles[i].getAbsolutePath();
						if ("java".equals(fileType)) {// 如果是java文件，去读取编译后的class文件
							tempStr = tempNewFilePath.substring(1, tempNewFilePath.length());
							firstRoot = tempNewFilePath.substring(0, tempStr.indexOf('\\') + 2);
							secondRoot = tempNewFilePath.substring(firstRoot.length(), tempNewFilePath.length());

							String newFileDir = secondRoot.substring(0, secondRoot.length() - fileName.length() - 1);
							// log("newFileDir：" + newFileDir);
							// newFileDir =
							// newFileDir.substring(0,newFileDir.indexOf("\\src\\"))+"\\cncpur\\WEB-INF\\classes\\"+newFileDir.substring(newFileDir.indexOf("\\src\\")+5,newFileDir.length());
							newFileDir = srcDir + web_inf_classes + newFileDir;

							// log("newFileDir：" + newFileDir);

							File newDir = new File(newFileDir);
							if (!newDir.isDirectory()) {
								log("WARN: java源文件：" + fileName + "编译文件夹：" + newDir + "不存在！");
							} else {
								File[] fileList = newDir.listFiles();
								if (fileList != null) {
									String tempFileAllName = null;
									String tempFileShortName = null;
									for (int m = 0; m < fileList.length; m++) {
										if (!fileList[m].isDirectory()) {
											tempFileAllName = fileList[m].getName();// 文件名，带后缀名
											tempFileShortName = tempFileAllName.substring(0,
													tempFileAllName.lastIndexOf('.'));// 文件名，不带后缀名
											if (tempFileShortName.indexOf(fileSortName) != -1
													|| tempFileShortName.indexOf(fileSortName + "$") != -1) {
												returnList.add(newFileDir + "\\" + tempFileAllName);
											}
										}
									}
								}
							}
						} else {// 如果不是java文件，则直接拷贝
							returnList.add(newFilePath);
						}
					}
				}
			}
		}
		return returnList;
	}

	/**
	 * 拷贝补丁压缩包到SVN路径下
	 * @param zipFile
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static void copyPatch(String zipFile) throws UnsupportedEncodingException, Exception {
		if (is_copy_patch) {
			String svnPatchDir = svn_patches_dir;
			svnPatchDir += !DvStringHelper.isEmpty(svnPatchDir) && !svnPatchDir.endsWith("/") ? "/" : "";
			// svnPatchDir = new String(svnPatchDir.getBytes("ISO8859-1"),
			// "GBK");
			File file = new File(svnPatchDir);
			if (!file.exists()) {
				log("WARN: 本地SVN目录:" + svnPatchDir + "不存在，无法进行拷贝！");
			} else {
				String svnFullPath = svnPatchDir + getGeneratedFileName();
				log("\t拷贝补丁包到SVN目录：" + svnFullPath);
				DvFileHelper.copyFile(zipFile, svnFullPath);
			}
		}
	}


	/**
	 * 生成补丁包包名
	 * 
	 * @return
	 */
	public static String getGeneratedFileName() {
		String path = document_path;
		int index = path.lastIndexOf("/");
		String s = path.substring(index + 1, path.length() - 4);
		s = s.replaceAll("修改记录", "");

		if (s.toLowerCase().indexOf("document") != -1) {
			s = "";
		}
		String comment = getComment(path);
		String property = user_name + s;

		return "PATCH-" + DvDateHelper.getJoinedSysDateTime()
				+ (DvStringHelper.isEmpty(property) ? "" : ("-" + property))
				+ comment + ".zip";
	}

	/**
	 * 获取包名注释
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getComment(String filePath) {
		String comment = "";
		try {
			FileReader fr = new FileReader(filePath);
			String line = "";
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#comment=")) {
					comment = line.substring("#comment=".length()).trim();
					break;
				}
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return comment;
	}

	/**
	 * 创建补丁包压缩包，包含files里路径下的所有文件
	 * @param files
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String createZipFile(File[] files,String fileName) throws IOException {

		// 补丁包文件名
		String patchName = default_dir_root + fileName;
		File zipFile = new File(patchName);
		log("\t生成补丁包位置：" + patchName);

		// 删除已存在的补丁包
		if (zipFile.exists()) {
			zipFile.delete();
		}

		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));// ,
																						// "GBK");
			for (int i = 0; i < files.length; i++) {				
				fileZip(zos, files[i], "");
			}

			File toPatch = new File(dir_to_patch);
			if (toPatch != null && toPatch.exists() && toPatch.isDirectory()) {
				String[] toPatchFiles = toPatch.list();
				for (int i = 0; i < toPatchFiles.length; i++) {
					fileZip(zos, new File(dir_to_patch + toPatchFiles[i]), "");
				}
			}

			zos.closeEntry();
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return patchName;
	}

	/**
	 * 创建压缩文件
	 * 
	 * @param zos
	 * @param f
	 * @param base
	 * @throws Exception
	 */
	public static void fileZip(ZipOutputStream zos, File f, String base) throws Exception {
		// 如果传入的是目录
		if (f.isDirectory()) {
			File[] fl = f.listFiles();

			if (base == null) {
				base = "";
			} else if (base.length() == 0) {
				base = f.getName() + "/";
			} else {
				base = base + f.getName() + "/";
			}
			for (int i = 0; i < fl.length; i++) {
				;
				fileZip(zos, fl[i], base);
			}
		} else if (f.isFile()) {
			zos.putNextEntry(new ZipEntry(base + f.getName()));
			// zos.closeEntry();
			// zos.close();
			FileInputStream in = new FileInputStream(f);
			byte[] bb = new byte[2048];
			int aa = 0;
			while ((aa = in.read(bb)) != -1) {
				zos.write(bb, 0, aa);
			}
			in.close();
		}
	}
	

	/**
	 * 获得当前文件对应的目标文件列表（如：java ——> class等）
	 * @param filePath
	 * @param project_path
	 * @return
	 */
	public static List<String> getDestFilePath(String filePath, String project_path) {
		List<String> list = new ArrayList<String>();
		
		String srcFullPath = project_path + filePath;
		
		if (filePath.endsWith(".java")) {
			String destFullPath = srcFullPath.replace("/src/", "/" + context_name + "/WEB-INF/classes/").replace(".java", ".class");
			File file = new File(destFullPath);
			if (!file.exists()) {
				log("WARN:" + destFullPath + "不存在！");
				return list;
			} else if (!file.isFile()) {
				log("WARN:" + destFullPath + "不是一个文件！");
				return list;
			}
			String destParentFullPath = file.getParent();
			
			String shortName = file.getName().substring(0, file.getName().lastIndexOf('.'));// 文件名，不带后缀名
			
			File[] fileList = file.getParentFile().listFiles();
			for (File file2 : fileList) {
				if (file2.isFile()) {
					String currFileName = file2.getName();// 文件名，带后缀名
					String currFileShortName = currFileName.substring(0, currFileName.lastIndexOf("."));// 文件名，带后缀名
					//当前类及其内部类，如：TDictionaryTypeDao$2.class
					if (currFileShortName.equals(shortName) || currFileShortName.startsWith(shortName + "$")) {
						list.add(destParentFullPath + "/" + currFileName);
					}
				}
			}
		} else {// 如果不是java文件，则直接拷贝
			String destFullPath = srcFullPath.replace("/src/", "/" + context_name + "/WEB-INF/classes/");
			list.add(destFullPath);
		}
		return list;
	}

	public static boolean copyCompareFiles(String file, String srcDir, String destDir, boolean overlay) {
		String srcFileName = file;
		String destFileName = "";
		try {
			destFileName = destDir + file.substring(srcDir.length(), file.length());
		} catch (Exception e) {
			log("file=" + file);
			log("srcDir=" + srcDir);
			e.printStackTrace();
		}
		File srcFile = new File(srcFileName);
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			log("WARN: 复制文件失败，源文件：" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			log("WARN: 复制文件失败，源文件：" + srcFileName + "不是一个文件！");
			return false;
		}

		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				if (!destFile.delete()) {
					log("WARN: 替换文件" + destFile + "失败！");
					return false;
				}
			} else {
				log("WARN: 目标文件" + destFileName + "已存在！");
				return false;
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				if (!destFile.getParentFile().mkdirs()) {
					log("WARN: 复制文件失败：创建" + destFile.getParent() + "失败！");
					return false;
				}
			}
		}
		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			// log("复制单个文件" + srcFileName + "至" + destFileName+ "成功！");
			return true;

		} catch (FileNotFoundException e) {
			log("WARN: 复制文件失败：" + e.getMessage());
			return false;
		} catch (IOException e) {
			log("WARN: 复制文件失败：" + e.getMessage());
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void log(String msg) {
		System.out.println(msg);
	}
	
}
