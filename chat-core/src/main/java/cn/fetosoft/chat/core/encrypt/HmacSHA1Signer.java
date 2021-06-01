package cn.fetosoft.chat.core.encrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * HmacSHA1数字签名验签
 * @author guobingbing
 * @create 2020-02-26 13:44
 */
public class HmacSHA1Signer {

	public static final String ENCODING = "UTF-8";
	private static final String ALGORITHM_NAME = "HmacSHA1";

	public static String signString(String stringToSign, String accessKeySecret) {
		try {
			Mac mac = Mac.getInstance(ALGORITHM_NAME);
			mac.init(new SecretKeySpec(accessKeySecret.getBytes(ENCODING), ALGORITHM_NAME));
			byte[] signData = mac.doFinal(stringToSign.getBytes(ENCODING));
			return DatatypeConverter.printBase64Binary(signData);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e.toString());
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e.toString());
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e.toString());
		}
	}

	public static void main(String[] args) {
		System.out.println(signString("123456", "SC5gvN3k!8BpgD7/!ztLW5SNabQxZWi6"));
	}
}
