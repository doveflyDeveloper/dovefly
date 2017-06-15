package com.deertt.frame.base.web.upload;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.module.tcommonfile.service.ITCommonFileService;
import com.deertt.module.tcommonfile.vo.Picture;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.upload.QiniuBucketConfig;
import com.deertt.utils.helper.upload.QiniuUploadHelper;

@Controller
@RequestMapping("/upload")
public class FileUploadController extends DvBaseController {

	@Autowired
	protected ITCommonFileService service;

	/**
	 * Excel文件导入上传
	 * 
	 * @param request
	 * @param attrs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importExcel")
	public String importExcel(MultipartHttpServletRequest request, RedirectAttributes attrs) throws Exception {
		String path = ApplicationConfig.FILE_ROOT_PATH_IMPORT + "/" + DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy/MM/dd");
		String redirectUrl = request.getParameter("redirectUrl");
		Map<String, MultipartFile> fileMap = request.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mFile = entity.getValue();
			String origFileName = mFile.getOriginalFilename();
			
			// 扩展名：
			String extName = "";
			if (origFileName.lastIndexOf(".") >= 0) {
				extName = origFileName.substring(origFileName.lastIndexOf("."));
			}
			String saveFileName = UUID.randomUUID().toString() + extName;

			//FtpApche.uploadFile(path, saveFileName, is);
			File saveFile = new File(path + "/" + saveFileName);
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			if (!saveFile.exists()) {
				saveFile.createNewFile();
			}
			mFile.transferTo(saveFile);
			
			attrs.addFlashAttribute(ApplicationConfig.FILE_IMPORT_KEY,	path + "/" + saveFileName);
		}

		attrs.addFlashAttribute("message", "文件上传成功。");
		return "redirect:/" + redirectUrl;
	}

	/**
	 * 附件上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/attachment", produces = { "application/json;charset=UTF-8" })
	public String attachment(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		String params = "[";
		String dateStr = DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy/MM/dd");
		String path = ApplicationConfig.FILE_ROOT_PATH_ATTACHMENT + "/" + dateStr;
		Map<String, MultipartFile> fileMap = request.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mFile = entity.getValue();
			String origFileName = mFile.getOriginalFilename();
			
			// 扩展名：
			String extName = "";
			if (origFileName.lastIndexOf(".") >= 0) {
				extName = origFileName.substring(origFileName.lastIndexOf("."));
			}
			
			String saveFileName = UUID.randomUUID().toString() + extName;

			params += "{";
			params += "\"file_name\":\"" + mFile.getOriginalFilename() + "\",";
			params += "\"file_url\":\""	+ (dateStr + "/" + saveFileName).replaceAll("\\\\", "/") + "\",";
			params += "\"file_size\":\"" + mFile.getSize() + "\",";
			params += "\"file_type\":\"" + extName + "\"";
			params += "}";

			//FtpApche.uploadFile(path, saveFileName, is);
			File saveFile = new File(path + "/" + saveFileName);
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			if (!saveFile.exists()) {
				saveFile.createNewFile();
			}
			mFile.transferTo(saveFile);

		}
		params += "]";

		return params;
	}

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/image/{type}", produces = { "application/json;charset=UTF-8" })
	public Picture image(MultipartHttpServletRequest request, HttpServletResponse response, @PathVariable("type") String type) throws Exception {
		String dateStr = DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy/MM/dd");
		String path = ApplicationConfig.FILE_ROOT_PATH_IMAGE + "/" + type + "/" + dateStr;
		Map<String, MultipartFile> fileMap = request.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mFile = entity.getValue();
			String origFileName = mFile.getOriginalFilename();
			
			// 扩展名：
			String extName = "";
			if (origFileName.lastIndexOf(".") >= 0) {
				extName = origFileName.substring(origFileName.lastIndexOf("."));
			}
			
			// 生成随机文件名：
			String saveFileName = UUID.randomUUID().toString() + extName;

			//FtpApche.uploadFile(path, saveFileName, is);
			File saveFile = new File(path + "/" + saveFileName);
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			if (!saveFile.exists()) {
				saveFile.createNewFile();
			}
			mFile.transferTo(saveFile);
	
			String filePath = saveFile.getAbsolutePath();
			String fileKey = saveFileName;
			String bucketName = null;
			String bucketUrl = null;
			if ("goods".equals(type)) {
				bucketName = QiniuBucketConfig.BUCKET_DEERTTGOODS;
				bucketUrl = QiniuBucketConfig.BUCKET_DEERTTGOODS_URL;
			} else if ("blog".equals(type)) {
				bucketName = QiniuBucketConfig.BUCKET_DEERTTBLOG;
				bucketUrl = QiniuBucketConfig.BUCKET_DEERTTBLOG_URL;
			} else if ("movie".equals(type)) {
				bucketName = QiniuBucketConfig.BUCKET_DEERTTMOVIE;
				bucketUrl = QiniuBucketConfig.BUCKET_DEERTTMOVIE_URL;
			} else if ("avatar".equals(type)) {
				bucketName = QiniuBucketConfig.BUCKET_DEERTT;
				bucketUrl = QiniuBucketConfig.BUCKET_DEERTT_URL;
			} else if ("banner".equals(type)) {
				bucketName = QiniuBucketConfig.BUCKET_DEERTTBANNER;
				bucketUrl = QiniuBucketConfig.BUCKET_DEERTTBANNER_URL;
			} else {
				bucketName = QiniuBucketConfig.BUCKET_DEERTTADMIN;
				bucketUrl = QiniuBucketConfig.BUCKET_DEERTTADMIN_URL;
			}
			
		//	System.out.println(filePath + "----" + fileKey + "----" + QiniuBucketConfig.BUCKET_DEERTTGOODS);
			QiniuUploadHelper.upload(filePath, bucketName, fileKey);
			return new Picture(bucketUrl + fileKey);
			
//			String s_path = ApplicationConfig.FILE_ROOT_PATH_IMAGE + "/" + type + "/" + "s_" + dateStr;
//			String xs_path = ApplicationConfig.FILE_ROOT_PATH_IMAGE + "/" + type + "/" + "xs_" + dateStr;
//			ImageUtil.fill(path + "/" + saveFileName, s_path + "/" + saveFileName, 220, 220);
//			ImageUtil.fill(path + "/" + saveFileName, xs_path + "/" + saveFileName, 90, 90);
//			return new Picture(dateStr + "/" + saveFileName);
		}
		return null;
	}

}
