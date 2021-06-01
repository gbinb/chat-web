package cn.fetosoft.chat.core.minio;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/26 11:57
 */
@Setter
@Getter
public class MinioFile {

	/**
	 * bucket名称
	 */
	private String bucket;

	/**
	 * Minio中的文件名
	 */
	private String objectName;

	/**
	 * 原文件名
	 */
	private String oriName;

	/**
	 * 新文件名
	 */
	private String newName;

	/**
	 * 文件链接
	 */
	private String fileUrl;

	/**
	 * 文件格式
	 */
	private String format;

	/**
	 * 文件路径
	 */
	private String savePath;

	/**
	 * 相对路径
	 */
	private String relativePath;
}
