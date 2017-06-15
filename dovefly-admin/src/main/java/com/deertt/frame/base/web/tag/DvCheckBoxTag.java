package com.deertt.frame.base.web.tag;

import java.util.List;

import com.deertt.module.sys.dict.DvDictionaryData;
import com.deertt.utils.helper.string.DvStringHelper;

/**
 * @author: fengcm
 * Date: 11-10-19
 * Time: 上午10:18.
 */
public class DvCheckBoxTag extends DvBaseTag {

	private static final long serialVersionUID = 1L;

	public int doStartTag() {
		try {
			StringBuilder sb = new StringBuilder();
			if (attributes == null)
				attributes = "";
			if (defaultValue == null)
				defaultValue = "";
			if (emptyText == null)
				emptyText = "";
			if (cssClass == null)
				cssClass = "";

			//如果存在options，则忽略dicKeyword属性
			List<DvDictionaryData> baseOptions = null;
			if(options != null) {
				baseOptions = reWrapOption(options);
			}else {
				baseOptions = getOptionByDic(dicKeyword);
			}
			if (baseOptions != null) {
				for(DvDictionaryData option : baseOptions) {
					String tempKey = option.getDic_key();
					String tempValue = option.getDic_value();
					if (filterValues != null && filterValues.length > 0 && DvStringHelper.arrayContainObject(filterValues, tempKey)) {
						continue;				
					}
					sb.append("<label><input type='checkbox' class='checkbox' name='").append(name).append("' value='").append(tempKey).append("' ").append(attributes);
					if (DvStringHelper.arrayContainObject((defaultValue.toString()).split(","), tempKey)) {
						sb.append(" checked='checked' ");
					}
					sb.append(" />");
					// 截去超长的字符
					int nDisplaySize = 20;
					if ( nDisplaySize >= 0 && tempValue != null && tempValue.length() > nDisplaySize) {
						tempValue = tempValue.substring(0, nDisplaySize);
					}
					sb.append(tempValue).append("</label>&nbsp;&nbsp;\r\n");
				}
			}
			this.pageContext.getOut().print(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
