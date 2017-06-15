package com.deertt.frame.base.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.deertt.frame.base.dao.IDvBaseDao;
import com.deertt.utils.helper.DvPopulateHelper;

public abstract class DvBaseDao<T, ID extends Serializable> implements IDvBaseDao<T, ID> {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	protected void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public int update(String sql) {
		return jdbcTemplate.update(sql);
	}
	
	public int update(String sql, Object[] args) {
		logger.info(Arrays.toString(args));
		return jdbcTemplate.update(sql, args);
	}

	public int insertForIntKey(final String sql, final Object[] args) {
		logger.info(Arrays.toString(args));
		KeyHolder keyHolder = new GeneratedKeyHolder(); // ①创建一个主键执有者
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				for (int i = 0; i < args.length; i++) {
					ps.setObject(i + 1, args[i]);
				}
				return ps;
			}   
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public long insertForLongKey(final String sql, final Object[] args) {
		logger.info(Arrays.toString(args));
		KeyHolder keyHolder = new GeneratedKeyHolder(); // ①创建一个主键执有者
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				for (int i = 0; i < args.length; i++) {
					ps.setObject(i + 1, args[i]);
				}
				return ps;
			}   
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	public int batchUpdate(String sql, final T[] objs, final CircleVoArray<T> cva){
		logger.info(objs);
		if (objs.length == 0) {
			return 0;
		}
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Object[] args = cva.getArgs(objs[i]);
				ArgTypePreparedStatementSetter atpss = new ArgTypePreparedStatementSetter(args, getSqlTypeFromArgs(args));
				atpss.setValues(ps);
			}

			public int getBatchSize() {
				return objs.length;
			}
		});
		return objs.length;
	}
	
	public int queryForInt(String sql) {
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}
	
	public T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) {
		logger.info(Arrays.toString(args));
		try{
			return this.getJdbcTemplate().queryForObject(sql, args, rowMapper);
		} catch (EmptyResultDataAccessException e){
			e.printStackTrace();
		} catch (DataAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<String, Object> queryForMap(String sql) {
		return this.getJdbcTemplate().queryForObject(sql, new RowMapper<Map<String, Object>>() {
			public Map<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
				Map<String, Object> vo = new LinkedHashMap<String, Object>();
				DvPopulateHelper.populate(vo, rs);
				return vo;
			}
		});
	}
	
	public List<Map<String, Object>> queryForMaps(String sql) {
		return jdbcTemplate.query(sql, new RowMapper<Map<String, Object>>() {
			public Map<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
				Map<String, Object> vo = new LinkedHashMap<String, Object>();
				DvPopulateHelper.populate(vo, rs);
				return vo;
			}
		});
	}
	
	public List<Map<String, Object>> queryForMaps(String sql, Object[] args) {
		logger.info(Arrays.toString(args));
		return jdbcTemplate.query(sql, args, new RowMapper<Map<String, Object>>() {
			public Map<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
				Map<String, Object> vo = new LinkedHashMap<String, Object>();
				DvPopulateHelper.populate(vo, rs);
				return vo;
			}
		});
	}
	
	public List<T> query(String sql, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	public List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
		logger.info(Arrays.toString(args));
		return jdbcTemplate.query(sql, args, rowMapper);
	}
	
	public List<?> queryList(String sql, Object[] args, RowMapper<?> rowMapper) {
		logger.info(Arrays.toString(args));
		return jdbcTemplate.query(sql, args, rowMapper);
	}
	
	public List<T> query(String sql, RowMapper<T> rowMapper, int startIndex, int size) {
		String limitSql = getSqlPage4Mysql(sql, startIndex, size);
		return jdbcTemplate.query(limitSql, rowMapper);
	}
	
	private String getSqlPage4Mysql(String sql, int startIndex, int size) {
		if(startIndex < 1) {
			startIndex = 1;
		}
		if(size < 0) {
			return sql;
		}
		return sql + " limit " + (startIndex - 1) + "," + size;
	}
	
	private String getSqlPage4Oracle(String sql, int startIndex, int size) {
		return "select * from (select rownum as rmrn, a.* from(" + sql + ") a where rownum<=" + (startIndex + size - 1) + ")where rmrn >=" + startIndex;
	}
	
	/**
	 * 根据参数对象类型，返回数据库对应的数据类型
	 * @param args
	 * @return
	 */
	private int[] getSqlTypeFromArgs(Object[] args) {
		int types[] = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				types[i] = Types.VARCHAR;
			} else if (args[i] instanceof java.sql.Timestamp) {
				types[i] = Types.TIMESTAMP;
			} else if (args[i] instanceof java.sql.Date) {
				types[i] = Types.DATE;
			} else if (args[i] instanceof java.sql.Time) {
				types[i] = Types.TIME;
			} else if (args[i] instanceof java.math.BigDecimal) {
				types[i] = Types.DECIMAL;
			} else if (args[i] instanceof Integer) {
				types[i] = Types.INTEGER;
			} else if (args[i] instanceof Long) {
				types[i] = Types.BIGINT;
			} else if (args[i] instanceof Short) {
				types[i] = Types.SMALLINT;
			} else if (args[i] instanceof Float) {
				types[i] = Types.FLOAT;
			} else if (args[i] instanceof Double) {
				types[i] = Types.DOUBLE;
			} else if (args[i] instanceof byte[]) {
				types[i] = Types.BLOB;
			} else {
				types[i] = Types.VARCHAR;
			}
		}
		return types;
	}
	
//	public int getNextVal(String seqName) {
//		return this.getJdbcTemplate().queryForInt("select nextval(?)", seqName);
//	}

}
