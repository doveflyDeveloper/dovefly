package com.deertt.module.mm.goods.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.module.mm.goods.dao.IGoodsSDao;
import com.deertt.module.mm.goods.util.IGoodsSConstants;
import com.deertt.module.mm.goods.vo.GoodsSVo;
import com.deertt.utils.helper.DvSqlHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Repository
public class GoodsSDao extends DvBaseDao<GoodsSVo, Integer> implements IGoodsSDao, IGoodsSConstants {

	public int insert(GoodsSVo vo) {
		Object[] objs = { vo.getOrigin_id(), vo.getCity_id(), vo.getCity_name(), vo.getShop_id(), vo.getShop_name(), vo.getItem_id(), vo.getBarcode(), vo.getName(), vo.getTitle(), vo.getTips(), vo.getType(), vo.getTag(), vo.getWeight(), vo.getShelf_life(), vo.getStorage_type(), vo.getProducer(), vo.getCost_price(), vo.getSale_price(), vo.getMarket_price(), vo.getLimit_coin_quantity(), vo.getSend_coin_quantity(), vo.getBuy_rule(), vo.getSpec(), vo.getCategory_id(), vo.getCategory_code(), vo.getSafe_line(), vo.getMax_limit(), vo.getCmt_point(), vo.getCmt_times(), vo.getImage(), vo.getImages(), vo.getDescription(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at()};
		vo.setId(super.insertForIntKey(SQL_INSERT, objs));
		return 1;
	}

	public int insert(GoodsSVo[] vos) {
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<GoodsSVo>() {
			public Object[] getArgs(GoodsSVo vo) {
				return new Object[]{ vo.getOrigin_id(), vo.getCity_id(), vo.getCity_name(), vo.getShop_id(), vo.getShop_name(), vo.getItem_id(), vo.getBarcode(), vo.getName(), vo.getTitle(), vo.getTips(), vo.getType(), vo.getTag(), vo.getWeight(), vo.getShelf_life(), vo.getStorage_type(), vo.getProducer(), vo.getCost_price(), vo.getSale_price(), vo.getMarket_price(), vo.getLimit_coin_quantity(), vo.getSend_coin_quantity(), vo.getBuy_rule(), vo.getSpec(), vo.getCategory_id(), vo.getCategory_code(), vo.getSafe_line(), vo.getMax_limit(), vo.getCmt_point(), vo.getCmt_times(), vo.getImage(), vo.getImages(), vo.getDescription(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at()};
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
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids));
		sql.append(")");
		return super.update(sql.toString());
	}

	public int deleteByCondition(String queryCondition) {
		StringBuilder sql = new StringBuilder(SQL_DELETE_MULTI_BY_IDS);
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD);
			sql.append(queryCondition);
		}
		return super.update(sql.toString());
	}

	public GoodsSVo find(Integer id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<GoodsSVo>(GoodsSVo.class));
	}

	public GoodsSVo findByCondition(String queryCondition) {
		List<GoodsSVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(GoodsSVo vo) {
		Object[] objs = { vo.getOrigin_id(), vo.getCity_id(), vo.getCity_name(), vo.getShop_id(), vo.getShop_name(), vo.getItem_id(), vo.getBarcode(), vo.getName(), vo.getTitle(), vo.getTips(), vo.getType(), vo.getTag(), vo.getWeight(), vo.getShelf_life(), vo.getStorage_type(), vo.getProducer(), vo.getCost_price(), vo.getSale_price(), vo.getMarket_price(), vo.getLimit_coin_quantity(), vo.getSend_coin_quantity(), vo.getBuy_rule(), vo.getSpec(), vo.getCategory_id(), vo.getCategory_code(), vo.getSafe_line(), vo.getMax_limit(), vo.getCmt_point(), vo.getCmt_times(), vo.getImage(), vo.getImages(), vo.getDescription(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId()};
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final GoodsSVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<GoodsSVo>() {
			public Object[] getArgs(GoodsSVo vo) {
				return new Object[]{ vo.getOrigin_id(), vo.getCity_id(), vo.getCity_name(), vo.getShop_id(), vo.getShop_name(), vo.getItem_id(), vo.getBarcode(), vo.getName(), vo.getTitle(), vo.getTips(), vo.getType(), vo.getTag(), vo.getWeight(), vo.getShelf_life(), vo.getStorage_type(), vo.getProducer(), vo.getCost_price(), vo.getSale_price(), vo.getMarket_price(), vo.getLimit_coin_quantity(), vo.getSend_coin_quantity(), vo.getBuy_rule(), vo.getSpec(), vo.getCategory_id(), vo.getCategory_code(), vo.getSafe_line(), vo.getMax_limit(), vo.getCmt_point(), vo.getCmt_times(), vo.getImage(), vo.getImages(), vo.getDescription(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId()};
			}
		});
	}

	public int getRecordCount(String queryCondition) {
		StringBuilder sql = new StringBuilder(SQL_COUNT + DEFAULT_SQL_WHERE_USABLE);
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD);
			sql.append(queryCondition);
		}
		return super.queryForInt(sql.toString());
	}

	public List<GoodsSVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
		StringBuilder sql = new StringBuilder();
		if(selectAllClumn) {
			sql.append(SQL_QUERY_ALL_EXPORT + DEFAULT_SQL_WHERE_USABLE);
		} else {
			sql.append(SQL_QUERY_ALL + DEFAULT_SQL_WHERE_USABLE);
		}
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD);
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

	public List<GoodsSVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<GoodsSVo>(GoodsSVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<GoodsSVo>(GoodsSVo.class), startIndex, size); 
		}
	}
	
	public int enable(Integer[] ids) {
		if (ids == null || ids.length == 0)
			return 0;
		StringBuilder sql = new StringBuilder("UPDATE " + TABLE_NAME + " SET status = '" + DV_YES + "'");
		sql.append(" WHERE id IN (");
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids)); //把ids数组转换为id1,id2,id3
		sql.append(")");
		return super.update(sql.toString());
	}
	
	public int disable(Integer[] ids) {
		if (ids == null || ids.length == 0)
			return 0;
		StringBuilder sql = new StringBuilder("UPDATE " + TABLE_NAME + " SET status = '" + DV_NO + "'");
		sql.append(" WHERE id IN (");
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids)); //把ids数组转换为id1,id2,id3
		sql.append(")");
		return super.update(sql.toString());
	}
	
	public int sort(String goods_ids) {
		if (goods_ids == null || goods_ids.length() == 0)
			return 0;
		StringBuilder sql = new StringBuilder("");
		sql.append("UPDATE ").append(TABLE_NAME).append(" SET sort = FIND_IN_SET(id, '").append(goods_ids).append("') ");
		sql.append(" WHERE id IN (").append(goods_ids).append(")");
		return super.update(sql.toString());
	}

}
