package com.deertt.module.sys.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deertt.frame.base.project.SpringContextHolder;
import com.deertt.module.mm.category.util.ICategoryConstants;
import com.deertt.module.sys.city.util.ICityConstants;
import com.deertt.module.sys.dict.data.service.IDictDataService;
import com.deertt.module.sys.dict.data.vo.DictDataVo;
import com.deertt.module.sys.warehouse.util.IWarehouseConstants;

public class DvDictionaryFactory {
	
	private Map<String, String> otherDictUrlMap;

	private Map<String, List<DvDictionaryData>> dictDatasMap;

	private static DvDictionaryFactory singleton;
	
	private DvDictionaryFactory(){
		dictDatasMap = new HashMap<String, List<DvDictionaryData>>();
		otherDictUrlMap = new HashMap<String, String>();
		otherDictUrlMap.put("DIC_GOODS_CATEGORY", ICategoryConstants.SQL_QUERY_DICT);
		otherDictUrlMap.put("DIC_SYS_CITY", ICityConstants.SQL_QUERY_DICT);
		otherDictUrlMap.put("DIC_SYS_WAREHOUSE", IWarehouseConstants.SQL_QUERY_DICT);
		initDictDatas();
	}
	
	public static DvDictionaryFactory getSingleton() {
		if(singleton == null){//线程不安全的，但因为数据字典是在第一次访问即可加载的，所以并发性并不强。
			synchronized(DvDictionaryFactory.class) {
				singleton = new DvDictionaryFactory();
			}
		}
		return singleton;
	}

	/**
	 * 获得字典值集合
	 * 
	 * @param type_keyword
	 * @return
	 */
	public List<DvDictionaryData> getDictDatas(String type_keyword) {
		return dictDatasMap.get(type_keyword);
	}
	
	/**
	 * 获得JSON格式字典值集合
	 * 
	 * @param type_keyword
	 * @return 如果为空，则返回"[]"，否则返回"["dic_key1":"dic_value1","dic_key2":"dic_value2"...]"
	 */
	public String getJsonDictDatas(String type_keyword) {
		StringBuffer json = new StringBuffer("[");
		List<DvDictionaryData> dictDatas = dictDatasMap.get(type_keyword);
		if(dictDatas != null && dictDatas.size() > 0){
			for (DvDictionaryData dictData : dictDatas) {
				json.append("\"").append(dictData.getDic_key()).append("\":\"").append(dictData.getDic_value()).append("\",");
			}
		}
		if(json.length() > 1 ){
			json.deleteCharAt(json.length() - 1);
		}
		json.append("]");
		return json.toString();
	}
	
	/**
	 * 获得字典显示值
	 * 
	 * @param type_keyword
	 * @return
	 */
	public String getDictData(String type_keyword, Object dic_key) {
		List<DvDictionaryData> dictDatas = getDictDatas(type_keyword);
		return getDictData(dictDatas, dic_key);
	}
	
	/**
	 * 获得字典显示值
	 * 
	 * @param type_keyword
	 * @return
	 */
	public String getDictData(List<DvDictionaryData> dictDatas, Object dic_key) {
		if(dic_key == null || "".equals(dic_key)){
			return "";
		}
		String dic_value = dic_key.toString();
		if(dictDatas != null && dictDatas.size() > 0){
			for (DvDictionaryData dictData : dictDatas) {
				if (dic_key.toString().equals(dictData.getDic_key())) {
					dic_value = dictData.getDic_value();
					break;
				}
			}
		}
		return dic_value;
	}
	
	/**
	 * 重新加载全部字典
	 */
	public void reloadAll() {
		synchronized (singleton) {
			initDictDatas();
		}
	}
	
	/**
	 * 重新加载指定字典
	 * 
	 * @param type_keyword
	 */
	public void reload(String type_keyword) {
		synchronized (singleton) {
			reloadDictDatas(type_keyword);
		}
	}
	
	/**
	 * 用传入的数据更新数据字典，原数据被清空。
	 * 
	 * @param type_keyword
	 * @param diclist
	 */
	public void reload(String type_keyword, List<DvDictionaryData> diclist) {
		synchronized (singleton) {
			dictDatasMap.put(type_keyword, diclist);
		}
	}
	
	/**
	 * 功能: 初始化字典数据，以Map<type_keyword, List>的形势存储
	 */
	private void initDictDatas() {
		dictDatasMap.clear();
		String keyword = null;
		//加载数据字典表的数据
		List<DvDictionaryData> dictList = queryDictDatas(keyword);
		List<DvDictionaryData> tempDictList = null;
		if (dictList != null && dictList.size() > 0) {
			for (Iterator<DvDictionaryData> itrator = dictList.iterator(); itrator.hasNext();) {
				DvDictionaryData data = itrator.next();
				keyword = data.getDic_type_keyword();
				if (dictDatasMap.containsKey(keyword)) {
					tempDictList = dictDatasMap.get(keyword);
					tempDictList.add(data);
				} else {
					tempDictList = new ArrayList<DvDictionaryData>();
					tempDictList.add(data);
					dictDatasMap.put(keyword, tempDictList);
				}
			}
		}
		
		//加载其他作为数据字典的表数据
		if (otherDictUrlMap.size() > 0) {
			for (Iterator<String> iterator = otherDictUrlMap.keySet().iterator(); iterator.hasNext();) {
				keyword = iterator.next();
				dictList = queryOtherDictDatas(keyword);
				dictDatasMap.put(keyword, dictList);
			}
		}
	}
	
	/**
	 * 功能: 初始化字典数据，以Map<type_keyword, List>的形势存储
	 * 
	 * @param keyword 根据字典类型关键词初始化字典值，如果为空或空值，则初始化全部。
	 */
	private void reloadDictDatas(String keyword) {
		if (StringUtils.isNotEmpty(keyword)) {
			dictDatasMap.remove(keyword);
			//加载数据字典表的数据
			List<DvDictionaryData> dictList = queryDictDatas(keyword);
			if (dictList == null || dictList.size() == 0) {
				if (otherDictUrlMap.containsKey(keyword)) {
					dictList = queryOtherDictDatas(keyword);
				}
			}
			dictDatasMap.put(keyword, dictList);
		}
	}
	
	/**
	 * 查询数据字典集合
	 * 
	 * @param keyword 根据字典类型关键词查找，如果为空或空值，则查询全部。
	 * @return
	 */
	private List<DvDictionaryData> queryDictDatas(String keyword){
		String queryCondition = null;
		if(StringUtils.isNotEmpty(keyword)){
			queryCondition = "t_type.keyword = '" + keyword + "'";
		}
		List<DvDictionaryData> dictList = new ArrayList<DvDictionaryData>();
		IDictDataService dictDataService = SpringContextHolder.getBean(IDictDataService.class);
		List<DictDataVo> dictDatas = dictDataService.queryAllEnabledDictDatas(queryCondition, null);
		if (dictDatas != null && dictDatas.size() > 0) {
			for (Iterator<DictDataVo> itrator = dictDatas.iterator(); itrator.hasNext();) {
				DictDataVo dictDataVo = itrator.next();
				DvDictionaryData data = new DvDictionaryData();
				keyword = dictDataVo.getKeyword();
				data.setDic_type_keyword(keyword);
				data.setDic_key(dictDataVo.getDic_key());
				data.setDic_value(dictDataVo.getDic_value());
				dictList.add(data);
			}
		}
		
		return dictList;
	}
	
	/**
	 * 查询其他表数据作为字典集合
	 * @param keyword
	 * @return
	 */
	public List<DvDictionaryData> queryOtherDictDatas(String keyword) {
		List<DvDictionaryData> results = new ArrayList<DvDictionaryData>();
		if (StringUtils.isNotEmpty(keyword)) {
			IDictDataService dictDataService = SpringContextHolder.getBean(IDictDataService.class);
			String sql = otherDictUrlMap.get(keyword);
			List<Map<String, Object>> maps = dictDataService.getDao().queryForMaps(sql);
			if (maps != null) {
				for (Map<String, Object> map : maps) {
					DvDictionaryData data = new DvDictionaryData();
					data.setDic_type_keyword(keyword);
					data.setDic_key(map.get("k").toString());
					data.setDic_value(map.get("v").toString());
					results.add(data);
				}
			}
		}
		return results;
	}
}
