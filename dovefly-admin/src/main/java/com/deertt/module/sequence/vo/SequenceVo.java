package com.deertt.module.sequence.vo;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class SequenceVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	// ----------开始vo的属性 ----------
	// id
	private Long id;
	private String sequence_name;
	private Long sequence_value;
	private Integer sequence_step;
	private Long version;

	@Override
	public boolean isNew() {
		return id == null || id == 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSequence_name() {
		return sequence_name;
	}

	public void setSequence_name(String sequence_name) {
		this.sequence_name = sequence_name;
	}

	public Long getSequence_value() {
		return sequence_value;
	}

	public void setSequence_value(Long sequence_value) {
		this.sequence_value = sequence_value;
	}

	public Integer getSequence_step() {
		return sequence_step;
	}

	public void setSequence_step(Integer sequence_step) {
		this.sequence_step = sequence_step;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}