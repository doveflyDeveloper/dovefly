package com.deertt.module.pay.alipaybill.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.utils.helper.DvSqlHelper;

import com.deertt.module.pay.alipaybill.dao.IAlipayBillDao;
import com.deertt.module.pay.alipaybill.util.IAlipayBillConstants;
import com.deertt.module.pay.alipaybill.vo.AlipayBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Repository
public class AlipayBillDao extends DvBaseDao<AlipayBillVo, Integer> implements IAlipayBillDao, IAlipayBillConstants {

	public int insert(AlipayBillVo vo) {
		Object[] objs = { vo.getBalance(), vo.getIncome(), vo.getOutcome(), vo.getTrans_date(), vo.getTrans_code_msg(), vo.getSub_trans_code_msg(), vo.getMerchant_out_order_no(), vo.getMemo(), vo.getBuyer_account(), vo.getSeller_account(), vo.getSeller_fullname(), vo.getCurrency(), vo.getDeposit_bank_no(), vo.getGoods_title(), vo.getIw_account_log_id(), vo.getPartner_id(), vo.getService_fee(), vo.getService_fee_ratio(), vo.getTotal_fee(), vo.getTrade_no(), vo.getTrade_refund_amount(), vo.getSign_product_name(), vo.getRate(), vo.getCheck_status() };
		vo.setId(super.insertForIntKey(SQL_INSERT, objs));
		return 1;
	}

	public int insert(AlipayBillVo[] vos) {
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<AlipayBillVo>() {
			public Object[] getArgs(AlipayBillVo vo) {
				return new Object[]{ vo.getBalance(), vo.getIncome(), vo.getOutcome(), vo.getTrans_date(), vo.getTrans_code_msg(), vo.getSub_trans_code_msg(), vo.getMerchant_out_order_no(), vo.getMemo(), vo.getBuyer_account(), vo.getSeller_account(), vo.getSeller_fullname(), vo.getCurrency(), vo.getDeposit_bank_no(), vo.getGoods_title(), vo.getIw_account_log_id(), vo.getPartner_id(), vo.getService_fee(), vo.getService_fee_ratio(), vo.getTotal_fee(), vo.getTrade_no(), vo.getTrade_refund_amount(), vo.getSign_product_name(), vo.getRate(), vo.getCheck_status() };
			}
		});
	}

	public int delete(Integer id) {
		return super.update(SQL_DELETE_BY_ID, new Object[] { id });
	}

	public int delete(Integer[] ids) {
		if (ids == null || ids.length == 0)
			return 0;
		StringBuilder sql = new StringBuilder(SQL_DELETE_MULTI_BY_IDS);
		sql.append(" WHERE id IN (");
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids)); //把ids数组转换为id1,id2,id3
		sql.append(")");
		return super.update(sql.toString());
	}

	public int deleteByCondition(String queryCondition) {
		StringBuilder sql = new StringBuilder(SQL_DELETE_MULTI_BY_IDS);
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD); //where后加上查询条件
			sql.append(queryCondition);
		}
		return super.update(sql.toString());
	}

	public AlipayBillVo find(Integer id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<AlipayBillVo>(AlipayBillVo.class));
	}

	public AlipayBillVo findByCondition(String queryCondition) {
		List<AlipayBillVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(AlipayBillVo vo) {
		Object[] objs = { vo.getBalance(), vo.getIncome(), vo.getOutcome(), vo.getTrans_date(), vo.getTrans_code_msg(), vo.getSub_trans_code_msg(), vo.getMerchant_out_order_no(), vo.getMemo(), vo.getBuyer_account(), vo.getSeller_account(), vo.getSeller_fullname(), vo.getCurrency(), vo.getDeposit_bank_no(), vo.getGoods_title(), vo.getIw_account_log_id(), vo.getPartner_id(), vo.getService_fee(), vo.getService_fee_ratio(), vo.getTotal_fee(), vo.getTrade_no(), vo.getTrade_refund_amount(), vo.getSign_product_name(), vo.getRate(), vo.getCheck_status(), vo.getId() };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final AlipayBillVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<AlipayBillVo>() {
			public Object[] getArgs(AlipayBillVo vo) {
				return new Object[]{ vo.getBalance(), vo.getIncome(), vo.getOutcome(), vo.getTrans_date(), vo.getTrans_code_msg(), vo.getSub_trans_code_msg(), vo.getMerchant_out_order_no(), vo.getMemo(), vo.getBuyer_account(), vo.getSeller_account(), vo.getSeller_fullname(), vo.getCurrency(), vo.getDeposit_bank_no(), vo.getGoods_title(), vo.getIw_account_log_id(), vo.getPartner_id(), vo.getService_fee(), vo.getService_fee_ratio(), vo.getTotal_fee(), vo.getTrade_no(), vo.getTrade_refund_amount(), vo.getSign_product_name(), vo.getRate(), vo.getCheck_status(), vo.getId() };
			}
		});
	}

	public int getRecordCount(String queryCondition) {
		StringBuilder sql = new StringBuilder(SQL_COUNT + DEFAULT_SQL_WHERE_USABLE);
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD); //where后加上查询条件
			sql.append(queryCondition);
		}
		return super.queryForInt(sql.toString());
	}

	public List<AlipayBillVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
		StringBuilder sql = new StringBuilder();
		if(selectAllClumn) {
			sql.append(SQL_QUERY_ALL_EXPORT + DEFAULT_SQL_WHERE_USABLE);
		} else {
			sql.append(SQL_QUERY_ALL + DEFAULT_SQL_WHERE_USABLE);
		}
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD); //where后加上查询条件
			sql.append(queryCondition);
		}
		if(orderStr != null && orderStr.trim().length() > 0) {
			sql.append(ORDER_BY_SYMBOL);
			sql.append(orderStr);
		} else {
			sql.append(DEFAULT_ORDER_BY_CODE);
		}
		return this.queryByCondition(sql.toString(), startIndex, size);
	}

	public List<AlipayBillVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<AlipayBillVo>(AlipayBillVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<AlipayBillVo>(AlipayBillVo.class), startIndex, size); 
		}
	}

}
