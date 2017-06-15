package com.deertt.module.sys.dict;

public class DicConstants {
	
	class AUDIT_STATE{
		
		/** 审批状态：已保存 */
		final public static String SAVED = "1";
		
		/** 审批状态：审批中 */
		final public static String SUBMITTED  = "2";
		
		/** 审批状态：已退回 */
		final public static String RETURNED = "3";
		
		/** 审批状态：已审批 */
		final public static String APPROVED = "4";
		
		/** 审批状态：已完成 */
		final public static String COMPLETED = "5";
		
		/** 审批状态：已撤销 */
		final public static String CANCELLED = "6";
	}

}
