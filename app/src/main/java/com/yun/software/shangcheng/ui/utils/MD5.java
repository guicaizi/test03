package com.yun.software.shangcheng.ui.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * 
 * @author LiBing
 * 
 */
public class MD5 {

	/**
	 * 加密算法
	 * 
	 * @param hash
	 * @return
	 */
	private static String toHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;
		for (i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * 加密
	 * 
	 * @param toDigest
	 * @return
	 */
	public static String getMD5(String toDigest) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(toDigest.getBytes());
			byte[] b = messageDigest.digest();
			return toHex(b);
		} catch (NoSuchAlgorithmException e) {
			String message = "没有找到 MD5 算法。";
			throw new RuntimeException(message, e);
		}
	}
	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
