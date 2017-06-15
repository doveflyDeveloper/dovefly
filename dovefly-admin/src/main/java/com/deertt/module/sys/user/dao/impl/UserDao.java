package com.deertt.module.sys.user.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.module.sys.user.dao.IUserDao;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.sys.user.vo.UserVo;
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
public class UserDao extends DvBaseDao<UserVo, Integer> implements IUserDao, IUserConstants {

	public int insert(UserVo vo) {
		Object[] objs = { vo.getCity_id(), vo.getCity_name(), vo.getSchool_id(), vo.getSchool_name(), vo.getReal_name(), vo.getNick_name(), vo.getAccount(), vo.getPassword(), vo.getMobile(), vo.getEmail(), vo.getDorm_address(), vo.getAddress(), vo.getAvatar(), vo.getBirthday(), vo.getLevel(), vo.getPoint(), vo.getLast_login_time(), vo.getLogin_times(), vo.getPwd_reset(), vo.getCoin_quantity(), vo.getWechat_subscribe(), vo.getWechat_account(), vo.getWechat_id(), vo.getWechat_unionid(), vo.getWechat_avatar(), vo.getAlipay_account(), vo.getDf_shop(), vo.getFav_shops(), vo.getManage_warehouse_id(), vo.getManage_shop_id(), vo.getManage_market_id(), vo.getRemark(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at() };
		vo.setId(super.insertForIntKey(SQL_INSERT, objs));
		return 1;
	}

	public int insert(UserVo[] vos) {
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<UserVo>() {
			public Object[] getArgs(UserVo vo) {
				return new Object[]{ vo.getCity_id(), vo.getCity_name(), vo.getSchool_id(), vo.getSchool_name(), vo.getReal_name(), vo.getNick_name(), vo.getAccount(), vo.getPassword(), vo.getMobile(), vo.getEmail(), vo.getDorm_address(), vo.getAddress(), vo.getAvatar(), vo.getBirthday(), vo.getLevel(), vo.getPoint(), vo.getLast_login_time(), vo.getLogin_times(), vo.getPwd_reset(), vo.getCoin_quantity(), vo.getWechat_subscribe(), vo.getWechat_account(), vo.getWechat_id(), vo.getWechat_unionid(), vo.getWechat_avatar(), vo.getAlipay_account(), vo.getDf_shop(), vo.getFav_shops(), vo.getManage_warehouse_id(), vo.getManage_shop_id(), vo.getManage_market_id(), vo.getRemark(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at() };
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

	public UserVo find(Integer id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<UserVo>(UserVo.class));
	}

	public UserVo findByCondition(String queryCondition) {
		List<UserVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(UserVo vo) {
		Object[] objs = { vo.getCity_id(), vo.getCity_name(), vo.getSchool_id(), vo.getSchool_name(), vo.getReal_name(), vo.getNick_name(), vo.getAccount(), vo.getPassword(), vo.getMobile(), vo.getEmail(), vo.getDorm_address(), vo.getAddress(), vo.getAvatar(), vo.getBirthday(), vo.getLevel(), vo.getPoint(), vo.getLast_login_time(), vo.getLogin_times(), vo.getPwd_reset(), vo.getCoin_quantity(), vo.getWechat_subscribe(), vo.getWechat_account(), vo.getWechat_id(), vo.getWechat_unionid(), vo.getWechat_avatar(), vo.getAlipay_account(), vo.getDf_shop(), vo.getFav_shops(), vo.getManage_warehouse_id(), vo.getManage_shop_id(), vo.getManage_market_id(), vo.getRemark(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final UserVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<UserVo>() {
			public Object[] getArgs(UserVo vo) {
				return new Object[]{ vo.getCity_id(), vo.getCity_name(), vo.getSchool_id(), vo.getSchool_name(), vo.getReal_name(), vo.getNick_name(), vo.getAccount(), vo.getPassword(), vo.getMobile(), vo.getEmail(), vo.getDorm_address(), vo.getAddress(), vo.getAvatar(), vo.getBirthday(), vo.getLevel(), vo.getPoint(), vo.getLast_login_time(), vo.getLogin_times(), vo.getPwd_reset(), vo.getCoin_quantity(), vo.getWechat_subscribe(), vo.getWechat_account(), vo.getWechat_id(), vo.getWechat_unionid(), vo.getWechat_avatar(), vo.getAlipay_account(), vo.getDf_shop(), vo.getFav_shops(), vo.getManage_warehouse_id(), vo.getManage_shop_id(), vo.getManage_market_id(), vo.getRemark(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
			}
		});
	}
	
	public int change(UserVo vo) {
		Object[] objs = { vo.getReal_name(), vo.getEmail(), vo.getAddress(), vo.getAlipay_account(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
		return super.update(SQL_CHANGE_BY_ID, objs);
	}
	
	public int changePwd(UserVo vo) {
		Object[] objs = { vo.getPassword(), vo.getPwd_reset(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
		return super.update(SQL_UPDATE_PWD_BY_ID, objs);
	}
	
	public int enable(Integer[] ids) {
		if (ids == null || ids.length == 0)
			return 0;
		StringBuilder sql = new StringBuilder("UPDATE " + TABLE_NAME + " SET status = '1'");
		sql.append(" WHERE id IN (");
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids)); //把ids数组转换为id1,id2,id3
		sql.append(")");
		return super.update(sql.toString());
	}
	
	public int disable(Integer[] ids) {
		if (ids == null || ids.length == 0)
			return 0;
		StringBuilder sql = new StringBuilder("UPDATE " + TABLE_NAME + " SET status = '0'");
		sql.append(" WHERE id IN (");
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids)); //把ids数组转换为id1,id2,id3
		sql.append(")");
		return super.update(sql.toString());
	}

	public int getRecordCount(String queryCondition) {
		StringBuilder sql = new StringBuilder(SQL_COUNT + DEFAULT_SQL_WHERE_USABLE);
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD); //where后加上查询条件
			sql.append(queryCondition);
		}
		return super.queryForInt(sql.toString());
	}

	public List<UserVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
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

	public List<UserVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<UserVo>(UserVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<UserVo>(UserVo.class), startIndex, size); 
		}
	}

	public int authorizeRoles(Integer user_id, Integer[] role_ids) {
		super.update(SQL_DELETE_AUTHORIZE_ROLE, new Integer[] { user_id });
		List<Object[]> list = new ArrayList<Object[]>();
		for (Integer role_id : role_ids) {
			list.add(new Integer[] { user_id, role_id });
		}
		super.getJdbcTemplate().batchUpdate(SQL_INSERT_AUTHORIZE_ROLE, list);
		return role_ids.length;
	}
	
	public int changeCoin_quantity(Integer user_id, Integer new_coin_quantity) {
		return super.update(SQL_UPDATE_COIN_QUANTITY_BY_USER, new Object[] { new_coin_quantity, user_id });
	}

}
