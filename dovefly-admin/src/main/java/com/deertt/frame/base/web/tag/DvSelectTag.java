package com.deertt.frame.base.web.tag;

import java.util.List;

import javax.servlet.jsp.JspException;

import com.deertt.module.sys.dict.DvDictionaryData;
import com.deertt.utils.helper.string.DvStringHelper;

/**
 * @author: fengcm
 * Date: 11-10-19
 * Time: 上午10:02.
 */
public class DvSelectTag extends DvBaseTag {

	private static final long serialVersionUID = 1L;

	public int doStartTag() throws JspException {
		try {
			StringBuilder sb = new StringBuilder();
			
			if (attributes == null)
				attributes = "";
			if (defaultValue == null)
				defaultValue = "";
			if (emptyText == null)
				emptyText = "--全部--";
			if (cssClass == null)
				cssClass = "";

			//如果存在options，则忽略dicKeyword属性
			List<DvDictionaryData> baseOptions = null;
			if(options != null) {
				baseOptions = reWrapOption(options);
			}else {
				baseOptions = getOptionByDic(dicKeyword);
			}
			
			sb.append("<select name='").append(name).append("'");
			sb.append(" class='").append(cssClass).append("' ").append(attributes).append(" >\r\n");
			
			if(hasEmpty) {
				sb.append("<option value=''>").append(emptyText).append("</option>\r\n");
			}
			if (baseOptions != null) {
				for(DvDictionaryData option : baseOptions) {
					String tempKey = option.getDic_key();
					String tempValue = option.getDic_value();
					if (filterValues != null && filterValues.length > 0 && DvStringHelper.arrayContainObject(filterValues, tempKey)) {
						continue;				
					}
					sb.append("<option value='").append(tempKey).append("'");
				   
					if (tempKey.equals(defaultValue.toString())) {
						sb.append(" selected='selected' ");
					}
					// 截去超长的字符
					int nDisplaySize = 100;
					if ( nDisplaySize >= 0 && tempValue != null && tempValue.length() > nDisplaySize) {
						tempValue = tempValue.substring(0, nDisplaySize);
					}
					sb.append(">").append(tempValue).append("</option>\r\n");
				}
			}
			
			sb.append("</select>\r\n");
			this.pageContext.getOut().print(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
}
