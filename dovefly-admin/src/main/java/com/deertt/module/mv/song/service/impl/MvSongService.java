package com.deertt.module.mv.song.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.mv.song.dao.IMvSongDao;
import com.deertt.module.mv.song.service.IMvSongService;
import com.deertt.module.mv.song.service.IMvSongSupportService;
import com.deertt.module.mv.song.util.IMvSongConstants;
import com.deertt.module.mv.song.vo.MvSongVo;
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.sys.user.vo.UserVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class MvSongService extends DvBaseService<IMvSongDao, MvSongVo, Integer> implements IMvSongService, IMvSongConstants {
    
	@Autowired
	private IMvSongSupportService supportservice;
	
	@Autowired
	protected INotificationService notificationService;
	
	@Autowired
	protected IUserService userService;
	
	@Transactional
	public MvSongVo findFull(Integer id) {
		MvSongVo vo = super.find(id);
		vo.setDetails(supportservice.queryByCondition("song_id = " + id, null));
		return vo;
	}

	@Transactional
	public int deny(Integer id, String reason) {
		MvSongVo bean = this.find(id);
		
		String message = "歌唱视频审核不通过，原因：" + reason;
		notificationService.addWMNotification(bean.getUser_id(), IUserConstants.TABLE_NAME + "-" + bean.getUser_id() + "-" + "hint", message);
		
		bean.setStatus(MvSongVo.STATUS_DENYED);
		return this.update(bean);
	}

	@Transactional
	public int agree(Integer id) {
        MvSongVo bean = this.find(id);
		
		String message = "歌唱视频审核通过，已上传至小羊视频区";
		UserVo user = userService.find(bean.getUser_id());
		
		notificationService.addWMNotification(user.getId(), IUserConstants.TABLE_NAME + "-" + user.getId() + "-" + "hint", message);
		
		bean.setStatus(MvSongVo.STATUS_SUCCESS);
		return this.update(bean);
	}
	

}
