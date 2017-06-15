package com.deertt.module.sys.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sys.notice.dao.INoticeDao;
import com.deertt.module.sys.notice.service.INoticeService;
import com.deertt.module.sys.notice.util.INoticeConstants;
import com.deertt.module.sys.notice.vo.NoticeVo;
import com.deertt.module.tcommonfile.service.ITCommonFileService;
import com.deertt.utils.helper.DvVoHelper;
import com.deertt.utils.helper.date.DvDateHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class NoticeService extends DvBaseService<INoticeDao, NoticeVo, Integer> implements INoticeService, INoticeConstants {
	
	@Autowired
	public ITCommonFileService fileService;
	
	@Transactional
	public int insert(NoticeVo vo) {
		int sum = super.insert(vo);
		fileService.insertUpdateBatch(vo.getFiles(), TABLE_NAME, TABLE_PRIMARY_KEY, vo.getId().toString());
		return sum;
	}

	@Transactional
	public int insert(NoticeVo[] vos) {
		int sum = super.insert(vos);
		for (NoticeVo vo : vos) {
			fileService.insertUpdateBatch(vo.getFiles(), TABLE_NAME, TABLE_PRIMARY_KEY, vo.getId().toString());
		}
		return sum;
	}

	@Transactional
	public NoticeVo find(Integer id) {
		NoticeVo vo = super.find(id);
		vo.setFiles(fileService.queryFiles(TABLE_NAME, TABLE_PRIMARY_KEY, vo.getId().toString()));
		return vo;
	}

	@Transactional
	public int update(NoticeVo vo) {
		int sum = super.update(vo);
		fileService.insertUpdateBatch(vo.getFiles(), TABLE_NAME, TABLE_PRIMARY_KEY, vo.getId().toString());
		return sum;
	}

	@Transactional
	public int update(NoticeVo[] vos) {
		int sum = super.update(vos);
		for (NoticeVo vo : vos) {
			fileService.insertUpdateBatch(vo.getFiles(), TABLE_NAME, TABLE_PRIMARY_KEY, vo.getId().toString());
		}
		return sum;
	}
	
	@Transactional
	public int issue(Integer id) {
		NoticeVo vo = getDao().find(id);
		DvVoHelper.markModifyStamp(null, vo);
		vo.setIssue_status(NoticeVo.ISSUE_STATUS);
		vo.setIssue_at(DvDateHelper.getSysTimestamp());
		return getDao().update(SQL_ISSUE_BY_ID, new Object[]{ vo.getIssue_status(), vo.getIssue_at(), vo.getModify_by(), vo.getModify_at(), vo.getId() });
	}

	@Transactional
	public int cancelIssue(Integer id) {
		NoticeVo vo = getDao().find(id);
		DvVoHelper.markModifyStamp(null, vo);
		vo.setIssue_status(NoticeVo.CANCEL_ISSUE_STATUS);
		vo.setIssue_at(null);
		return getDao().update(SQL_ISSUE_BY_ID, new Object[]{ vo.getIssue_status(), vo.getIssue_at(), vo.getModify_by(), vo.getModify_at(), vo.getId() });
	}
	
	public void increaseReadTimes(Integer id) {
		getDao().update(SQL_INCREASE_READ_TIMES_BY_ID, new Object[]{ id });
	}

}
