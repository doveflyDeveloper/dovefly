package com.deertt.frame.base.web.tag;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONObject;

import com.deertt.module.sys.dict.DvDictionaryData;
import com.deertt.module.sys.dict.DvDictionaryFactory;

/**
 * @author: fengcm
 * Date: 2013-10-19
 * Time: 上午9:55.
 */
public class DvBaseTag extends TagSupport {
	private static final long serialVersionUID = -1401431096999186953L;
	
	/** 字段名称 */
	protected String name;
	
	/** CSS样式类名 */
	protected String cssClass;
	
	/** 属性字符串 */
	protected String attributes;
	
	/** 数据字典类型关键词 */
	protected String dicKeyword;
	
	/** 是否包含默认空值 */
	protected boolean hasEmpty;
	
	/** 默认空置的显示文本 */
	protected String emptyText;
	
	/** 默认选中的值 */
	protected Object defaultValue;
	
	/** 外部传入的参数列表 */
	protected Object options;
	
	/** 从参数列表中过滤掉指定的值 */
	protected Object[] filterValues;
	
	protected String ignoreValues;
	
	protected String value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getDicKeyword() {
		return dicKeyword;
	}

	public void setDicKeyword(String dicKeyword) {
		this.dicKeyword = dicKeyword;
	}

	public boolean isHasEmpty() {
		return hasEmpty;
	}

	public void setHasEmpty(boolean hasEmpty) {
		this.hasEmpty = hasEmpty;
	}

	public String getEmptyText() {
		return emptyText;
	}

	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Object getOptions() {
		return options;
	}

	public void setOptions(Object options) {
		this.options = options;
	}
	
	public String getIgnoreValues() {
		return ignoreValues;
	}

	public void setIgnoreValues(String ignoreValues) {
		this.ignoreValues = ignoreValues;
		if (ignoreValues != null) {
			this.filterValues = ignoreValues.split(",");
		}
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<DvDictionaryData> reWrapOption(Object options) {
		List<DvDictionaryData> dicDatas = new ArrayList<DvDictionaryData>();
		if (options instanceof String){
			//String options = "{\"a\":\"123\",\"b\":\"123\"}";
			JSONObject jsonObj = JSONObject.fromObject(options);
			//JSONArray array = JSONArray.fromObject(options);
			for (Object key : jsonObj.keySet()) {
				DvDictionaryData obj = new DvDictionaryData();
				obj.setDic_key(key.toString());
				obj.setDic_value(jsonObj.get(key).toString());
				dicDatas.add((DvDictionaryData)obj);
			}
		} else if(options instanceof Object[]){  
			Object[] objs = (Object[]) options;
			for(Object obj : objs){  
				if(obj instanceof DvDictionaryData){
					dicDatas.add((DvDictionaryData)obj);
				} else {
					System.out.println("options中存在非DictionaryData类型的对象，此对象已被忽略");
				}
			}
		} else if(options instanceof List) {
			List objs = (List) options;
			for(Object obj : objs){  
				if(obj instanceof DvDictionaryData){
					dicDatas.add((DvDictionaryData)obj);
				} else {
					System.out.println("options中存在非DictionaryData类型的对象，此对象已被忽略");
				}
			}
		} else if(options instanceof Map) {
			Map<String, String> objs = (Map<String, String>)options;
			for (String key : objs.keySet()) {
				DvDictionaryData obj = new DvDictionaryData();
				obj.setDic_key(key);
				obj.setDic_value(objs.get(key));
				dicDatas.add((DvDictionaryData)obj);
			}
		} else {
			System.out.println("options属性的值非法，只能是Object[]，List，Map类型");
		}
		return dicDatas;
	}
	
	public List<DvDictionaryData> getOptionByDic(String dicKeyword){
		return DvDictionaryFactory.getSingleton().getDictDatas(dicKeyword);
	}

}
