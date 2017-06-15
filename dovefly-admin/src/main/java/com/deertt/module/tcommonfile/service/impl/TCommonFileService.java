package com.deertt.module.tcommonfile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.tcommonfile.dao.ITCommonFileDao;
import com.deertt.module.tcommonfile.service.ITCommonFileService;
import com.deertt.module.tcommonfile.util.ITCommonFileConstants;
import com.deertt.module.tcommonfile.vo.TCommonFileVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class TCommonFileService extends DvBaseService<ITCommonFileDao, TCommonFileVo, String> implements ITCommonFileService, ITCommonFileConstants {
	
	@Transactional
	public int[] insertUpdateBatch(List<TCommonFileVo> voList, String owner_table_name, String owner_column_name, String owner_bill_id) {
		int[] sum_insert_update_delete = new int[3];
		List<TCommonFileVo> lInsert = new ArrayList<TCommonFileVo>();
		List<TCommonFileVo> lUpdate = new ArrayList<TCommonFileVo>();
		List<String> lDelete = new ArrayList<String>();
		Map<String, TCommonFileVo> currentIds = new HashMap<String, TCommonFileVo>();
		if(voList != null){
			for (TCommonFileVo vo : voList) {
				vo.setOwner_table_name(owner_table_name);
				vo.setOwner_column_name(owner_column_name);
				vo.setOwner_bill_id(owner_bill_id);
				
				if(vo.getFile_id() == null || vo.getFile_id().trim().length() == 0) {
					lInsert.add(vo);
				} else {
					lUpdate.add(vo);
					currentIds.put(vo.getFile_id(), vo);
				}
			}
		}
		List<TCommonFileVo> oldList = queryFiles(owner_table_name, owner_column_name, owner_bill_id);
		if(oldList != null){
			for (TCommonFileVo vo : oldList) {
				if(!currentIds.containsKey(vo.getFile_id())){
					lDelete.add(vo.getFile_id());
				}
			}
		}

		if(lInsert.size() > 0) {
			sum_insert_update_delete[0] = insert(lInsert.toArray(new TCommonFileVo[0]));
		}
		if(lUpdate.size() > 0) {
			sum_insert_update_delete[1] = update(lUpdate.toArray(new TCommonFileVo[0]));
		}
		if(lDelete.size() > 0) {
			sum_insert_update_delete[2] = delete(lDelete.toArray(new String[0]));
		}
		return sum_insert_update_delete;
	}

	public List<TCommonFileVo> queryFiles(String owner_table_name, String owner_column_name, String owner_bill_id){
		String queryCondition = "owner_table_name = '" + owner_table_name + "' and owner_column_name = '" + owner_column_name + "' and owner_bill_id = '" + owner_bill_id + "'";
		return super.queryByCondition(queryCondition, "create_time");
	}

}
