package com.deertt.module.market.bill.util;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.market.bill.vo.MarketBillVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IMarketBillConstants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "marketBillController:read";
	
	public final static String PERM_WRITE = "marketBillController:*";
	
	//URL
	public final static String CONTROLLER = "/marketBillController";
	
	public final static String DEFAULT_REDIRECT = "/marketBillController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/market";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/market";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "id, city_id, city_name, market_id, market_name, buyer_id, buyer_name, bill_code, bill_type, bill_date, bill_time, quantity, real_amount, total_amount, limit_coin_quantity, use_coin_quantity, send_coin_quantity, use_coin_amount, pay_type, pay_code, pay_amount, pay_time, pay_status, refund_code, refund_amount, refund_time, refund_status, ship_name, ship_mobile, ship_addr, express_id, remark, submit_time, send_time, receive_time, status, create_by, create_at, modify_by, modify_at";

	public final static String AFTER_SELECT_SHORT = "id, city_id, city_name, market_id, market_name, buyer_id, buyer_name, bill_code, bill_type, bill_date, bill_time, quantity, real_amount, total_amount, limit_coin_quantity, use_coin_quantity, send_coin_quantity, use_coin_amount, pay_type, pay_code, pay_amount, pay_time, pay_status, refund_code, refund_amount, refund_time, refund_status, ship_name, ship_mobile, ship_addr, express_id, remark, submit_time, send_time, receive_time, status";

	public final static String SQL_INSERT = "INSERT INTO market_bill ( city_id, city_name, market_id, market_name, buyer_id, buyer_name, bill_code, bill_type, bill_date, bill_time, quantity, real_amount, total_amount, limit_coin_quantity, use_coin_quantity, send_coin_quantity, use_coin_amount, ship_name, ship_mobile, ship_addr, express_id, remark, status, create_by, create_at ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM market_bill WHERE id = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM market_bill";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM market_bill WHERE id = ?";

	public final static String SQL_UPDATE_BY_ID = "UPDATE market_bill SET city_id = ?, city_name = ?, market_id = ?, market_name = ?, buyer_id = ?, buyer_name = ?, bill_code = ?, bill_type = ?, bill_date = ?, bill_time = ?, quantity = ?, real_amount = ?, total_amount = ?, limit_coin_quantity = ?, use_coin_quantity = ?, send_coin_quantity = ?, use_coin_amount = ?, ship_name = ?, ship_mobile = ?, ship_addr = ?, express_id = ?, remark = ?, status = ?, modify_by = ?, modify_at = ? WHERE id = ?";

	public final static String SQL_UPDATE_PAY_INFO_BY_ID = "UPDATE market_bill SET pay_type = ?, pay_code = ?, pay_amount = ?, pay_time= ?, pay_status = ? WHERE id = ?";

	public final static String SQL_UPDATE_REFUND_INFO_BY_ID = "UPDATE market_bill SET refund_code = ?, refund_amount = ?, refund_time = ?, refund_status = ? WHERE id = ?";

	public final static String SQL_UPDATE_STATUS_BY_ID = "UPDATE market_bill SET status = ? WHERE id = ?";
	
	public final static String SQL_SUBMIT_BY_ID = "UPDATE market_bill SET bill_date = now(), bill_time = now(), submit_time = now(), status = ? WHERE id = ?";

	public final static String SQL_DELIVER_BY_ID = "UPDATE market_bill SET send_time = now(), status = ? WHERE id = ?";

	public final static String SQL_RECEIVE_BY_ID = "UPDATE market_bill SET receive_time = now(), status = ? WHERE id = ?";

	public final static String SQL_COUNT = "SELECT COUNT(id) FROM market_bill";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM market_bill";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM market_bill";

	public final static String SQL_REPORT_GROUP_BY_BILL_DATE = "SELECT city_id, city_name, bill_date, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM market_bill WHERE status in ('" + MarketBillVo.STATUS_SUBMIT + "','" + MarketBillVo.STATUS_DELIVERED + "','" + MarketBillVo.STATUS_RECEIVED + "') AND city_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY city_id, city_name, bill_date ORDER BY city_id ASC, bill_date ASC";
	
	public final static String SQL_REPORT_SHOP_GROUP_BY_BILL_DATE = "SELECT city_id, city_name, market_id, market_name, bill_date, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM market_bill WHERE status in ('" + MarketBillVo.STATUS_SUBMIT + "','" + MarketBillVo.STATUS_DELIVERED + "','" + MarketBillVo.STATUS_RECEIVED + "') AND city_id = ? AND market_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY bill_date ORDER BY bill_date ASC";
	
	public final static String SQL_REPORT_MANAGER_GROUP_BY_BILL_DATE = "SELECT city_id, city_name, manager_id, manager_name, bill_date, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM market_bill WHERE status in ('" + MarketBillVo.STATUS_SUBMIT + "','" + MarketBillVo.STATUS_DELIVERED + "','" + MarketBillVo.STATUS_RECEIVED + "') AND city_id = ? AND manager_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY bill_date ORDER BY bill_date ASC";
	
	public final static String SQL_REPORT_GROUP_BY_CITY = "SELECT city_id, city_name, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM market_bill WHERE status in ('" + MarketBillVo.STATUS_SUBMIT + "','" + MarketBillVo.STATUS_DELIVERED + "','" + MarketBillVo.STATUS_RECEIVED + "') AND city_id = ? AND bill_date >= ? and bill_date <= ? "
			+ "GROUP BY city_id ORDER BY city_id ASC";  
	
	public final static String SQL_REPORT_GROUP_BY_MARKET = "SELECT market_id, market_name, COUNT(id) bill_count, SUM(total_amount) bill_amount "
			+ "FROM market_bill WHERE status in ('" + MarketBillVo.STATUS_SUBMIT + "','" + MarketBillVo.STATUS_DELIVERED + "','" + MarketBillVo.STATUS_RECEIVED + "') AND market_id = ? AND bill_date >= ? AND bill_date <= ? "
			+ "GROUP BY market_id";
	
	public final static String SQL_REPORT_GROUPY_BY_GOODS = "SELECT d.goods_id, d.goods_name, SUM(d.quantity) quantity, SUM(d.sub_total) sub_total "
			+ "FROM market_bill t, market_detail d "
			+ "WHERE t.id = d.bill_id AND t.status in ('" + MarketBillVo.STATUS_SUBMIT + "','" + MarketBillVo.STATUS_DELIVERED + "','" + MarketBillVo.STATUS_RECEIVED + "') AND t.market_id = ? AND t.bill_date >= ? AND t.bill_date <= ? "
			+ "GROUP BY d.goods_id ORDER BY sub_total desc";
	
	public final static String SQL_LIST_DETAILS = "SELECT t.id, t.city_name, t.buyer_id, t.buyer_name, t.market_id, t.market_name, t.bill_code, "
			+ "t.bill_date, t.total_amount, t.real_amount, t.pay_type, t.pay_status, t.refund_status, t.refund_amount, t.refund_time, t.status, d.goods_id, d.goods_name, d.unit_price, d.quantity, d.sub_total "
			+ "FROM market_bill t, market_detail d WHERE t.id = d.bill_id";
	
	public final static String SQL_REPORT_GROUP_BY_STATUS = "SELECT status, count(id) bill_count FROM market_bill #WHERE# GROUP BY status WITH ROLLUP";
	
	public final static String SQL_UPDATE_EXPRESS_ID = "UPDATE market_bill SET express_id = ? where id = ?";
	/**表名*/
	public final static String TABLE_NAME = "market_bill";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "订单";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "id";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
