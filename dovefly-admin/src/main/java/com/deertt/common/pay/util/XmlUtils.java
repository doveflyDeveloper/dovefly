package com.deertt.common.pay.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

import com.deertt.module.pay.alipaybill.vo.AlipayBillVo;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.file.DvFileHelper;

public class XmlUtils {

	public XmlUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		// 获取一个xml文件
		String textFromFile = DvFileHelper.readStringFromFile(new File("C:/Users/fengcm/Desktop/alipay.xml"));
		String json = xml2Json(textFromFile);
		JSONObject result = JSONObject.fromObject(json);
		
		if ("T".equals(result.optString("is_success"))) {
			JSONObject response = result.getJSONObject("response");
			JSONObject account_page_query_result = response.getJSONObject("account_page_query_result");
//			String has_next_page = account_page_query_result.optString("has_next_page");//T or F
			JSONArray account_log_list = account_page_query_result.getJSONArray("account_log_list");
			
			for (int i = 0; i < account_log_list.size(); i++) {
				JSONObject obj = account_log_list.getJSONObject(i);
				
				AlipayBillVo vo = new AlipayBillVo();
				vo.setBalance(obj.optString("balance"));
				vo.setBuyer_account(obj.optString("buyer_account"));
				vo.setCurrency(obj.optString("currency"));
				vo.setDeposit_bank_no(obj.optString("deposit_bank_no"));
				vo.setGoods_title(obj.optString("goods_title"));
				vo.setIncome(obj.optString("income"));
				vo.setIw_account_log_id(obj.optString("iw_account_log_id"));
				vo.setMemo(obj.optString("memo"));
				vo.setMerchant_out_order_no(obj.optString("merchant_out_order_no"));
				vo.setOutcome(obj.optString("outcome"));
				vo.setPartner_id(obj.optString("partner_id"));
				vo.setRate(obj.optString("rate"));
				vo.setSeller_account(obj.optString("seller_account"));
				vo.setSeller_fullname(obj.optString("seller_fullname"));
				vo.setService_fee(obj.optString("service_fee"));
				vo.setService_fee_ratio(obj.optString("service_fee_ratio"));
				vo.setSign_product_name(obj.optString("sign_product_name"));
				vo.setSub_trans_code_msg(obj.optString("sub_trans_code_msg"));
				vo.setTotal_fee(obj.optString("total_fee"));
				vo.setTrade_no(obj.optString("trade_no"));
				vo.setTrade_refund_amount(obj.optString("trade_refund_amount"));
				vo.setTrans_code_msg(obj.optString("trans_code_msg"));
				vo.setTrans_date(DvDateHelper.getTimestamp(obj.optString("trans_date")));
				
				System.out.println(vo);
			}
			
		}
	}

	public static Map<String, Object> xml2map(String xmlString) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlString);
		Element rootElement = doc.getRootElement();
		Map<String, Object> map = new HashMap<String, Object>();
		ele2map(map, rootElement);
		System.out.println(map);
		// 到此xml2map完成，下面的代码是将map转成了json用来观察我们的xml2map转换的是否ok
		String string = JSONObject.fromObject(map).toString();
		System.out.println(string);
		return map;
	}

	/***
	 * 核心方法，里面有递归调用
	 * 
	 * @param map
	 * @param ele
	 */
	@SuppressWarnings("unchecked")
	public static void ele2map(Map<String, Object> map, Element ele) {
//		System.out.println(ele);
		// 获得当前节点的子节点
		List<Element> elements = ele.elements();
		if (elements.size() == 0) {
			// 没有子节点说明当前节点是叶子节点，直接取值即可
			map.put(ele.getName(), ele.getText());
		} else {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Object> tempMap = new HashMap<String, Object>();
			for (Element element : elements) {
				tempMap.put(element.getName(), null);
			}
			if (tempMap.size() == elements.size()) {//每个子节点都不同
				for (Element element : elements) {
					Map<String, Object> tmap = new HashMap<String, Object>();
					ele2map(tmap, element);
					map.put(element.getName(), tmap);
				}
			} else if (tempMap.size() == 1) {//每个子节点都相同，说明是数组
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (Element element : elements) {
					Map<String, Object> tmap = new HashMap<String, Object>();
					ele2map(tmap, element);
					list.add(tmap);
				}
				map.put(elements.get(0).getName(), list);
			}
		}
	}
	
	/***
	 * 核心方法，里面有递归调用
	 * 
	 * @param map
	 * @param ele
	 */
	@SuppressWarnings("unchecked")
	public static void ele2map2(Map<String, Object> map, Element ele) {
		
		// 获得当前节点的子节点
		List<Element> elements = ele.elements();
		if (elements.size() == 0) {
			// 没有子节点说明当前节点是叶子节点，直接取值即可
			map.put(ele.getName(), ele.getText());
		} else if (elements.size() == 1) {
			// 只有一个子节点说明不用考虑list的情况，直接继续递归即可
			Map<String, Object> tempMap = new HashMap<String, Object>();
			ele2map(tempMap, elements.get(0));
			map.put(ele.getName(), tempMap);
		} else {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Object> tempMap = new HashMap<String, Object>();
			for (Element element : elements) {
				tempMap.put(element.getName(), null);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = elements.get(0).getNamespace();

				List<Element> elements2 = ele.elements(new QName(string, namespace));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (Element element : elements2) {
						Map<String, Object> tempMap1 = new HashMap<String, Object>();
						ele2map(tempMap1, element);
						list.add(tempMap1);
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					Map<String, Object> tempMap1 = new HashMap<String, Object>();
					ele2map(tempMap1, elements2.get(0));
					map.put(string, tempMap1);
				}
			}
		}
	}
	
	public static String xml2Json(String xml) {  
        XMLSerializer xmlSerializer = new XMLSerializer();  
        return xmlSerializer.read(xml).toString();  
    }  

}
