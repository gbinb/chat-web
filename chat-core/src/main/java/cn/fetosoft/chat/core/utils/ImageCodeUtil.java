package cn.fetosoft.chat.core.utils;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @Title：图形码base64
 * @Author：guo
 * @Date 2020/6/10 19:03
 * @Description
 * @Version
 */
public class ImageCodeUtil {

	private static final char[] CHAR_CODE = {'2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	/**
	 * 生成图片的Base64码
	 * @param width
	 * @param high
	 * @return
	 */
	public static String[] createImage(int width, int high) {
		if(width<=10){
			width = 90;
		}
		if(high<=10){
			high = 26;
		}
		String[] arr = new String[2];
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		BufferedImage image = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		createBackground(g, width, high);
		String[] fontTypes = {"Arial","Arial Black","AvantGarde Bk BT","Calibri"};
		for (int i = 0; i < 4; i++) {
			String r = String.valueOf(CHAR_CODE[random.nextInt(CHAR_CODE.length)]);
			g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)],Font.BOLD,26));
			g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
			sb.append(r);
		}
		g.dispose();
		arr[0] = sb.toString();
		try(ByteArrayOutputStream os=new ByteArrayOutputStream()) {
			//利用ImageIO类提供的write方法，将image以png图片的数据模式写入流。
			ImageIO.write(image, "png", os);
			byte[] b = os.toByteArray();
			arr[1] = Base64.encodeBase64String(b);
		} catch (IOException e) {
			throw new ImagingOpException("生成图片验证码失败");
		}
		return arr;
	}

	/**
	 * 获取随机颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc,int bc) {
		int f = fc;
		int b = bc;
		Random random=new Random();
		if(f>255) {
			f=255;
		}
		if(b>255) {
			b=255;
		}
		return new Color(f+random.nextInt(b-f),f+random.nextInt(b-f),f+random.nextInt(b-f));
	}

	/**
	 * 填充背景，加入干扰线条
	 * @param g
	 */
	private static void createBackground(Graphics g, int width, int high) {
		g.setColor(getRandColor(220,250));
		g.fillRect(0, 0, width, high);
		for (int i = 0; i < 8; i++) {
			g.setColor(getRandColor(40,150));
			Random random = new Random();
			int x = random.nextInt(width);
			int y = random.nextInt(high);
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(high);
			g.drawLine(x, y, x1, y1);
		}
	}

	public static void main(String[] args) {
		String[] arr = createImage(90, 26);
		System.out.println(arr[0]);
		System.out.println(arr[1]);
	}
}
