package cn.fetosoft.chat.core.encrypt;

import cn.fetosoft.chat.core.utils.IdGenerateUtils;
import cn.fetosoft.chat.core.utils.LocalDateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title：jwt token
 * @Author：guo
 * @Date 2020/6/12 10:40
 * @Description
 * @Version
 */
public final class JwtToken {

	private static final String ISSUER = "server";
	private static final String AUDIENCE = "client";

	/**
	 * 生成token，采用Hmac256加密
	 * @param jwtId
	 * @param expireDays 有效天数
	 * @param secret 密钥
	 * @param map 自定义参数
	 * @return
	 */
	public static String createToken(String jwtId, int expireDays, String secret, Map<String, String> map){
		JWTCreator.Builder builder = JWT.create()
				// payload
				.withIssuer(ISSUER)
				.withAudience(AUDIENCE)
				.withJWTId(jwtId)
				// 生成token的时间
				.withIssuedAt(new Date())
				// 过期时间
				.withExpiresAt(getExpireDate(expireDays));
		// 添加自定义信息
		for(Map.Entry<String, String> entry : map.entrySet()){
			builder.withClaim(entry.getKey(), entry.getValue());
		}
		// 签名 生成token  并返回
		return builder.sign(Algorithm.HMAC256(secret));
	}

	/**
	 * 获取过期日期
	 * @param expireDays
	 * @return
	 */
	private static Date getExpireDate(int expireDays){
		LocalDate localDate = LocalDate.now();
		expireDays = expireDays<=0?30:expireDays;
		localDate = localDate.plusDays(expireDays);
		return LocalDateUtil.localDateToDate(localDate);
	}

	/**
	 * 验证token
	 * @param token
	 * @param secret
	 * @return
	 */
	public static VerifyResult verifyToekn(String token, String secret){
		VerifyResult result = VerifyResult.create(false);
		try{
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
			DecodedJWT jwt = verifier.verify(token);
			Map<String, Claim> map = jwt.getClaims();
			for(Map.Entry<String, Claim> entry : map.entrySet()){
				result.putValue(entry.getKey(), entry.getValue().asString());
			}
			result.setSuccess(true);
		}catch(TokenExpiredException e){
		    result.setErrorMsg("登录过期");
		}
		return result;
	}

	/**
	 * 验证结果
	 */
	public static class VerifyResult{

		private boolean isSuccess;
		private String errorMsg;
		private Map<String, String> dataMap = new HashMap<>(8);

		private VerifyResult(boolean succ){
			this.isSuccess = succ;
		}

		public static VerifyResult create(boolean succ){
			return new VerifyResult(succ);
		}

		public boolean isSuccess() {
			return isSuccess;
		}

		public void setSuccess(boolean success) {
			isSuccess = success;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		public Map<String, String> getDataMap(){
			return this.dataMap;
		}

		public void putDataMap(Map<String, String> map){
			this.dataMap.putAll(map);
		}

		public void putValue(String key, String value){
			this.dataMap.put(key, value);
		}
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>(8);
		map.put("username", "admin");
		map.put("password", "A000000");
		String secret = IdGenerateUtils.buildUUid();
		String token = createToken("123",30, secret, map);
		System.out.println(token);
		VerifyResult result = verifyToekn(token, secret);
		System.out.println(result.isSuccess);
		if(result.isSuccess){
			for(Map.Entry<String, String> entry : result.dataMap.entrySet()){
				System.out.println(entry.getKey() + "=" + entry.getValue());
			}
		}
	}
}
