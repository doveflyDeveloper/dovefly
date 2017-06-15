package com.deertt.utils.helper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotReadablePropertyException;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.tcommonfile.vo.CommonFileAware;
import com.deertt.module.tcommonfile.vo.TCommonFileVo;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.string.DvStringHelper;

/**
 * 作用是实现自动处理VO操作中的一些事情
 */
public final class DvVoHelper implements IGlobalConstants {
	private DvVoHelper() {
	}

	/**
	 * 借助BeanWrapper循环Vo
	 * 
	 * @param obj 输入一个VO
	 * @return 被替换的值个数
	 */
	public static int accessVo(Object obj, ITransctVoField transctVoField) {
		int returnCount = 0;
		try {
			BeanWrapper bw = new BeanWrapperImpl(obj);
			PropertyDescriptor pd[] = bw.getPropertyDescriptors();
			for (int i = 0; i < pd.length; i++) {
				try {
					returnCount += transctVoField.transctVo(bw, pd[i]);
				} catch (ClassCastException e) {
					//e.printStackTrace();
					continue;
				} catch (NotReadablePropertyException e) {
					//e.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			//RmLogHelper.getLogger(RmVoHelper.class).error("accessVo " + (obj != null ? obj.getClass() : "") + " " + e.toString());
		}
		return returnCount;
	}

	/**
	 * 把VO中值为null的数据一律替换成""
	 * 
	 * @param obj 输入一个VO
	 * @return 被替换的值个数
	 */
	static int null2Nothing(Object obj) {
		return accessVo(obj, new ITransctVoField() {
			public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
				if (!pd.getName().equals("class")) {
					if (bw.getPropertyValue(pd.getName()) == null) {
						if(bw.getPropertyType(pd.getName()).getName().equals("java.sql.Timestamp")) {
							bw.setPropertyValue(pd.getName(), null); 
						} else {
							bw.setPropertyValue(pd.getName(), ""); 
						}
						return 1;
					} else {
						return 0;
					}
				} else {
					return 0;
				}
			}
		});
	}
	
	/**
	 * 把VO中的关键字值一律替换成ASCII码表示，同时把null换为""
	 * 
	 * @param obj 输入一个VO
	 * @return 操作次数
	 */
	public static int replaceToHtml(Object obj) {
		return replaceToHtml(obj, null);
	}

	/**
	 * 把VO中的关键字值一律替换成ASCII码表示，同时把null换为""
	 * 
	 * @param obj 输入一个VO
	 * @param ignoreName 
	 * @return 操作次数
	 */
	public static int replaceToHtml(Object obj, final String[] ignoreName) {
		return accessVo(obj, new ITransctVoField() {
			public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
				if (!pd.getName().equals("class")) {
					if (ignoreName != null && ignoreName.length > 0 && DvStringHelper.arrayContainString(ignoreName, pd.getName())) {
						return 0;
					}
					String tempValue = (String) bw.getPropertyValue(pd.getName());
					if (tempValue == null && "java.lang.String".equals(pd.getPropertyType().getName())) {
						bw.setPropertyValue(pd.getName(), "");
						return 1;
					} else if("java.lang.String".equals(pd.getPropertyType().getName())){
						bw.setPropertyValue(pd.getName(), DvStringHelper.replaceStringToHtml(tempValue));
						return 1;
					} else {
						return 0;
					}
				} else {
					return 0;
				}
			}
		});

	}

	/**
	 * 把VO中的关键字值按指定规则替换成ASCII码表示，同时把null换为""
	 * 
	 * @param obj 输入一个VO
	 * @return 操作次数
	 */
	public static int replaceToScript(Object obj) {
		return accessVo(obj, new ITransctVoField() {
			public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
				if (!pd.getName().equals("class")) {
					String tempValue = (String) bw.getPropertyValue(pd.getName());
					if (tempValue == null && "java.lang.String".equals(pd.getPropertyType().getName())) {
						bw.setPropertyValue(pd.getName(), "");
						return 1;
					} else if("java.lang.String".equals(pd.getPropertyType().getName())) {
						bw.setPropertyValue(pd.getName(), DvStringHelper.replaceStringToScript(tempValue));
						return 1;
					} else {
						return 0;
					}
				} else {
					return 0;
				}
			}
		});
	}
	
	/**
	 * 将对象转换为Map，key为属性名，value为对应的值
	 * 
	 * @param obj 对象
	 * @return
	 */
	public static Map<Object, Object> getMapFromVo(Object obj) {
		Map<Object, Object> rtMap = new TreeMap<Object, Object>();
		if(obj == null) {
			return rtMap;
		}
		BeanWrapper bw = new BeanWrapperImpl(obj);
		PropertyDescriptor pd[] = bw.getPropertyDescriptors();

		for (int i = 0; i < pd.length; i++) {
			try {
				if (!pd[i].getName().equals("class")) {
					String tempKey = pd[i].getName();
					rtMap.put(tempKey, bw.getPropertyValue(pd[i].getName()));
				}
			} catch (ClassCastException e) {
				e.printStackTrace();
				continue;
			} catch (NotReadablePropertyException e) {
				e.printStackTrace();
				continue;
			}
		}
		return rtMap;
	}
	
	/**
	 * 从vo列表中获得表单值列表
	 * @param lvo
	 * @return
	 */
	public static List<Map<Object, Object>> getMapsFromVos(List<Object> lvo) {
		if(lvo == null) {
			return null;
		}
		List<Map<Object, Object>> result = new ArrayList<Map<Object, Object>>(lvo.size());
		for(Object obj : lvo) {
			result.add(getMapFromVo(obj));
		}
		return result;
	}
	

	
	/**
	 * 功能: 从Map的value中取出Set
	 *
	 * @param mValue
	 * @return
	 */
	public static Set<Object> getSetFromMapValue(Map<Object, Object> mValue) {
		Set<Object> s = new LinkedHashSet<Object>();
		for(Iterator<Object> itMValue = mValue.keySet().iterator(); itMValue.hasNext(); ) {
			s.add(mValue.get(itMValue.next()));
		}
		return s;
	}
	
	/**
	 * 从request中获取表单值
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getMapFromRequest(HttpServletRequest request) {
		return getMapFromRequest(request, null);
	}

	/**
	 * 从request中获取表单值
	 * 
	 * @param request
	 * @param aNeedName 关注的key值 
	 * @return
	 */
	public static Map<String, Object> getMapFromRequest(HttpServletRequest request, String[] aNeedName) {
		Map<String, Object> rtMap = new TreeMap<String, Object>();
		Iterator<String> itParams = request.getParameterMap().keySet().iterator();
		while (itParams.hasNext()) {
			String tempKey = itParams.next();
			if (aNeedName != null && !DvStringHelper.arrayContainString(aNeedName, tempKey)) {
				continue;
			}
			String[] tempArray = request.getParameterValues(tempKey);
			if (tempArray == null || tempArray.length == 0) {
				continue;
			}
			if (tempArray.length == 1) {
				rtMap.put(tempKey, request.getParameter(tempKey));
			} else {  //杜绝了相同value的提交值多次被回写
				Set<String> sUniqueValue = new HashSet<String>();
				for(int i=0; i<tempArray.length; i++) {
					sUniqueValue.add(tempArray[i]);
				}
				rtMap.put(tempKey, sUniqueValue.toArray(new String[0]));
			}
		}
		return rtMap;
	}
	  
   
	/**
	 * 对Object列表打“创建时间、创建IP、创建人员ID、排序字段、启用状态”等戳
	 * 
	 * @param request 来自页面的请求
	 * @param collection VO列表
	 */
	public static int markCreateStamp(final HttpServletRequest request, Collection<Object> collection) {
		int result = 0;
		for(Object obj : collection) {
			result += markCreateStamp(request, obj);
		}
		return result;
	}
	
	/**
	 * 对Object打“创建时间、创建IP、创建人员ID、排序字段、启用状态”等戳
	 * 
	 * @param request 来自页面的请求
	 * @param myVo 输入一个VO
	 */
	public static int markCreateStamp(final HttpServletRequest request, Object obj) {
		return accessVo(obj, new ITransctVoField() {
			public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
				if (!pd.getName().equals("class")) {
					if (DvStringHelper.arrayContainString(DESC_CREATE_TIME, pd.getName())) {//创建时间
						if(bw.getPropertyType(pd.getName()).getName().equals("java.sql.Timestamp")) {
							bw.setPropertyValue(pd.getName(), DvDateHelper.getSysTimestamp()); 
						} else {
							bw.setPropertyValue(pd.getName(), DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy-MM-dd HH:mm:ss")); 
						}
						return 1;
					} else if (DvStringHelper.arrayContainString(DESC_CREATE_IP, pd.getName()) && request != null) {//创建IP
						bw.setPropertyValue(pd.getName(), DvJspHelper.getRemoteIp(request));
						return 1;
					} else if (DvStringHelper.arrayContainString(DESC_CREATE_USER_ID, pd.getName()) && request != null) {//创建人员
						bw.setPropertyValue(pd.getName(), LoginHelper.getUserId());
						return 1;
					} else if (DESC_ORDER_CODE.equals(pd.getName())) {//排序字段
						bw.setPropertyValue(pd.getName(), 10000);
						return 1;
					} else if (DESC_USABLE_STATE.equals(pd.getName())) {//可用状态
						bw.setPropertyValue(pd.getName(), DV_YES);
						return 1;
					} else {
						return 0;
					}
				} else {
					return 0;
				}
			}
		});
	}
	
	/**
	 * 对Object打“修改时间、修改IP、修改人员ID”等戳
	 * 
	 * @param request 来自页面的请求
	 * @param collection VO列表
	 */
	public static int markModifyStamp(final HttpServletRequest request, Collection<Object> collection) {
		int result = 0;
		if (collection != null && collection.size() > 0) {
			for(Object obj : collection) {
				result += markModifyStamp(request, obj);
			}
		}
		return result;
	}
	
	/**
	 * 对Object打“修改时间、修改IP、修改人员ID”等戳
	 * 
	 * @param request 来自页面的请求
	 * @param myVo 输入一个VO
	 */
	public static int markModifyStamp(final HttpServletRequest request, Object thisObj) {
		return accessVo(thisObj, new ITransctVoField() {
			public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
				if (!pd.getName().equals("class")) {
					if (DvStringHelper.arrayContainString(DESC_MODIFY_TIME, pd.getName())) {//修改时间
						if(bw.getPropertyType(pd.getName()).getName().equals("java.sql.Timestamp")) {
							bw.setPropertyValue(pd.getName(), DvDateHelper.getSysTimestamp()); 
						} else {
							bw.setPropertyValue(pd.getName(), DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy-MM-dd HH:mm:ss")); 
						}
						return 1;
					} else if (DvStringHelper.arrayContainString(DESC_MODIFY_IP, pd.getName()) && request != null) {//修改IP
						bw.setPropertyValue(pd.getName(), DvJspHelper.getRemoteIp(request));
						return 1;
					} else if (DvStringHelper.arrayContainString(DESC_MODIFY_USER_ID, pd.getName()) && request != null) {//修改人员
						bw.setPropertyValue(pd.getName(), LoginHelper.getUserId());
						return 1;
//					} else if (DESC_ORDER_CODE.equals(pd.getName())) {//排序字段
//						bw.setPropertyValue(pd.getName(), DvDateHelper.getSysDateTime());
//						return 1;
//					} else if (DESC_USABLE_STATE.equals(pd.getName())) {//可用状态
//						bw.setPropertyValue(pd.getName(), DV_YES);
//						return 1;
					} else {
						return 0;
					}
				} else {
					return 0;
				}
			}
		});
	}
	
	/**
	 * 功能: 把vo中的值取出来
	 *
	 * @param vo
	 * @return
	 */
	public static String voToString(Object vo) {
		if (vo == null) {
			return "";
		}
		final StringBuffer sb = new StringBuffer();
		final Map<String, String> mFinalValue = new HashMap<String, String>();
		mFinalValue.put("tempIndex", "0");
		//sb.append(vo.getClass().getName() + ":" );
		accessVo(vo, new ITransctVoField() {
			public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
				if (pd.getName().equals("class")) {
					return 0;
				}
				int index = Integer.parseInt(mFinalValue.get("tempIndex"));
				mFinalValue.put("tempIndex", String.valueOf(++index));
				sb.append(pd.getName() + "=" + bw.getPropertyValue(pd.getName()) + "\n");
				return 1;
			}
		});
		return sb.toString();
	}

	
	/**
	 * 功能: 判断2个vo的值是否相等
	 *
	 * @param vo1
	 * @param vo2
	 * @return
	 */
	public static boolean voEquals(Object vo1, final Object vo2) {
		if(vo1 == vo2) {
			return true;
		}
		final Map<String, Object> mFinalValue = new HashMap<String, Object>();
		mFinalValue.put("tempIndex", "0");
		mFinalValue.put("tempEquals", "1");
		if(vo1 != null && vo2 != null) {
			if(!(vo2.getClass().equals(vo1.getClass()))) {
				return false;
			}
			accessVo(vo1, new ITransctVoField() {
				public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
					String currentKey = pd.getName();
					if (!currentKey.equals("class")) {
						boolean bEquals = (String.valueOf(mFinalValue.get("tempEquals"))).equals("1") ? true : false ;
						if(bEquals) {  //只有true才进行比较
							BeanWrapper bw2 = new BeanWrapperImpl(vo2);
							if(bw.getPropertyValue(currentKey) == null) {
								if(bw2.getPropertyValue(currentKey) != null) {
									bEquals = false;
								}
							} else {
								if(!bw.getPropertyValue(currentKey).equals(bw2.getPropertyValue(currentKey))) {
									bEquals = false; 
								}
							}
							int index = Integer.parseInt(String.valueOf(mFinalValue.get("tempIndex")));
							mFinalValue.put("tempIndex", String.valueOf(++ index) );
							mFinalValue.put("tempEquals", bEquals ? "1" : "0" );
						}
						return 1;
					} else {
						return 0;
					}
				}
			});
		} else if(vo1 == null && vo2 == null) {
			return true;
		}
		boolean bEquals = (String.valueOf(mFinalValue.get("tempEquals"))).equals("1") ? true : false ;
		return bEquals;
	}
	
	/**
	 * 功能: 克隆自身（浅拷贝）
	 *
	 * @param vo1
	 * @return
	 */
	public static Object voClone(Object vo1) {
		Object vo2 = null;
		try {
			vo2 = vo1.getClass().newInstance();
			final BeanWrapper bw2 = new BeanWrapperImpl(vo2);
		
			accessVo(vo1, new ITransctVoField() {
				public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
					String currentKey = pd.getName();
					if (!currentKey.equals("class")) {
						bw2.setPropertyValue(currentKey, bw.getPropertyValue(currentKey));
						return 1;
					} else {
						return 0;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo2;
	}
	
	/**
	 * 自定义HashCode
	 * @param vo
	 * @return
	 */
	public static int voHashCode(Object vo) {
		final Object[] hashCode = new Object[]{0 + ""};
		try {
			accessVo(vo, new ITransctVoField() {
				public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
					String currentKey = pd.getName();
					if (!currentKey.equals("class")) {
						Object fieldValue = bw.getPropertyValue(currentKey);
						if(fieldValue != null) {
							int tempHashCode = Integer.parseInt(hashCode[0].toString());
							tempHashCode += 29 * tempHashCode + fieldValue.hashCode();
							hashCode[0] = tempHashCode + "";
						}
						return 1;
					} else {
						return 0;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(hashCode[0].toString());
	}
	
	/**
	 * 从列表中找到第一个（属性为fieldName且值为fieldValue）的对象
	 * 
	 * @param fieldName 对象的属性名称
	 * @param fieldValue 属性的值
	 * @param list 对象列表
	 * @return
	 */
	public static Object findVoFromList(String fieldName, Object fieldValue, List<Object> list) {
		Object result = null;
		if (list != null && list.size() > 0 && fieldName != null) {
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				Object value = getVoFieldValue(obj, fieldName);
				if (fieldValue == null && value == null){
					result = null;
				} else if(fieldValue != null && fieldValue.equals(value)){
					result = obj;
				} else if(value != null && value.equals(fieldValue)){
					result = obj;
				}
			}
		}
		return result;
	}
	
	/**
	 * 功能: 得到vo中的某个字段的值
	 *
	 * @param vo
	 * @param fieldName
	 * @return
	 */
	public static Object getVoFieldValue(Object vo, String fieldName) {
		Object rtObj = null;
		String getMethodName = "get" + DvStringHelper.toFirstUpperCase(fieldName);
		try {
			Method getMethod = vo.getClass().getDeclaredMethod(getMethodName,new Class[]{});
			if(getMethod != null) {
				rtObj = getMethod.invoke(vo ,new Object[] {});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtObj;
	}
	
	/**
	 * 判断对象是否存在某个方法
	 * @param vo
	 * @param method
	 * @return
	 */
	public static boolean voContainMethod(Object vo, String method) {
		boolean isContain = false;
		try {
			Method getMethod = vo.getClass().getDeclaredMethod(method, new Class[]{});
			if(getMethod != null) {
				isContain = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isContain;
	}	

	public static int populateCommonFiles(final HttpServletRequest request, CommonFileAware thisObj, String namespacePrefix){
		int sum = 0;
		CommonFileAware obj = (CommonFileAware) thisObj;
		if(StringUtils.isEmpty(namespacePrefix)){
			namespacePrefix = CommonFileAware.COMMON_FILE_NAMESPACE_PREFIX + ".";
		}
		List<TCommonFileVo> files = DvPopulateHelper.populateVos(TCommonFileVo.class, request, "file_name", namespacePrefix);
		obj.setFiles(files);
		sum = files == null ? 0 : files.size();
		return sum;
	}

}