
/**
 * 下载附件
 * @param file_id 附件ID
 */
function commonFile_download(file_id){	
	if(!file_id){
		dvAlert("此文件不存在或已被删除，请联系管理员。");
		return;
	}
	window.location.href = context_path + "/download/attachment/" + file_id;
}

/**
 * 删除附件（或取消上传）
 * @param instance_id 附件上传实例ID
 * @param file_id 文件ID
 */
function commonFile_delete(instance_id, file_id){
	$('#' + instance_id).uploadify('cancel', file_id);
}

/** 
 * 文件名截断显示，最长25个字符，多余的用...代替
 * @param fileName 原文件名
 * @return 截取的文件名
 */
function getFileNameDisplay(fileName){
	// Truncate the filename if it's too long
	if (fileName.length > 25) {
		fileName = fileName.substr(0,25) + '...';
	}
	return fileName;
}

/** 
 * 获取文件大小的表现形式
 * @param fileSize 以B为单位的文件原始大小
 * @return 以B，KB，MB为后缀的字符串表示形式
 */
function getFileSizeDisplay(fileSize){
	var suffix   = 'B';
	if (fileSize > 1000) {
		fileSize = fileSize / 1024;
		suffix   = 'KB';
	}
	if (fileSize > 1000) {
		fileSize = fileSize / 1024;
		suffix   = 'MB';
	}
	var fileSizeParts = fileSize.toString().split('.');
	fileSize = fileSizeParts[0];
	if (fileSizeParts.length > 1) {
		fileSize += '.' + fileSizeParts[1].substr(0,2);
	}
	fileSize += suffix;
	return fileSize;
}

/** 
 * 根据文件类型获取文件图标CSS样式类
 * @param fileType 文件类型后缀，如.doc
 * @return 文件图标CSS样式类名称，如icon-doc
 */
function getIconClass(fileType){
	var fileIconClass = 'icon-default';
	switch (fileType) {
	case '.txt':
	case '.sql':
		fileIconClass = 'icon-txt';
		break;
	case '.jpg':
	case '.gif':
	case '.png':
	case '.bmp':
		fileIconClass = 'icon-image';
		break;
	case '.rar':
	case '.zip':
	case '.jar':
		fileIconClass = 'icon-zip';
		break;
	case '.doc':
	case '.docx':
		fileIconClass = 'icon-doc';
		break;
	case '.xls':
	case '.xlsx':
		fileIconClass = 'icon-xls';
		break;
	case '.ppt':
	case '.pptx':
		fileIconClass = 'icon-ppt';
		break;
	case '.pdf':
		fileIconClass = 'icon-pdf';
		break;
	case '.flv':
	case '.swf':
		fileIconClass = 'icon-flash';
		break;
	case '.chm':
		fileIconClass = 'icon-chm';
		break;
	case '.rm':
	case '.mp4':
	case '.avi':
	case '.wmv':
	case '.rmvb':
		fileIconClass = 'icon-video';
		break;
	default:
		break;
	}
	
	return fileIconClass;
}

