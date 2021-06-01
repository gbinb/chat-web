package cn.fetosoft.chat.test.image;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/20 18:04
 */
public class ImageThumbnailTest {

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		Thumbnails.of(new File("E:/20201116104303.jpg")).scale(0.3f).toFile("E:/20201116104303_1.jpg");
		System.out.println(System.currentTimeMillis()-start);
	}
}
