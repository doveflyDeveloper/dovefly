package com.deertt.module.tcommonfile.vo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class Picture {
	private boolean isDefault;
	private String path;
	private String title;
	
	public Picture() {
		
	}
	
	public Picture(String path) {
		this.path = path;
	}

	public Picture(boolean isDefault, String path, String title) {
		super();
		this.isDefault = isDefault;
		this.path = path;
		this.title = title;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Picture [isDefault=" + isDefault + ", path=" + path	+ ", title=" + title + "]";
	}

}
