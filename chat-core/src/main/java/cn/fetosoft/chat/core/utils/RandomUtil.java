package cn.fetosoft.chat.core.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author guobingbing
 * @create 2020-02-20 21:06
 */
public class RandomUtil {

	/**
	 * 获取一定长度的随机数
	 * @author guobingbing
	 * @date 2018/9/5 20:18
	 * @param length
	 * @return java.lang.String
	 */
	public static String getLenRandom(int length){
		long number = ThreadLocalRandom.current().nextLong((long)Math.pow(10, length));
		return formatNumberByZero(length, number);
	}

	/**
	 * 格式数字，用零填充
	 * @param length
	 * @param number
	 * @return
	 */
	public static String formatNumberByZero(int length, long number){
		String incStr = String.valueOf(number);
		if(incStr.length()<length){
			return String.format("%0"+length+"d", number);
		}else {
			return incStr;
		}
	}

	/**
	 * 获取指定长度的随机Key
	 * @param len
	 * @return
	 */
	public static String getRandomKey(int len){
		if(len<16){
			len = 16;
		}else if(len>32){
			len = 32;
		}
		String str = "HIJKLMNOabcdefghijklmA=BCDEFGn!opqrstu=vwxyzPQRSTU!VWXYZ1234567890=";
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for(int i=0; i< len; i++){
			int ram = random.nextInt(chars.length);
			char c = chars[ram];
			sb.append(c);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getRandomKey(16));
	}
}
