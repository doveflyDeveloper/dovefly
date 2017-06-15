package com.deertt.module.tcommonfile.service;

import java.util.List;

import com.deertt.frame.base.service.IDvBaseService;
import com.deertt.module.tcommonfile.dao.ITCommonFileDao;
import com.deertt.module.tcommonfile.vo.TCommonFileVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface ITCommonFileService extends IDvBaseService<ITCommonFileDao, TCommonFileVo, String> {
	
	/**
	 * 批量保存，没有主键的insert，有主键的update，缺少的则删除
	 * 
	 * @param voList
	 * @param owner_table_name 所属表
	 * @param owner_column_name 所属列
	 * @param owner_bill_id 所属单据ID
	 * @return
	 */
	public int[] insertUpdateBatch(List<TCommonFileVo> voList, String owner_table_name, String owner_column_name, String owner_bill_id);

	/**
	 * 查询单据下的所有关联附件
	 * @param owner_table_name 所属表
	 * @param owner_column_name 所属列
	 * @param owner_bill_id 所属单据ID
	 * @return
	 */
	public List<TCommonFileVo> queryFiles(String owner_table_name, String owner_column_name, String owner_bill_id);
}
