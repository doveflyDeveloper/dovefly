package com.deertt.frame.task;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.utils.helper.upload.QiniuUploadHelper;

/**
 * 用户每日贷款利息计算（每天23点执行一次）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class QiniuUploadJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private IUserService userService;

	@Scheduled(cron = "0 0/10 * * * ?")
	public void calculateTodayInterestAmount() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
//		String[] images = {
//				
//		};
//		
//		// 查询有贷款的用户
//		for (int i = 0; i < images.length; i++) {
//			try {
//				String filePath = "/alidata1/static/resource/blog/" + images[i];
//				String fileKey = images[i].substring(images[i].lastIndexOf("/") + 1);
//				System.out.println(filePath + "----" + fileKey + "----" + QiniuBucketConfig.BUCKET_DEERTTBLOG);
//				QiniuUploadHelper.upload(filePath, QiniuBucketConfig.BUCKET_DEERTTBLOG, fileKey);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		List<Map<String, Object>> imageList = userService.queryForMaps("select * from qiniu_image where status = '1'");
		if (imageList != null && imageList.size() > 0) {
			for (int i = 0; i < imageList.size(); i++) {
				try {
					Map<String, Object> obj = imageList.get(i);
					String image = obj.get("image").toString();
					String filePath = "/alidata1/static/resource/" + obj.get("type") + "/" + image;
					String fileKey = image.substring(image.lastIndexOf("/") + 1);
//					System.out.println(filePath + "----" + fileKey + "----" + obj.get("bucket_name"));
					QiniuUploadHelper.upload(filePath, obj.get("bucket_name").toString(), fileKey);
					userService.update("update qiniu_image set status = '2' where id = " + obj.get("id"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
