package com.deertt.utils.helper;

import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.frame.base.vo.DvBaseVo;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotWritablePropertyException;

import com.deertt.utils.helper.string.DvStringHelper;


public class DvPopulateHelper {
	static Logger log = DvLogHelper.getLogger(DvPopulateHelper.class);
	static IPopulateParser parser = new DvPopulateParser();

	/**
	 * 针对多个vo，按出现的顺序循环注值
	 * 
	 * @param <T> vo泛型
	 * @param classVo vo的class对象
	 * @param request 存放form提交数据的request
	 * @param keyName 某个不为null(可以是""空字符串)的关键字段,此字段的数组length决定注值vo的length
	 * @return
	 */
	public static<T> List<T> populateVos(Class<T> classVo, HttpServletRequest request, String keyName) {
		return populateVos(classVo, request, keyName, "");
	}
	
	/**
	 * 针对多个vo，按出现的顺序循环注值
	 * 
	 * @param <T>
	 *			vo泛型
	 * @param classVo
	 *			vo的class对象
	 * @param request
	 *			存放form提交数据的request
	 * @param keyName
	 *			某个不为null(可以是""空字符串)的关键字段,此字段的数组length决定注值vo的length
	 * @param namespacePrefix
	 *			命名空间前缀，从request.getParameter取值时会加上namespacePrefix
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> populateVos(Class<T> classVo, HttpServletRequest request, String keyName, String namespacePrefix) {
		if (namespacePrefix != null) {
			keyName = namespacePrefix + keyName;
		}
		List<T> lvo = new ArrayList<T>();
		if (request.getParameterValues(keyName) == null) {
			return lvo;
		}
		int dv_record_length = request.getParameterValues(keyName).length;
		Set<String> sKey = request.getParameterMap().keySet();
		Map<String, String>[] mps = new HashMap[dv_record_length];
		for (int i = 0; i < mps.length; i++) {
			mps[i] = new HashMap<String, String>();
		}
		for (int i = 0; i < dv_record_length; i++) {
			for (String key : sKey) {
				String[] values = request.getParameterValues(key);
				String shortKey = key;
				if (namespacePrefix != null && key.length() > namespacePrefix.length()) {
					shortKey = key.substring(namespacePrefix.length());
				}
				if (values != null && values.length > i) {
					mps[i].put(shortKey, values[i]);
				}
			}
		}
		for (int i = 0; i < dv_record_length; i++) {
			try {
				T vo = classVo.newInstance();
				populate(vo, mps[i], null, null, null, null);
				lvo.add(vo);
			} catch (Exception e) {
				log.warn("populateVos " + classVo.getName() + " " + e.toString());
			}
		}
		return lvo;
	}
	
	/**
	 * 功能: 根据源对象的属性值填充目标对象
	 *
	 * @param destObj 目标对象(DvBaseVo, DvCommonVo, Map)
	 * @param sourceObj 来源对象(ServletRequest, ResultSet, DvBaseVo, DvCommonVo, Map)
	 * @return 成功的个数
	 */
	public static int populate(Object destObj, Object sourceObj) {
		return populate(destObj, sourceObj, null, null, null, null);
	}

	/**
	 * 功能: 根据源对象的属性值填充目标对象
	 *
	 * @param destObj 目标对象(ServletRequest, ResultSet, DvBaseVo)
	 * @param sourceObj 来源对象(ServletRequest, ResultSet, DvBaseVo, DvCommonVo, Map)
	 * @param fieldMap field名对应，比如字段名称不一致的，要实现Object.setName(ResultSet.getString("myname"))，需提前fieldMap.put("name", "myname")
	 * @param ignoreField 对destObj中忽略的field名，即这些字段不填充
	 * @param iterateObj 自定义
	 * @param getValue
	 * @return 成功的个数
	 */
	public static int populate(Object destObj, Object sourceObj, Map<String, String> fieldMap, String[] ignoreField, IDvIterateObj iterateObj, IDvGetValue getValue) {
		if(destObj == null || sourceObj == null) {
			return 0;
		}
		boolean pullData = true;
		if(destObj instanceof ResultSet || destObj instanceof ServletRequest) {
			throw new RuntimeException("destObj是不支持的注值类型:" + destObj.getClass());
		}
		if(destObj instanceof Map) {//如果目标是Map类，从右到左<--
			pullData = false;
		} else if(sourceObj instanceof ResultSet) { //如果来源是ResultSet，从右到左-->
			pullData = false;
		}
		return populate(destObj, sourceObj, fieldMap, ignoreField, iterateObj, getValue, pullData);
	}
	
	/**
	 * 功能: 
	 *
	 * @param destObj 目标对象(ServletRequest, ResultSet, DvBaseVo)
	 * @param sourceObj 来源对象
	 * @param fieldMap field名对应，比如要实现Object.setName(ResultSet.getString("myname"))，需提前fieldMap.put("name", "myname")
	 * @param ignoreField 对destObj中忽略的field名
	 * @param iterateObj 自定义
	 * @param getValue
	 * @param pullData true是从destObj取出可能的set方法，再从sourceObj调用get方法
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static int populate(Object destObj, Object sourceObj, Map<String, String> fieldMap, String[] ignoreField, IDvIterateObj iterateObj, IDvGetValue getValue, final boolean pullData) {
		int sum = 0;
		if(getValue != null) {
			//采用自定义的取值
		} else if (sourceObj instanceof Map) {
			getValue = new IDvGetValue() {
				public Object getValue(Object sourceObj, String key) {
					return ((Map<String, Object>)sourceObj).get(key);
				}
				public boolean containKey(Object sourceObj, String key) {
					return ((Map<String, Object>)sourceObj).containsKey(key);
				}
				public Map<Object, Object> getKeyValueMap(Object sourceObj) {
					return (Map<Object, Object>)sourceObj;
				}
			};
		}  else if (sourceObj instanceof ServletRequest) {
			getValue = new IDvGetValue() {
				public Object getValue(Object sourceObj, String key) {
					ServletRequest request = ((ServletRequest)sourceObj);
					String[] values = request.getParameterValues(key);
					if(values == null || values.length == 0) {
						return null;
					} else if(values.length == 1) {
						return request.getParameter(key);
					} else {
						return DvStringHelper.parseToSQLString(request.getParameterValues(key));
					}
				}
				public boolean containKey(Object sourceObj, String key) {
					return ((ServletRequest)sourceObj).getParameterMap().containsKey(key);
				}
				public Map<Object, Object> getKeyValueMap(Object sourceObj) {
					Map<String, String[]> mRequest = ((ServletRequest)sourceObj).getParameterMap();
					Map<Object, Object> mReturn = new HashMap<Object, Object>();
					for(String key: mRequest.keySet()) {
						mReturn.put(key, DvStringHelper.parseToString(mRequest.get(key)));
					}
					return mReturn;
				}
			};
		} else if (sourceObj instanceof ResultSet) {
			getValue = new IDvGetValue() {
				public Object getValue(Object sourceObj, String key) {
					ResultSet rs = (ResultSet)sourceObj;
					Object obj = getResultValue(rs, key);
					return obj;
				}
				public boolean containKey(Object sourceObj, String key) {
					ResultSet rs = (ResultSet)sourceObj;
					try {
						ResultSetMetaData rsmd = rs.getMetaData();
						for (int i=1; i<=rsmd.getColumnCount(); i++) {
							if(rsmd.getColumnLabel(i).equalsIgnoreCase(key)) {
								return true;
							}
						}
					} catch (SQLException e) {
						throw new RuntimeException("ResultSet containKey('" + key + "') error", e);
					}

				   return false;
			   }
				public Map<Object, Object> getKeyValueMap(Object sourceObj) {
					Map<Object, Object> m = new LinkedHashMap<Object, Object>();//保存取值有序
					ResultSet rs = (ResultSet)sourceObj;
					try {
						ResultSetMetaData rsmd = rs.getMetaData();
						for (int i=1; i<=rsmd.getColumnCount(); i++) {
							String columnLabel = rsmd.getColumnLabel(i);
							//如果出现小写字母后面紧跟大写字母，则判定为驼峰式规范，保持大小写和sql一致
							if(!Pattern.compile("(?<=[a-z])[A-Z]").matcher(columnLabel).find()) {
								columnLabel = columnLabel.toLowerCase();
							}
							m.put(columnLabel, getResultValue(rs, columnLabel));
						}
					} catch (SQLException e) {
						throw new RuntimeException("ResultSet getKeyValueMap() error", e);
					}
					return m;
				}
			};
		} else if (sourceObj instanceof DvBaseVo) {
			getValue = new IDvGetValue() {
				public Object getValue(Object sourceObj, String key) {
					return DvVoHelper.getVoFieldValue(sourceObj, key);
				}
				public boolean containKey(Object sourceObj, String key) {
					return DvVoHelper.voContainMethod(sourceObj, "get" + DvStringHelper.toFirstUpperCase(key));
				}
				public Map<Object, Object> getKeyValueMap(Object sourceObj) {
					//TODO 实现VO-->Map的转化
					return null;
				}
				
			};
		}
		
		//注值
		if(iterateObj == null) {
			if(destObj instanceof Map) {
				iterateObj = new IDvIterateObj() {
					public int iterateObj(Object destObj, Object sourceObj, Map<String, String> fieldMap, String[] ignoreField, IDvGetValue getValue) {
						int sum = 0;
						Map<Object, Object> mDestination = (Map<Object, Object>)destObj;
						if(pullData) { //从Map取出所有get方法的值
							for(Object key : mDestination.keySet()) {
								String keyStr = key != null ? key.toString() : null;
								mDestination.put(key, getValue.getValue(sourceObj, keyStr));
							}
						} else { //一次性从sourceObj取出所有值，赋值给Map
							Map<Object, Object> mSource = getValue.getKeyValueMap(sourceObj);
							for(Object key : mSource.keySet()) {
								mDestination.put(key, mSource.get(key));
							}
						}
						return sum;
					}
				};
			} else if(destObj instanceof DvBaseVo || destObj instanceof Serializable) {
				iterateObj = new IDvIterateObj() {
					public int iterateObj(Object destObj, Object sourceObj, Map<String, String> fieldMap, String[] ignoreField, IDvGetValue getValue) {
						Object vo = destObj;
						int sum = 0;
						BeanWrapper bw = new BeanWrapperImpl(vo);
						PropertyDescriptor pd[] = bw.getPropertyDescriptors();
						Map<Object, Object> mSourceAll = null;
			   			if(!pullData) { //一次性从sourceObj取出所有值，赋值给Map
			   				mSourceAll = getValue.getKeyValueMap(sourceObj);
						}
						for (int i = 0; i < pd.length; i++) {
							try {
								String currentKey = pd[i].getName();
								if (!"class".equals(currentKey) && !DvStringHelper.arrayContainString(ignoreField, currentKey)) {
									if(fieldMap != null) {
										//如果fieldMap只有{"", "$0"},是ajax注值模式
										if(fieldMap.size() == 1 && fieldMap.get("") != null) {
											currentKey = currentKey + fieldMap.get("");
										} else if(fieldMap.get(currentKey) != null) {
											currentKey = fieldMap.get(currentKey).toString();
										}
									}
									Object value = null;
									if(pullData) {
										value = getValue.getValue(sourceObj, currentKey);
									} else  {
										if(mSourceAll != null) {
											value = mSourceAll.get(currentKey);
										} else {
											log.warn("从source到destination注值模式下，sourceObj.getKeyValueMap()是null");
										}
									}
									if(value == null) {
										continue;
									}
									Class<?> propertyType = pd[i].getPropertyType();
									Object valueFinal = parser.parse(propertyType, value);
									if(valueFinal == null) {
										continue;
									}
									bw.setPropertyValue(pd[i].getName(), valueFinal);							
									sum ++;
								}
							} catch (NotWritablePropertyException e) {
								continue;
							} catch (RuntimeException e) {
								if(e.getCause() instanceof NoSuchMethodException) {
									continue;
								} else {
									log.warn(e.toString());
									throw e;
								}
							}
						}
						return sum;
					}
				};
			} else {
				throw new RuntimeException("destObj是不支持的类型:" + destObj.getClass().getName());
			}
		}
		sum = iterateObj.iterateObj(destObj, sourceObj, fieldMap, ignoreField, getValue);
		return sum;
	}

	/**
	 * 从ResultSet取值
	 * 
	 * @param rs 查询结果集
	 * @param columnName 字段名
	 * @return
	 */
	public static Object getResultValue(ResultSet rs, String columnName) {
		try {
			Object resultObj = rs.getObject(columnName);
			if(resultObj instanceof Date) {
				if(IGlobalConstants.DATABASE_PRODUCT_NAME_ORACLE.equals(DvConfig.getDatabaseProductName())) {
					resultObj = rs.getTimestamp(columnName);
				}
			} else if (resultObj instanceof Clob) {
				StringBuilder sb = new StringBuilder();
				Reader reader = ((Clob) resultObj).getCharacterStream();
				BufferedReader br = new BufferedReader(reader);
				String tempStr = null;
				while ((tempStr = br.readLine()) != null) {
					sb.append(tempStr + "\n");
				}
				br.close();
				reader.close();
				return sb.toString();
			} else if (resultObj instanceof Blob) { //Blob输出到byte[]返回
				InputStream is = ((Blob) resultObj).getBinaryStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = bis.read(b)) != -1) {
					baos.write(b, 0, len);
				}
				bis.close();
				is.close();
				return baos.toByteArray();
			}
			return resultObj;
		} catch (SQLException e) {
			if(e.toString().indexOf("列名无效") > -1 || e.toString().indexOf("Invalid column name") > -1) {
				return null;
			} else {
				return null;
			}
		} catch (IOException e) {
			throw new RuntimeException("ResultSet获取" + columnName + "出错", e);
		}
	}

	public static Map<String, Object> getRequestMap(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		for (Enumeration<String> it = request.getParameterNames(); it.hasMoreElements();) {
			String key = it.nextElement();
			if (request.getParameterValues(key).length > 1) {
				params.put(key, request.getParameterValues(key));
			} else {
				params.put(key, request.getParameter(key));
			}
		}
		return params;
	}
}