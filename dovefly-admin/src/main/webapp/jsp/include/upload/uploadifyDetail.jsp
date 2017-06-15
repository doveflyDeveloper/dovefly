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
	function initOldFiles() {
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
		createItem(file);
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
	function createItem(file){
			// Create the file data object
			var itemData = {
				'fileID'		 : file.id,
				'fileUrl'		: file.url,
				'fileType'	   : file.type,
				'fileFullName'   : file.name,
				'fileOldSize'	: file.size,
				'fileIconClass'  : getIconClass(file.type),
				'fileName'	   : getFileNameDisplay(file.name),
				'fileSize'	   : getFileSizeDisplay(file.size)
			};
			var itemHTML = '<div id="' + itemData.fileID + '" class="uploadify-queue-item">' +
				'<div class="cancel">' +
					'<a class="download" title="下载" href="javascript:commonFile_download(\'' + itemData.fileID + '\')">V</a>' +
				'</div>' +
				'<span class="fileName ' + itemData.fileIconClass + '">' + itemData.fileName + ' (' + itemData.fileSize + ')</span><span class="data"></span>' +
				'</div>';
			
			// Add the file item to the queue
			$('#<%=common_file_upload%>_queue').append(itemHTML);

	}
	
	$(document).ready(function() {
		//初始化原有的已上传文件
		initOldFiles();
	});  
</script>
<div class="common_file_div">
	<div id="<%=common_file_upload%>_queue">
	</div>
</div>