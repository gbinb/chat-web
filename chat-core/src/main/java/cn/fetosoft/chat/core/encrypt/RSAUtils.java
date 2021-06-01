package cn.fetosoft.chat.core.encrypt;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Title：
 * @Author：guobingbing
 * @Date 2017/10/27 14:16
 * @Description
 * @Version
 */
public class RSAUtils {
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";


    private static final String CHARSET = "UTF-8";

	/**
	 * 生成的公私钥
	 */
	public static class RsaKey {

		/**
		 * 私钥
		 */
		private String privateKey;

		/**
		 * 公钥
		 */
    	private String publicKey;

		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}

		public String getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}
	}

    /**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 *
	 * @return
	 * @throws Exception
	 */
	public static RsaKey genKeyPair() throws Exception {
		RsaKey rsaKey = new RsaKey();
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		java.security.KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		rsaKey.setPrivateKey(Base64.encodeBase64String(privateKey.getEncoded()));
		rsaKey.setPublicKey(Base64.encodeBase64String(publicKey.getEncoded()));
		return rsaKey;
	}

	/**
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 *
	 * @param data 已加密数据
	 * @param privateKey 私钥(BASE64编码)
	 *
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return new String(Base64.encodeBase64(signature.sign()), CHARSET);
	}

	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 *
	 * @param data 已加密数据
	 * @param publicKey 公钥(BASE64编码)
	 * @param sign 数字签名
	 *
	 * @return
	 * @throws Exception
	 *
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64.decodeBase64(sign));
	}

	/**
	 * 加密
	 * @param publicKey
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String publicKey, String data) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key pubKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		try {
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] output = cipher.doFinal(data.getBytes(CHARSET));
			return Base64.encodeBase64String(output);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * 解密
	 * @param privateKey
	 * @param cipherData
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String privateKey, String cipherData) throws Exception {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		try {
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			byte[] output = cipher.doFinal(Base64.decodeBase64(cipherData));
			return new String(output);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * 从公钥文件中读取公钥串
	 * @author guobingbing
	 * @date 2018/2/8 10:12
	 * @param
	 * @return java.lang.String
	 */
	public static String getPubKeyFromCRT(String crtFileName) throws Exception {
		InputStream is = new FileInputStream(crtFileName);
		CertificateFactory cf = CertificateFactory.getInstance("x509");
		java.security.cert.Certificate cerCert = cf.generateCertificate(is);
		PublicKey publicKey = cerCert.getPublicKey();
		String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
		return publicKeyString;
	}

	public static void main(String[] args) throws Exception {
		RsaKey rsaKey = RSAUtils.genKeyPair();
		System.out.println(rsaKey.privateKey.length());
		System.out.println(rsaKey.publicKey.length());

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("requestNo","9161281d5c53433433bb");
		jsonObject.put("code","afdsfsd");
		jsonObject.put("mobile","18532348899");
		jsonObject.put("userNo","20850000053");

		String encData = RSAUtils.encrypt(rsaKey.getPublicKey(), jsonObject.toJSONString());
		System.out.println(encData);
		System.out.println(RSAUtils.decrypt(rsaKey.getPrivateKey(), encData));
	}
}
