package com.test;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreemarkTest {
	
	public static final Configuration cfg = new Configuration();
	
	static {
		/* 在整个应用的生命周期中，这个工作你应该只做一次。 */
		/* 创建和调整配置模板，可以从数据库拉取。 */
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("fool", "${name}是个大傻子！");
		stringLoader.putTemplate("beauty", "${name}是个大美女！");
		stringLoader.putTemplate("love", "${boy}爱${girl}！");
		cfg.setTemplateLoader(stringLoader);
	}
	
	public static String getMSNMessage(String tplKey, Map<String, Object> params) {
		String result = null;
		try {
			Template temp = cfg.getTemplate(tplKey, "utf-8");
			Writer out = new StringWriter(2048);//斟酌合适空间，不能太大浪费，也不能太小，容纳不下
			temp.process(params, out);
			result = out.toString();
			out.close();
		} catch (TemplateException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("boy", "蛋蛋");
		params.put("girl", "格格");
		System.out.println(getMSNMessage("love", params));
		
		System.out.print("即便，");
		params.put("name", "格格");
		System.out.println(getMSNMessage("fool", params));
	}
}