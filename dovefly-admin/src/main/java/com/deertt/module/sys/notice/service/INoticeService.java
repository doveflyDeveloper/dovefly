package com.deertt.module.sys.notice.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.sys.notice.dao.INoticeDao;
import com.deertt.module.sys.notice.vo.NoticeVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface INoticeService extends IDvBaseService<INoticeDao, NoticeVo, Integer> {

	/**
	 * 发布公告
	 * @param id
	 * @return
	 */
	public int issue(Integer id);

	/**
	 * 取消发布公告
	 * @param id
	 * @return
	 */
	public int cancelIssue(Integer id);

	/**
	 * 阅读次数加一
	 * @param id
	 */
	public void increaseReadTimes(Integer id);

}
