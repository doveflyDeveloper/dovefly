package com.deertt.frame.task;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.common.pay.util.AlipayUtils;
import com.deertt.common.pay.util.WxpayUtils;
import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.pay.alipaybill.service.IAlipayBillService;
import com.deertt.module.pay.alipaybill.vo.AlipayBillVo;
import com.deertt.module.pay.wxbill.service.IWxBillService;
import com.deertt.module.pay.wxbill.vo.WxBillVo;
import com.deertt.utils.helper.date.DvDateHelper;

/**
 * 微信支付、支付宝支付对账单下载（每天上午十点执行，下载前一天的对账单数据）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class PayBillDownloadJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private IWxBillService wxBillService;
	
	@Autowired
	protected IAlipayBillService alipayBillService;
	
	// 下载微信、支付宝对账单（每天上午十点执行，）
	//微信对账单每天九点开始生成前一天的对账单，建议十点之后下载
	@Scheduled(cron = "0 0 10 * * ?")
	public void downloadPayBill() {
		
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		//微信对账单下载
		//获取最后一次同步数据的时间，并依此时间的第二日作为同步起始日期（为避免因某次定时任务未执行导致当日数据未能同步）。
		Timestamp wxStartDate = null;
		List<WxBillVo> lastWXList = wxBillService.queryByCondition(null, "id desc", 0, 1);
		if (lastWXList != null && lastWXList.size() == 1) {
			wxStartDate = new Timestamp(lastWXList.get(0).getTrade_time().getTime() + 1000L*60*60*24);//后推1天
		} else {
			wxStartDate = DvDateHelper.getTimestamp("2016-08-01");
		}
		Timestamp wxEndDate = DvDateHelper.getSysTimestamp();
		
		while (wxStartDate.before(wxEndDate)) {
			List<WxBillVo> wxList = WxpayUtils.downloadBill(new SimpleDateFormat("yyyyMMdd").format(wxStartDate));
			wxBillService.insert(wxList.toArray(new WxBillVo[wxList.size()]));
			wxStartDate = new Timestamp(wxStartDate.getTime() + 1000L*60*60*24);
		}
		
		//支付宝对账单下载
		Timestamp aliStartDate = null;
		List<AlipayBillVo> lastAliList = alipayBillService.queryByCondition(null, "id desc", 0, 1);
		if (lastAliList != null && lastAliList.size() == 1) {
			aliStartDate = new Timestamp(lastAliList.get(0).getTrans_date().getTime() + 1000L*60*60*24);
		} else {
			aliStartDate = DvDateHelper.getTimestamp("2016-08-01");
		}
		Timestamp aliEndDate = DvDateHelper.getSysTimestamp();
		
		while (aliStartDate.before(aliEndDate)) {
			List<AlipayBillVo> alipayList = AlipayUtils.downloadBill(new SimpleDateFormat("yyyy-MM-dd").format(aliStartDate));
			alipayBillService.insert(alipayList.toArray(new AlipayBillVo[0]));
			aliStartDate = new Timestamp(aliStartDate.getTime() + 1000L*60*60*24);
		}
	}
}
