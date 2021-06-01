package cn.fetosoft.chat.core.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Aes加解密 128位
 * @author guobingbing
 * @create 2020/12/26 15:06
 */
public final class AesCipherUtil {

	private static final String IV_STRING = "A-16-Byte-String";
	private static final String charset = "UTF-8";

	/**
	 * 加密
	 * @param key
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String key, String content) throws Exception {
		byte[] contentBytes = content.getBytes(charset);
		byte[] keyBytes = key.getBytes(charset);
		byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(encryptedBytes);
	}

	/**
	 * 解密
	 * @param key
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String key, String content) throws Exception {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedBytes = decoder.decode(content);
		byte[] keyBytes = key.getBytes(charset);
		byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes);
		return new String(decryptedBytes, charset);
	}

	private static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) throws Exception {
		return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
	}

	private static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) throws Exception {
		return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
	}

	private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
		byte[] initParam = IV_STRING.getBytes(charset);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(mode, secretKey, ivParameterSpec);
		return cipher.doFinal(contentBytes);
	}
}
