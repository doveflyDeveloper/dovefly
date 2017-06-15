package com.deertt.utils.helper.encryt;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Base64;

public class DvEncryptHelper {

	private final static String desKey = "12345678";
	
	private static final String DES = "DES";
	
	/**
	 * 对密码单向加密
	 * 
	 * @param password 密码
	 * @param seed 种子
	 * @return
	 */
	public static String encryptPassword(String password, String seed) {
		if(seed == null) {
			seed = "";
		}
		//return Md5Token.getInstance().getLongToken(Md5Token.getInstance().getLongToken(password) + seed);
		return encryptDesBase64(password + seed);
		//return password;
	}
	
	/**
	 * DES加密 + Base64编码(URL安全)
	 * @param value
	 * @return
	 */
	public static String encryptDesBase64(String value){
		try {
			byte[] s = desEncrypt(value.getBytes(), desKey.getBytes());
			String base64 = Base64.encodeBase64URLSafeString(s);
			return base64;
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}
	
	/**
	 * Base64解码 + DES解密 
	 * @param value
	 * @return
	 */
	public static String decryptDesBase64(String value) {
		try {
			byte[] s = Base64.decodeBase64(value);
			String result = new String(desDecrypt(s, desKey.getBytes()));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}

	}
	
	/**
	 * Base64编码(URL安全)
	 * @param value
	 * @return
	 */
	public static String encryptBase64(String value){
		try {
			String base64 = Base64.encodeBase64URLSafeString(value.getBytes());
			return base64;
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}
	
	/**
	 * Base64解码
	 * @param value
	 * @return
	 */
	public static String decryptBase64(String value) {
		try {
			return new String(Base64.decodeBase64(value));
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}

	public static byte[] desEncrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		javax.crypto.SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(1, securekey, sr);
		return cipher.doFinal(src);
	}

	public static byte[] desDecrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		javax.crypto.SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(2, securekey, sr);
		return cipher.doFinal(src);
	}

	public static String digestString(String pass, String algorithm) throws Exception {
		ByteArrayOutputStream bos;
		MessageDigest md = MessageDigest.getInstance(algorithm);
		byte digest[] = md.digest(pass.getBytes("iso-8859-1"));
		bos = new ByteArrayOutputStream();
		OutputStream encodedStream = MimeUtility.encode(bos, "base64");
		encodedStream.write(digest);
		return bos.toString("iso-8859-1");
	}

	public static String digestPassword(String password, String seed) {
		if(seed == null)
			seed = "";
		return encryptDesBase64((new StringBuilder(String.valueOf(password))).append(seed).toString());
	}

}
