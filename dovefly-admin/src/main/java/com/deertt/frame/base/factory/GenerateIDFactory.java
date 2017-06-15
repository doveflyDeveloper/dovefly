package com.deertt.frame.base.factory;

import java.util.Random;
import java.util.UUID;


/**
 * 
 * @Description ID生成器
 * @author fcm
 *
 * @date 2013-5-3下午2:53:37
 * @version v1.0
 */
public class GenerateIDFactory {
	
	private static Random random = new Random();
	private static int base = 1000000;

	/**
	 * 生成唯一ID
	 * 
	 * @return
	 */
	public static String generateID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 生成唯一ID
	 * 
	 * @return
	 */
	public static Long getLongId() {
		return System.currentTimeMillis() * base + random.nextInt(base);
	}
	
	/**
	 * 生成唯一ID
	 * 
	 * @return
	 */
	public static int getIntId() {
		return random.nextInt(base);
	}
	
	/**
	 * 生成唯一ID
	 * 
	 * @return
	 */
	public static String getStringId() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 生成唯一ID
	 * 
	 * @return
	 */
	public static int getIntId(String tableName) {
		
		 
		return random.nextInt(base);
	}

	/**
	 * 生成唯一ID数组
	 * 
	 * @return
	 */
	public static String[] generateIDs(int size) {
		String[] ids = new String[size];
		for (int i = 0; i < size; i++) {
			ids[i] = generateID();
		}
		return ids;
	}

}
