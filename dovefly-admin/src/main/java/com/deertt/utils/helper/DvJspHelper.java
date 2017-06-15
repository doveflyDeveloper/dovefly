package com.deertt.utils.helper;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.utils.helper.string.DvStringHelper;


/**
 * @author   帮助实现一些有关Jsp页面的处理
 */
public class DvJspHelper implements IGlobalConstants {

	/**
	 * 从表单中获取值，如果值是null或"null"或"undefined"则返回""
	 * 
	 * @param request HttpServletRequest
	 * @param name 表单名称
	 * @return 表单值
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);
		if (strValue == null || "null".equals(strValue) || "undefined".equals(strValue)) {
			strValue = "";
		}
		return strValue;
	}
	
	/**
	 * 功能: 先从request.getAttribute，没有的话再从request.getParameter取
	 *
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getValueFromRequest_attributeParameter(HttpServletRequest request, String key) {
		String value = null;
		if(request.getAttribute(key) != null) {  //如果request.getAttribute中有，就不取request.getParameter
			value = request.getAttribute(key).toString();
		} else {
			//如果有多个key值，取最后一个
			if(request.getParameterValues(key) != null && request.getParameterValues(key).length > 1) {
				value = request.getParameterValues(key)[request.getParameterValues(key).length-1];
			} else {
				value = request.getParameter(key);  //从request的parameter获得
			}

		}
		return value;
	}

	/**
	 * 从表单中获取整数值，如果是null或 "null"，则过滤为0
	 * 
	 * @param request HttpServletRequest
	 * @param name 需要获取的input名字
	 * @return 表单中的实际内容
	 */
	public static int getParameterInt(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);
		if (strValue == null) {
			strValue = "0";
		} else if ("null".equals(strValue)) {
			strValue = "0";
		}
		int returnValue = 0;
		try {
			returnValue = Integer.parseInt(strValue);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * 功能: 从request获取服务器根的http地址，如http://127.0.0.1:9999
	 *
	 * @param request
	 * @return
	 */
	public static String getRootHttpUrl(HttpServletRequest request) {
		String baseProjectPath = request.getScheme() + "://" + request.getServerName() + ":" + String.valueOf(request.getServerPort());
		return baseProjectPath;
	}
	
	/**
	 * 功能: 从request获取war应用的http地址，如http://127.0.0.1:9999/rmweb
	 *
	 * @param request
	 * @return
	 */
	public static String getWarHttpUrl(HttpServletRequest request) {
		String baseProjectPath = request.getScheme() + "://" + request.getServerName() + ":" + String.valueOf(request.getServerPort()) + request.getContextPath();
		return baseProjectPath;
	}


	private static StringBuffer getUrlFromParameters(StringBuffer url, HttpServletRequest req) {
		Enumeration enu = req.getParameterNames();
		StringBuffer re = new StringBuffer();
		while (enu.hasMoreElements()) {
			String pstr = (String) enu.nextElement();
			if (url.indexOf(pstr + "=") == -1) {
				String value = req.getParameter(pstr);
				value = DvStringHelper.encodeUrl(value);
				re.append(pstr).append("=").append(value);
				if (enu.hasMoreElements()) {
					re.append("&");					
				}
			}
		}
		return re;
	}  
	
	/**
	 * 功能: 从 request 获取可能为多个值的数组表示，""也是有效的单个值，但""会返回0数组
	 * 
	 * @param request
	 * @param inputName
	 * @return
	 */
	public static String[] getArrayWithEmptyFromRequest(HttpServletRequest request, String inputName) {
		String[] returnStrArray = null;
		String tempStr = request.getParameter(inputName);
		if(tempStr != null && tempStr.length() > 0) {
			returnStrArray = tempStr.split(",", -1);
		}
		if(returnStrArray == null) {
			returnStrArray = new String[0];
		}
		return returnStrArray;
	}
	
	/**
	 * 从 request 获取可能为多个值的数组表示, ""不是有效单个值
	 *
	 * @param request
	 * @param inputName
	 * @return
	 */
	public static String[] getArrayFromRequest(HttpServletRequest request, String inputName) {
		String[] returnStrArray = null;
		String tempStr = request.getParameter(inputName);
		if(tempStr != null && tempStr.length() > 0) {
			returnStrArray = tempStr.split(",");
		}
		if(returnStrArray == null) {
			returnStrArray = new String[0];
		}
		return returnStrArray;
	}
	
	/**
	 * 从 request 获取可能为多个值的数组表示
	 *
	 * @param request
	 * @param inputName
	 * @return
	 */
	public static String[] getArrayWithNullFromRequest(HttpServletRequest request, String inputName) {
		String[] returnStrArray = null;
		String tempStr = request.getParameter(inputName);
		if(tempStr != null && tempStr.length() > 0) {
			returnStrArray = tempStr.split(",",-1);
		}
		if(returnStrArray == null) {
			returnStrArray = new String[0];
		}
		return returnStrArray;
	}
	
	public static String getProfile(HttpServletRequest request, String key) {
		if(request.getAttribute(key) != null) {
			return request.getAttribute(key).toString();
		} else {
			Cookie[] aCookie = request.getCookies();
			if(aCookie != null) {
				for(Cookie c : aCookie) {
					if(key.equals(c.getName()) && c.getValue() != null && c.getValue().length() > 0) {
						return c.getValue();
					}
				}
			}

		}
		return null;
	}
	
	public static void clearProfile(HttpServletRequest request, HttpServletResponse response, String key) {
		request.removeAttribute(key);
		if(!"1".equals(request.getAttribute("RM_RESPONSE_WRITE_BACK"))) {
			//回写原来的cookie
			Cookie[] aCookie = request.getCookies();
			if(aCookie != null) {
				for(Cookie c : aCookie) {
					if(!key.equals(c.getName())) {
						response.addCookie(c);
					}
				}
			}
			request.setAttribute("RM_RESPONSE_WRITE_BACK", "1");
		}

		Cookie cookie = new Cookie(key, "");
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	///////////////////////////////////////////////////////////////////////////////

	/**
	 * 功能: 获得客户端真实IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(ServletRequest request) {
		String ip = null;
		if(request instanceof HttpServletRequest) {
			ip = ((HttpServletRequest)request).getHeader("X-Forwarded-For");
			if(ip != null && ip.trim().indexOf(",") > 0) {
				ip = ip.trim().substring(0, ip.trim().indexOf(","));
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = ((HttpServletRequest)request).getHeader("X-Real-IP");
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}