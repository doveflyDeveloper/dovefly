package com.deertt.module.mv.song.vo;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class MvSongSupportVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	
	//----------开始vo的属性 ----------
	//id
	private Integer id;
	
	//歌曲id
	private Integer song_id;
	
	//歌曲名称
	private String song_name;
	
	//参赛人id
	private Integer user_id;
	
	//参赛人
	private String user_name;
	
	//汀豆
	private Integer coin_quantity;
	
    //投票时间
	
	//----------结束vo的属性----------
	@Override
	public boolean isNew() {
		return id == null || id == 0;
	}
	//----------开始vo的setter和getter方法----------
	/** 获取id */
	public Integer getId() {
		return id;
	}
	
	/** 设置id */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/** 获取歌曲id */
	public Integer getSong_id() {
		return song_id;
	}
	
	/** 设置歌曲id */
	public void setSong_id(Integer song_id) {
		this.song_id = song_id;
	}
	
	/** 获取歌曲名称 */
	public String getSong_name() {
		return song_name;
	}
	
	/** 设置歌曲名称 */
	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}
	
	/** 获取参赛人id */
	public Integer getUser_id() {
		return user_id;
	}
	
	/** 设置参赛人id */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	/** 获取参赛人 */
	public String getUser_name() {
		return user_name;
	}
	
	/** 设置参赛人 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/** 获取汀豆 */
	public Integer getCoin_quantity() {
		return coin_quantity;
	}
	
	/** 设置汀豆 */
	public void setCoin_quantity(Integer coin_quantity) {
		this.coin_quantity = coin_quantity;
	}
	
	//----------结束vo的setter和getter方法----------
}