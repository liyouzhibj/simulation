package com.rrtx.simulationserver.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum  DesUtils {
	INSTANCE;

	private static final String ENC_PARA = "DES/ECB/PKCS7Padding";
	
	public byte[] encrypt(byte[] bytP, byte[] key, byte[] ivKey) throws Exception {
		return des(bytP, key, ivKey, Cipher.ENCRYPT_MODE);
	}
	
	public byte[] decrypt(byte[] bytE, byte[] key, byte[] ivKey) throws Exception {
		return des(bytE, key, ivKey, Cipher.DECRYPT_MODE);
	}
	
	private byte[] des(byte[] bytP, byte[] key, byte[] ivKey, int mode) throws Exception {
		
		// 加密算法名称+加密模式+填充模式()
		Cipher cipher = Cipher.getInstance(ENC_PARA);
		
		if(ivKey != null && ivKey.length != 0){
			// 设置向量
			IvParameterSpec iv = new IvParameterSpec(ivKey);
			// 设置工作模式为加密模式，给出密钥和向量 
			cipher.init(mode, getKey(key), iv);
		}else{
			// 设置工作模式为加密模式，给出密钥和向量 
			cipher.init(mode, getKey(key));			
		}
		
		// 加密/解密
		byte[] enc = cipher.doFinal(bytP);
		
		return enc;
	}
	
	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private SecretKey getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		// 设置密钥参数
		DESKeySpec desKeySpec = new DESKeySpec(arrBTmp);

		// 获得密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

		// 得到密钥对象
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		return secretKey;
	}

	/**
	 * 从指定字符串的后8位生成向量，所需的字节数组长度为8位 不足8位时后面补0，超出8位只取后8位
	 *
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的向量
	 * @throws java.lang.Exception
	 */
	private IvParameterSpec getIvKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		int offset = 0;
		if(arrBTmp.length > 8){
			offset = arrBTmp.length - 8;
		}
		int j = 0;
		for (int i = offset; i < arrBTmp.length; i++) {
			arrB[j++] = arrBTmp[i];
		}

		// 设置向量
		IvParameterSpec iv = new IvParameterSpec(arrB);

		return iv;
	}
}
