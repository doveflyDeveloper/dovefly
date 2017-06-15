package com.deertt.utils.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class DvHttpHelper {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
	}
	
	/**
	 * 发送 get请求
	 */
	public static String get(String url) {
		String ret = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.  
			HttpGet httpget = new HttpGet(url);
			
			// 执行get请求.  
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体  
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					ret = EntityUtils.toString(entity, "utf-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	/**
	 * 发送 get请求
	 */
	public static String get(String url, Map<String, Object> paramMap, Map<String, Object> headerMap) {
		String ret = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建参数队列  
			if (paramMap != null) {
				url += "?";
				for (Iterator<Map.Entry<String, Object>> ite = paramMap.entrySet().iterator(); ite.hasNext(); ) {
					Map.Entry<String, Object> entry = ite.next();
					url += entry.getKey() + "=" + entry.getValue().toString() + "&";//没有Unicode编码！！
				}
			}
			// 创建httpget.  
			HttpGet httpget = new HttpGet(url);
			
			if (headerMap != null) {
				for (Iterator<Map.Entry<String, Object>> ite = headerMap.entrySet().iterator(); ite.hasNext(); ) {
					Map.Entry<String, Object> entry = ite.next();
					httpget.addHeader(entry.getKey(), entry.getValue().toString());
				}
			}
			
			// 执行get请求.  
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体  
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					ret = EntityUtils.toString(entity, "utf-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static String post(String url, Map<String, Object> params) {
		String ret = null;
		// 创建默认的httpClient实例.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost  
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列  
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		for (Iterator<Map.Entry<String, Object>> ite = params.entrySet().iterator(); ite.hasNext(); ) {
			Map.Entry<String, Object> entry = ite.next();
			formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
		}
		
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					ret = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	
	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static String postForm(String url, Map<String, Object> paramMap, Map<String, Object> headerMap) {
		String ret = null;
		// 创建默认的httpClient实例.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost  
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列  
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		if (paramMap != null) {
			for (Iterator<Map.Entry<String, Object>> ite = paramMap.entrySet().iterator(); ite.hasNext(); ) {
				Map.Entry<String, Object> entry = ite.next();
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}
		if (headerMap != null) {
			for (Iterator<Map.Entry<String, Object>> ite = headerMap.entrySet().iterator(); ite.hasNext(); ) {
				Map.Entry<String, Object> entry = ite.next();
				httppost.addHeader(entry.getKey(), entry.getValue().toString());
			}
		}
		
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					ret = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(ret);
		return ret;
	}

}