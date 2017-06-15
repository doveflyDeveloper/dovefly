package com.deertt.module.pay.wxbill.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.utils.helper.DvSqlHelper;

import com.deertt.module.pay.wxbill.dao.IWxBillDao;
import com.deertt.module.pay.wxbill.util.IWxBillConstants;
import com.deertt.module.pay.wxbill.vo.WxBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Repository
public class WxBillDao extends DvBaseDao<WxBillVo, Integer> implements IWxBillDao, IWxBillConstants {

	public int insert(WxBillVo vo) {
		Object[] objs = { vo.getTrade_time(), vo.getGh_id(), vo.getMch_id(), vo.getSub_mch(), vo.getDevice_id(), vo.getWx_order(), vo.getBz_order(), vo.getOpen_id(), vo.getTrade_type(), vo.getTrade_status(), vo.getBank(), vo.getCurrency(), vo.getTotal_money(), vo.getRed_packet_money(), vo.getWx_refund(), vo.getBz_refund(), vo.getRefund_money(), vo.getRed_packet_refund(), vo.getRefund_type(), vo.getRefund_status(), vo.getProduct_name(), vo.getBz_data_packet(), vo.getFee(), vo.getRate(), vo.getCheck_status() };
		vo.setId(super.insertForIntKey(SQL_INSERT, objs));
		return 1;
	}

	public int insert(WxBillVo[] vos) {
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<WxBillVo>() {
			public Object[] getArgs(WxBillVo vo) {
				return new Object[]{ vo.getTrade_time(), vo.getGh_id(), vo.getMch_id(), vo.getSub_mch(), vo.getDevice_id(), vo.getWx_order(), vo.getBz_order(), vo.getOpen_id(), vo.getTrade_type(), vo.getTrade_status(), vo.getBank(), vo.getCurrency(), vo.getTotal_money(), vo.getRed_packet_money(), vo.getWx_refund(), vo.getBz_refund(), vo.getRefund_money(), vo.getRed_packet_refund(), vo.getRefund_type(), vo.getRefund_status(), vo.getProduct_name(), vo.getBz_data_packet(), vo.getFee(), vo.getRate(), vo.getCheck_status() };
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

	public WxBillVo find(Integer id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<WxBillVo>(WxBillVo.class));
	}

	public WxBillVo findByCondition(String queryCondition) {
		List<WxBillVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(WxBillVo vo) {
		Object[] objs = { vo.getTrade_time(), vo.getGh_id(), vo.getMch_id(), vo.getSub_mch(), vo.getDevice_id(), vo.getWx_order(), vo.getBz_order(), vo.getOpen_id(), vo.getTrade_type(), vo.getTrade_status(), vo.getBank(), vo.getCurrency(), vo.getTotal_money(), vo.getRed_packet_money(), vo.getWx_refund(), vo.getBz_refund(), vo.getRefund_money(), vo.getRed_packet_refund(), vo.getRefund_type(), vo.getRefund_status(), vo.getProduct_name(), vo.getBz_data_packet(), vo.getFee(), vo.getRate(), vo.getCheck_status(), vo.getId() };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final WxBillVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<WxBillVo>() {
			public Object[] getArgs(WxBillVo vo) {
				return new Object[]{ vo.getTrade_time(), vo.getGh_id(), vo.getMch_id(), vo.getSub_mch(), vo.getDevice_id(), vo.getWx_order(), vo.getBz_order(), vo.getOpen_id(), vo.getTrade_type(), vo.getTrade_status(), vo.getBank(), vo.getCurrency(), vo.getTotal_money(), vo.getRed_packet_money(), vo.getWx_refund(), vo.getBz_refund(), vo.getRefund_money(), vo.getRed_packet_refund(), vo.getRefund_type(), vo.getRefund_status(), vo.getProduct_name(), vo.getBz_data_packet(), vo.getFee(), vo.getRate(), vo.getCheck_status(), vo.getId() };
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

	public List<WxBillVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
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

	public List<WxBillVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<WxBillVo>(WxBillVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<WxBillVo>(WxBillVo.class), startIndex, size); 
		}
	}

}
