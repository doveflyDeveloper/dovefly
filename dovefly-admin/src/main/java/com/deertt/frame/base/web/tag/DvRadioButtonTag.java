package com.deertt.frame.base.web.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.sys.dict.DvDictionaryData;
import com.deertt.utils.helper.string.DvStringHelper;

/**
 * @author: fengcm
 * Date: 11-10-19
 * Time: 上午10:35.
 */
public class DvRadioButtonTag extends DvBaseTag {
	private static final long serialVersionUID = 8248756476934340948L;

	public int doStartTag() throws JspException {
		try {
			StringBuilder sb = new StringBuilder();
			if (attributes == null)
				attributes = "";
			if (defaultValue == null)
				defaultValue = "";
			if (emptyText == null)
				emptyText = "全部";
			if (cssClass == null)
				cssClass = "";
			
			//如果存在options，则忽略dicKeyword属性
			List<DvDictionaryData> baseOptions = null;
			if (options != null) {
				baseOptions = reWrapOption(options);
			} else {
				baseOptions = getOptionByDic(dicKeyword);
			}

			Map<String, Object> renderDataMap = (Map<String, Object>) pageContext.getRequest().getAttribute(IGlobalConstants.REQUEST_RADIO_BUTTON_DATA_MAP);
			
			sb.append("<div class='radioButtonContainer'>");
			if(hasEmpty) {
				String tempKey = "";
				String tempValue = emptyText;
				if (renderDataMap != null) {
					tempValue = tempValue + "(" + (renderDataMap.get(tempKey) == null ? "0" : renderDataMap.get(tempKey)) + ")";
				}
				if (filterValues != null && filterValues.length > 0 && DvStringHelper.arrayContainObject(filterValues, tempKey)) {
					
				} else {
					if (tempKey.equals(defaultValue.toString())) {
						sb.append("<label class='red'>");
					} else {
						sb.append("<label>");
					}
					sb.append("<input type='radio' class='radioButton' name='").append(name).append("' value='").append(tempKey).append("' ").append(attributes);
					if (tempKey.equals(defaultValue.toString())) {
						sb.append(" checked='checked' ");
					}
					sb.append(" /><span>");
					// 截去超长的字符
					int nDisplaySize = 20;
					if ( nDisplaySize >= 0 && tempValue != null && tempValue.length() > nDisplaySize) {
						tempValue = tempValue.substring(0, nDisplaySize);
					}
					sb.append(tempValue).append("</span></label>\r\n");
				}
			}
			if (baseOptions != null) {
				for(DvDictionaryData option : baseOptions) {
					String tempKey = option.getDic_key();
					String tempValue = option.getDic_value();
					if (renderDataMap != null) {
						tempValue = tempValue + "(" + (renderDataMap.get(tempKey) == null ? "0" : renderDataMap.get(tempKey)) + ")";
					}
					if (filterValues != null && filterValues.length > 0 && DvStringHelper.arrayContainObject(filterValues, tempKey)) {
						continue;
					}
					if (tempKey.equals(defaultValue.toString())) {
						sb.append("<label class='red'>");
					} else {
						sb.append("<label>");
					}
					sb.append("<input type='radio' class='radioButton' name='").append(name).append("' value='").append(tempKey).append("' ").append(attributes);
					if (tempKey.equals(defaultValue)) {
						sb.append(" checked='checked' ");
					}
					sb.append(" /><span>");
					// 截去超长的字符
					int nDisplaySize = 20;
					if ( nDisplaySize >= 0 && tempValue != null && tempValue.length() > nDisplaySize) {
						tempValue = tempValue.substring(0, nDisplaySize);
					}
					sb.append(tempValue).append("</span></label>\r\n");
				}
			}
			sb.append("</div>\r\n");
			this.pageContext.getOut().print(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

}
