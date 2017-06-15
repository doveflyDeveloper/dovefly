package com.deertt.module.mv.song.service;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.mv.song.dao.IMvSongDao;
import com.deertt.module.mv.song.vo.MvSongVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMvSongService extends IDvBaseService<IMvSongDao, MvSongVo, Integer> {

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public MvSongVo findFull(Integer id);
	
	/**
	 * 歌唱审核拒绝
	 * @param id
	 * @param reason
	 * @return
	 */
	public int deny(Integer id, String reason);
	
	/**
	 * 歌唱审核通过
	 * @param id
	 * @param reason
	 * @return
	 */
	public int agree(Integer id);
}
