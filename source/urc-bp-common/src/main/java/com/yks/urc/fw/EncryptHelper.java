package com.yks.urc.fw;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密解密处理
 * 
 * @author Administrator
 * 
 */
public class EncryptHelper {
	/**
	 * SHA1加密
	 * 
	 * @param decript
	 * @return
	 */
	public static String EncryptSha1(String decript) {
		if (StringUtility.isNullOrEmpty(decript))
			return StringUtility.Empty;
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptAesToByte(String content, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, getSecureRandom(password));
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		byte[] byteContent = content.getBytes("utf-8");
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(byteContent);
		return result; // 加密
	}

	private static SecureRandom getSecureRandom(String password) throws Exception {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(password.getBytes("utf-8"));
		return secureRandom;
	}

	/**
	 * AES解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decryptAes(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, getSecureRandom(password));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (Exception e) {

		}
		return new byte[] {};
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toLowerCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}

		return result;
	}

	public static String encryptAes(String strSrc, String strPwd) throws Exception {
		if (StringUtility.isNullOrEmpty(strSrc))
			return strSrc;
		byte[] encryptResult = encryptAesToByte(strSrc, strPwd);
		return parseByte2HexStr(encryptResult);
	}

	public static String decryptAes(String strSrc, String strPwd) throws UnsupportedEncodingException {
		if (StringUtility.isNullOrEmpty(strSrc))
			return strSrc;
		byte[] decryptFrom = parseHexStr2Byte(strSrc);
		byte[] decryptResult = decryptAes(decryptFrom, strPwd);
		return new String(decryptResult, "utf-8");
	}

	/**
	 * 附件路径加密密钥
	 * 
	 */
	public static final String Path_AES_Key = "Path_AES_Key";

	public static String encryptAes_Base64(String strSrc, String strPwd) throws Exception {
		if (StringUtility.isNullOrEmpty(strSrc))
			return strSrc;
		byte[] encryptResult = encryptAesToByte(strSrc, strPwd);
		String strBase64 = StringUtility.Base64Encode(encryptResult);
		return strBase64.replace(StringUtility.NewLine(), StringUtility.Empty);
	}

	public static String decryptAes_Base64(String strSrc, String strPwd) throws UnsupportedEncodingException {
		if (StringUtility.isNullOrEmpty(strSrc))
			return strSrc;

		byte[] decryptFrom = StringUtility.base64Decode(strSrc);
		byte[] decryptResult = decryptAes(decryptFrom, strPwd);
		return StringUtility.byteToStringUTF8(decryptResult);
	}

	public static void main(String[] args) throws Exception {
		String password = "!QAZ2wsx";
		String strSrc = "平凡之路.mp3|平凡之路.mp3平凡之路.mp3|平凡之路.mp3平凡之路.mp3|平凡之路.mp3平凡之路.mp3|平凡之路.mp3";

		String strEncode = EncryptHelper.encryptAes(strSrc, password);
		System.out.println("加密1：" + strEncode.length() + " " + strEncode);

		String strDecode = decryptAes(strEncode, password);
		System.out.println("解密1：" + strDecode);

		strEncode = encryptAes_Base64(strSrc, password);
		System.out.println("加密2：" + strEncode.length() + " " + strEncode);

		strDecode = decryptAes_Base64(strEncode, password);
		System.out.println("解密2：" + strDecode);
	}
}
