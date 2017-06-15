<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.deertt.module.tcommonfile.vo.CommonFileAware" %>
<%@ page import="com.deertt.module.tcommonfile.vo.TCommonFileVo" %>
<%@ page import="com.deertt.module.tcommonfile.util.ITCommonFileConstants" %>
<%
	String fileListName = request.getParameter("fileListName")==null ? CommonFileAware.COMMON_FILE_LIST_KEY : request.getParameter("fileListName");
	String common_file_upload = request.getParameter("fileUploadName")==null ? CommonFileAware.COMMON_FILE_NAMESPACE_PREFIX : request.getParameter("fileUploadName");
	
	//取出附件列表
	List<TCommonFileVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(fileListName) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(fileListName);  //赋值给resultList
	}
	
%>
<!-- 在dvGlobal.jsp文件内已经引入过
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/uploadify-3.2/uploadify.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/uploadify-3.2/jquery.uploadify-zh.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/uploadify-3.2/uploadify-common.js"></script>
 -->
<script type="text/javascript">
	
	/** 
	 * 初始化原有的已上传的文件信息
	 * @param instance 上传控件实例
	 */
	function initOldFiles(instance){
		var file = {};
	<%
		if(lResult != null && lResult.size() > 0){
			for(int i = 0; i < lResult.size(); i++){
				TCommonFileVo file = lResult.get(i);
	%>
		file = {
			id   : '<%=file.getFile_id()%>',
			name : '<%=file.getFile_name()%>',
			url  : '<%=file.getFile_url()%>',
			size : '<%=file.getFile_size()%>',
			type : '<%=file.getFile_type()%>'
		};
		
		instance.queueData.queueLength += 1;
		instance.queueData.queueSize += file.size;
		instance.queueData.queueBytesUploaded += file.size;
		instance.queueData.files[file.id] = file;
		createItem(instance, file);
	<%		
			}
		}
	%>
	}
	
	/** 
	 * 创建已上传文件的DOM元素
	 * @param instance 上传控件实例
	 * @param file 已上传的文件
	 */
	function createItem(instance, file){
			// Create the file data object
			var itemData = {
				'fileID'		 : file.id,
				'fileUrl'		: file.url,
				'fileType'	   : file.type,
				'fileFullName'   : file.name,
				'fileOldSize'	: file.size,
				'instanceID'	 : instance.settings.id,
				'fileIconClass'  : getIconClass(file.type),
				'fileName'	   : getFileNameDisplay(file.name),
				'fileSize'	   : getFileSizeDisplay(file.size)
			};
			var itemHTML = '<div id="' + itemData.fileID + '" class="uploadify-queue-item">' +
				'<div class="cancel">' + 
					'<a class="del" title="删除" href="javascript:commonFile_delete(\'' + itemData.instanceID + '\', \'' + itemData.fileID + '\')">X</a>' +
				'</div>' +
				'<span class="fileName ' + itemData.fileIconClass + '">' + itemData.fileName + ' (' + itemData.fileSize + ')</span><span class="data"> - 已上传</span>' +
				'<span class="form-data">' +
					'<input type="hidden" name="<%=common_file_upload%>.file_id" value="' + itemData.fileID + '"/>' +
					'<input type="hidden" name="<%=common_file_upload%>.file_name" value="' + itemData.fileFullName + '"/>' +
					'<input type="hidden" name="<%=common_file_upload%>.file_url" value="' + itemData.fileUrl + '"/>' +
					'<input type="hidden" name="<%=common_file_upload%>.file_type" value="' + itemData.fileType + '"/>' +
					'<input type="hidden" name="<%=common_file_upload%>.file_size" value="' + itemData.fileOldSize + '"/>' +
				'</span>' +
				'</div>';
			
			// Add the file item to the queue
			$('#' + instance.settings.queueID).append(itemHTML);
	}
	$(document).ready(function() {
		$("#<%=common_file_upload%>").uploadify({
			'uploader'		: '<%=request.getContextPath()%>/upload/attachment',
			'queueID'		 : '<%=common_file_upload%>_queue',
			'width'		   : '60',
			'height'		  : '20',
			//'buttonClass'	 : 'button',
			'swf'			 : '<%=request.getContextPath()%>/resources/js/uploadify-3.2/uploadify.swf',
			'fileSizeLimit'   : '10MB',
			'uploadLimit'	 : 10,
			//'formData'		: {'file_type' : file_type},
			'itemTemplate'	: '<div id="\$\{fileID}" class="uploadify-queue-item">\
									<div class="cancel">\
										<a class="del" title="删除" href="javascript:commonFile_delete(\'\$\{instanceID}\', \'\$\{fileID}\')">X</a> \
									</div>\
									<span class="fileName">\$\{fileName} (\$\{fileSize})</span><span class="data"></span>\
									<div class="uploadify-progress">\
										<div class="uploadify-progress-bar"><!--Progress Bar--></div>\
									</div>\
								</div>',
			'removeCompleted' : false,
			'removeTimeout'   : 1,
			
			'onSelect' : function(file) {
				//设置文件类型图标
				$("#" + file.id).find(".fileName").addClass(getIconClass(file.type));
			},
			
			'onUploadSuccess' : function(file, data, response) {//文件上传成功时触发
				//回写文件隐藏域信息
				data = eval(data);
				var form_data ='<span class="form-data">' +
				'<input type="hidden" name="<%=common_file_upload%>.file_id" value=""/>' +
				'<input type="hidden" name="<%=common_file_upload%>.file_name" value="' + data[0].file_name + '"/>' +
				'<input type="hidden" name="<%=common_file_upload%>.file_url" value="' + data[0].file_url + '"/>' +
				'<input type="hidden" name="<%=common_file_upload%>.file_type" value="' + data[0].file_type + '"/>' +
				'<input type="hidden" name="<%=common_file_upload%>.file_size" value="' + data[0].file_size + '"/>' +
				'</span>';
				$("#" + file.id).append(form_data);
			},
			
			'onUploadError' : function(file, errorCode, errorMsg, errorString) {//文件上传失败时触发
				alert('文件“' + file.name + '”上传失败。失败原因为: ' + errorString);
			},
			
			'onFallback' : function() {
				alert("没有兼容的flash");
			},
			
			'onInit' : function(instance) {
				//初始化原有的已上传文件
				initOldFiles(instance);
			}

			// Put your options here
		});
	});  
</script>
<div class="common_file_div">
	<input type="file" name="<%=common_file_upload%>" id="<%=common_file_upload%>"/>
	<div id="<%=common_file_upload%>_queue">
	</div>
</div>