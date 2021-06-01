package cn.fetosoft.chat.connect.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * 图片信息
 * @author guobingbing
 * @create 2020/12/22 18:29
 */
@Setter
@Getter
public class ChatImage extends FileMetadata {

	/**
	 * 宽
	 */
	private int width;

	/**
	 * 高
	 */
	private int height;

	/**
	 * 大小
	 */
	private long size;

	/**
	 * 缩略图
	 */
	private String scaleUrl;
}
