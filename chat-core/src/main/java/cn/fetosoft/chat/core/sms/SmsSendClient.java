package cn.fetosoft.chat.core.sms;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/18 11:21
 */
public final class SmsSendClient {

	/**
	 * 发送短信
	 * @param gatewayUrl
	 * @param paramMap
	 * @return
	 */
	public static String postData(String gatewayUrl, Map<String, Object> paramMap) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(gatewayUrl);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// conn.setRequestProperty("Charset", "UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 设置请求属性
			String param = "";
			if (paramMap != null && paramMap.size() > 0) {
				Iterator<String> ite = paramMap.keySet().iterator();
				while (ite.hasNext()) {
					String key = ite.next();
					Object value = paramMap.get(key);
					param += key + "=" + value + "&";
				}
				param = param.substring(0, param.length() - 1);
				System.out.println(param);
			}

			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.err.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}


	//JSON方法输出
	public static void writeJson(String array,HttpServletResponse resp) {
		resp.setContentType("text/json;charset=utf-8");
		PrintWriter pw;
		try {
			pw = resp.getWriter();
			pw.write(array);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
