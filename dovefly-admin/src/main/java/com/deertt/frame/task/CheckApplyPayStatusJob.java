package com.deertt.frame.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deertt.common.pay.util.WxpayUtils;
import com.deertt.common.pay.vo.PayCheckResult;
import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.module.fund.apply.service.IApplyService;
import com.deertt.module.fund.apply.vo.ApplyVo;

/**
 * 提现处理支付状态定时查询（每十分钟执行一次）
 * @author fengcm
 *
 */
@Component
@Lazy(false)
public class CheckApplyPayStatusJob {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private IApplyService applyService;

	@Scheduled(cron = "15 0/10 * * * ?")
	public void checkApplyPayStatus() {
		if (!ApplicationConfig.isProduction()) {
			return;
		}
		
		// 查询五分钟前的处理中的提现申请
		List<ApplyVo> applies = applyService.queryByCondition("status = '" + ApplyVo.STATUS_DEALING + "' AND pay_time < DATE_SUB(now(), INTERVAL 10 MINUTE)", null);
		if (applies != null && applies.size() > 0) {
			for (ApplyVo bean : applies) {
				try {
					if (ApplyVo.APPLY_TO_WXPAY.equals(bean.getApply_to())) {
						PayCheckResult payCheckResult = WxpayUtils.getTransferInfo(bean.getBill_code());
						if (payCheckResult.isSuccess()) {
							applyService.paySuccessCallBack(bean.getBill_code());
						} else {
							applyService.payFailCallBack(bean.getBill_code(), payCheckResult.getMessage());
						}
					} else if (ApplyVo.APPLY_TO_ALIPAY.equals(bean.getApply_to())) {
						logger.info("暂时没有找到支付宝批量付款结果查询的接口");
					}
//					logger.info("----------");
//					logger.info(bean);
//					logger.info("----------");
					
				} catch (Exception e) {
					logger.info(e);
					e.printStackTrace();
				}
			}
		}
	}
}
