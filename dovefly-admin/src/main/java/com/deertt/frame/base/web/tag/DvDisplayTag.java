package com.deertt.frame.base.web.tag;

import java.util.List;

import javax.servlet.jsp.JspException;

import com.deertt.module.sys.dict.DvDictionaryData;
import com.deertt.module.sys.dict.DvDictionaryFactory;

/**
 * @author: fengcm
 * Date: 11-10-19
 * Time: 上午10:02.
 */
public class DvDisplayTag extends DvBaseTag {

	private static final long serialVersionUID = -388836001011179254L;

	public int doStartTag() throws JspException {
		String displayValue = value;
		try {
			//如果存在options，则忽略dicKeyword属性
			List<DvDictionaryData> baseOptions = null;
			if(options != null) {
				baseOptions = reWrapOption(options);
				displayValue = DvDictionaryFactory.getSingleton().getDictData(baseOptions, value).toString();
			} else {
				displayValue = DvDictionaryFactory.getSingleton().getDictData(dicKeyword, value).toString();
			}
			this.pageContext.getOut().print(displayValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
}
