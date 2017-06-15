package com.deertt.frame.base.web.page;

public class DvPageVo {
	
	/**
	 * 默认的分页条数
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * recordCount 表示: 记录总数
	 */
	private int recordCount = 0;

	/**
	 * pageSize 表示: 每页条数
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * currentPage 表示: 当前第几页
	 */
	private int currentPage = 1;
	
	public DvPageVo() {
		recordCount = 0;
		pageSize = DEFAULT_PAGE_SIZE;
		currentPage = 1;
	}
	
	/**
	 * 构造函数:
	 * @param recordCount 记录总数
	 * @param pageSize 每页条数
	 */
	public DvPageVo(int recordCount, int pageSize) {
		this();
		if(recordCount < 0) {
			recordCount = 0;
		}
		if(pageSize < 1) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.recordCount = recordCount;
		this.pageSize = pageSize;
	}
   
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if(currentPage <= 1 || getPageCount() == 0) {
			currentPage = 1;
		} else if(currentPage > getPageCount()) {
			currentPage = getPageCount();
		}
		this.currentPage = currentPage;
	}
	   
	/**
	 * 功能: 获得总页数
	 *
	 * @return
	 */
	public int getPageCount() {
		int pc = recordCount / pageSize + (recordCount % pageSize == 0 ? 0 : 1);
		return pc == 0 ? 1 : pc;
	}
	
	/**
	 * 功能: 获得开始条数
	 *
	 * @return
	 */
	public int getStartIndex() {
		return (currentPage - 1) * pageSize + 1;
	}
}
