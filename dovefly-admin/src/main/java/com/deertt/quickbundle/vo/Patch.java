package com.deertt.quickbundle.vo;

public class Patch {
	//#补丁包用户名
	private String user_name = "FENGCHUNMING-";

	//#是否清除上次生成的source和cncpur文件夹
	private boolean is_clear_old = true;

	//#生成的补丁包拷贝到的目标路径，即本地SVN路径
	private String svn_patches_dir = "E:";

	//#是否拷贝生成的补丁包到本地SVN路径 
	private boolean is_copy_patch = true;

	//#需要打包的文件列表
	private String document = "E:/patch/document.txt";
	
	//#需要打包的文件列表
	private String files_list_str = "";

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public boolean isIs_clear_old() {
		return is_clear_old;
	}

	public void setIs_clear_old(boolean is_clear_old) {
		this.is_clear_old = is_clear_old;
	}

	public String getSvn_patches_dir() {
		return svn_patches_dir;
	}

	public void setSvn_patches_dir(String svn_patches_dir) {
		this.svn_patches_dir = svn_patches_dir;
	}

	public boolean isIs_copy_patch() {
		return is_copy_patch;
	}

	public void setIs_copy_patch(boolean is_copy_patch) {
		this.is_copy_patch = is_copy_patch;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getFiles_list_str() {
		return files_list_str;
	}

	public void setFiles_list_str(String files_list_str) {
		this.files_list_str = files_list_str;
	}
	
	

}
