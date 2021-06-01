package cn.fetosoft.chat.core.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author guobingbing
 * @create 2020/1/28 16:55
 */
public final class NetUtil {

	/**
	 * 获取本机IP地址
	 * @author guobingbing
	 * @date 2020/1/28 18:58
	 * @return list
	 */
	public static String getLocalIP() {
		String host = "";
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return host;
	}

	/**
	 * 将ip转换成long型数值
	 * @author guobingbing
	 * @date 2020/1/28 18:58
	 * @param ip
	 * @return long
	 */
	public static long ipToLong(String ip){
		long iplong = 0;
		String[] ipArr = ip.split("\\.");
		for(int i=0; i<ipArr.length; i++){
			long tmp = Long.parseLong(ipArr[i]);
			iplong += tmp << ((ipArr.length-1-i)*8);
		}
		return iplong;
	}

	/**
	 * 将long型数值转换成ip串
	 * @author guobingbing
	 * @date 2020/1/28 18:58
	 * @param ip
	 * @return java.lang.String
	 */
	public static String longToIp(long ip){
		StringBuilder sb = new StringBuilder();
		sb.append((ip >> 24) & 0xFF).append(".");
		sb.append((ip >> 16) & 0xFF).append(".");
		sb.append((ip >> 8) & 0xFF).append(".");
		sb.append(ip & 0xFF);
		return sb.toString();
	}

	/**
	 * 获取远程IP
	 * @author guobingbing
	 * @date 2020-03-02 14:51
	 * @param request
	 * @return java.lang.String
	 */
	public static String getRemoteIp(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
