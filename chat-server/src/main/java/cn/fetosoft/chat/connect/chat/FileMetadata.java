package cn.fetosoft.chat.connect.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/22 18:34
 */
@Setter
@Getter
public class FileMetadata {

	/**
	 * 文件ID
	 */
	private String fileId;

	/**
	 * 远程URL
	 */
	private String url;

	/**
	 * 原始文件名
	 */
	private String oriName;
}
