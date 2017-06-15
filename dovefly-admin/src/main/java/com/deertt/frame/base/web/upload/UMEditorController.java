package com.deertt.frame.base.web.upload;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.upload.QiniuBucketConfig;
import com.deertt.utils.helper.upload.QiniuUploadHelper;

@Controller
@RequestMapping("/ueditor")
public class UMEditorController {
	
	/**
	 * 富文本编辑器上传图片
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/image/{type}")
	public void image(HttpServletRequest request, HttpServletResponse response, @PathVariable("type") String type) throws Exception {
		String dateStr = DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy/MM/dd");
		String resourcePath = ApplicationConfig.FILE_ROOT_PATH_IMAGE + "/" + type + "/" + dateStr;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//		String type = request.getParameter("type");
		String result = "";
		new File(resourcePath).mkdirs();
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					String originalName = file.getOriginalFilename();
					String ext = originalName.substring(originalName.lastIndexOf(".") + 1);
					String name = UUID.randomUUID().toString() + "." + ext;
					long size = file.getSize();
					String url = dateStr + "/" + name;
					String path = resourcePath + "/" + name;
					file.transferTo(new File(path));
					
					String filePath = path;
					String fileKey = name;
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
					
					result = "{\"name\":\"" + name + "\", " + 
							"\"originalName\": \"" + originalName + "\", " + 
							"\"size\": \"" + size + "\", " + 
							"\"state\": \"SUCCESS\", " + 
							"\"type\": \"" + "." + ext + "\", " + 
							"\"url\": \"" + bucketUrl + fileKey + "\"}";
					break;//UE只上传一张图片
				}
			}
		}
	    result = result.replaceAll("\\\\", "\\\\");
	    response.setContentType("text/html");
	    response.getWriter().print(result);
	}

}
