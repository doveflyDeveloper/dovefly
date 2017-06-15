package com.deertt.frame.base.web.upload;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.module.tcommonfile.service.ITCommonFileService;
import com.deertt.module.tcommonfile.vo.TCommonFileVo;
import com.deertt.utils.helper.file.ftp.FtpApche;

@Controller
@RequestMapping("/download")
public class FileDownloadController extends DvBaseController {
	
	@Autowired
	protected ITCommonFileService service;
	
	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/attachment/{id}")
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_ID) String id) throws Exception {
		TCommonFileVo vo = service.find(id);
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(vo.getFile_name(), "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
//		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
//		String templateFileName = projectBasePath + "files/upload/attachments/" + vo.getFile_url();
		String templateFileName = ApplicationConfig.FILE_ROOT_PATH_ATTACHMENT + "/" + vo.getFile_url();
//		String templateFileName = vo.getFile_url();
		OutputStream outputStream = response.getOutputStream();   
		outputStream.write(FileUtils.readFileToByteArray(new File(templateFileName)));  
		outputStream.flush();
		outputStream.close(); 
		
		//String templateFileName = "D:/" + vo.getFile_url();
//		OutputStream outputStream = response.getOutputStream();   
//		outputStream.write(FileUtils.readFileToByteArray(downloadFTP(id)));  
//		outputStream.flush();
//		outputStream.close(); 
		
	}
	
	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @throws Exception
	 */
	public File downloadFTP(String id) throws Exception {
		TCommonFileVo vo = service.find(id); 
		String remotePath = vo.getFile_url().substring(0, vo.getFile_url().lastIndexOf("/") + 1);
		String remoteFileName = vo.getFile_url().substring(vo.getFile_url().lastIndexOf("/") + 1);
		
		boolean flag = FtpApche.downFile("127.0.0.1", 21, "root", "root", remotePath, remoteFileName, "D:/");
		System.out.println(flag);
		return new File("D:/" + remoteFileName);
	}

}
