package com.deertt.module.tcommonfile.vo;

import java.util.List;

import com.deertt.frame.base.vo.DvBaseVo;

public abstract class CommonFileAware extends DvBaseVo {
	private static final long serialVersionUID = 1L;

	final public static String COMMON_FILE_LIST_KEY = "commonFileList";

	final public static String COMMON_FILE_NAMESPACE_PREFIX = "common_file_upload";

	private List<TCommonFileVo> files;

	public List<TCommonFileVo> getFiles() {
		return this.files;
	}

	public void setFiles(List<TCommonFileVo> files) {
		this.files = files;
	}

}
