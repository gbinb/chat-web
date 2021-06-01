package cn.fetosoft.chat.core.minio;

import cn.fetosoft.chat.core.utils.RandomUtil;
import cn.fetosoft.chat.core.utils.UUidUtil;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * MinIO工具类
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/26 10:16
 */
public class MinioHelper {

	private MinioClient minioClient;

	public MinioHelper(MinioClient minioClient){
		this.minioClient = minioClient;
	}

	/**
	 * 如果bucket不存在则创建
	 * @param bucket
	 * @return
	 * @throws Exception
	 */
	private void createBucket(String bucket) throws Exception{
		boolean isExist = minioClient.bucketExists(bucket);
		if (!isExist) {
			// 新建桶
			minioClient.makeBucket(bucket);
			minioClient.setBucketPolicy(bucket, "");
		}
	}

	/**
	 * 上传文件到服务器
	 * @param bucket
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public MinioFile upload(String bucket, File file) throws Exception{
		byte[] bytes = FileUtils.readFileToByteArray(file);
		MinioFile minioFile = null;
		try(InputStream inputStream = new ByteArrayInputStream(bytes)){
			minioFile = this.upload(bucket, file.getName(), inputStream);
		}catch(Exception e){
			throw e;
		}
		return minioFile;
	}

	/**
	 * 上传文件
	 * @author guobingbing
	 * @date 2020/12/26 13:44
	 * @param bucket
	 * @param fileName
	 * @param stream
	 * @return cn.fetosoft.chat.core.minio.MinioFile
	 */
	public MinioFile upload(String bucket, String fileName, InputStream stream) throws Exception{
		this.createBucket(bucket);
		MinioFile minioFile = this.buildFile(fileName);
		minioFile.setBucket(bucket);
		minioFile.setRelativePath("/" + bucket + "/" + minioFile.getNewName());
		PutObjectOptions options = new PutObjectOptions(stream.available(), -1);
		String contentType = this.genContentType(fileName);
		if(StringUtils.isNotBlank(contentType)){
			options.setContentType(contentType);
		}
		minioClient.putObject(bucket, minioFile.getNewName(), stream, options);
		minioFile.setFileUrl(minioClient.getObjectUrl(bucket, minioFile.getNewName()));
		return minioFile;
	}

	/**
	 * 创建MinioFile基本信息
	 * @param fileName
	 * @return
	 */
	private MinioFile buildFile(String fileName){
		MinioFile minioFile = new MinioFile();
		minioFile.setOriName(fileName);
		String format = fileName.substring(fileName.lastIndexOf("."));
		minioFile.setFormat(format);
		String newName = UUidUtil.buildUUID() + format;
		minioFile.setObjectName(newName);
		minioFile.setNewName(newName);
		return minioFile;
	}

	/**
	 * 生成ContentType
	 * @param fileName
	 * @return
	 */
	private String genContentType(String fileName){
		String contentType = null;
		if (fileName.lastIndexOf(".") > 0) {
			String foramt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
			if(".jpeg".equals(foramt) || "jpg".equals(foramt)){
				contentType = "image/jpeg";
			}else if(".png".equals(foramt)){
				contentType = "image/png";
			}else if(".git".equals(foramt)){
				contentType = "image/gif";
			}
		}
		return contentType;
	}

	/**
	 * 读取文件流
	 * @param bucket
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public InputStream readFile(String bucket, String fileName) throws Exception{
		return minioClient.getObject(bucket, fileName);
	}

	/**
	 * 获取文件的URL
	 * @param bucket
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String getFileUrl(String bucket, String fileName) throws Exception{
		return minioClient.getObjectUrl(bucket, fileName);
	}
}
