package cn.fetosoft.chat.controller;

import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.controller.BaseController;
import cn.fetosoft.chat.core.data.entity.MsgFile;
import cn.fetosoft.chat.core.data.service.MsgFileService;
import cn.fetosoft.chat.core.encrypt.AesCipherUtil;
import cn.fetosoft.chat.core.enums.DateFormatEnum;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.minio.MinioFile;
import cn.fetosoft.chat.core.minio.MinioHelper;
import cn.fetosoft.chat.core.utils.UUidUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guobingbing
 * @create 2020/12/26 15:34
 */
@Controller
@RequestMapping("/chat/file")
public class FileController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	private static final String PREVIEW_FORMAT = ".jpg.png.bmp.gif.jpeg";
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private MinioHelper minioHelper;
	@Autowired
	private MsgFileService msgFileService;

	/**
	 * 文件上传
	 * @author guobingbing
	 * @date 2020/12/26 9:40
	 * @param file
	 * @return java.lang.String
	 */
	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file, @RequestParam("token") String token){
		RespResult<Map<String, String>> result = RespResult.create(RespCode.BUSI_ERROR);
		try{
			RespResult<Map<String, String>> verifyResult = this.verifyToken(token, systemConfig.getJwt().getSecretKey());
			if(RespCode.SUCCESS.getCode().equals(verifyResult.getCode())) {
				Map<String, String> map = verifyResult.getData();
				String userId = map.get("userId");
				String fileName = file.getOriginalFilename();
				String format = "";
				if (fileName.lastIndexOf(".") > 0) {
					format = fileName.substring(fileName.lastIndexOf("."));
				}

				String yearMonthDay = DateFormatEnum.YMD.dateToString(new Date());
				String saveDir = systemConfig.getSaveFilePath() + File.separator + yearMonthDay;
				File dir = new File(saveDir);
				if (!dir.exists()) {
					boolean b = dir.mkdirs();
					if(!b){
						result.setMessage("目录没有权限");
						return result.toString();
					}
				}
				System.out.println("fileSize：" + (file.getSize()/1024) + "k");
				long size = file.getSize()/1024/1024;
				if(size>2){
					result.setMessage("文件不能超过2M");
					return result.toString();
				}
				Map<String, String> fileMap = new HashMap<>(16);
				fileMap.put("name", fileName);
				MinioFile minioFile = minioHelper.upload(systemConfig.getMinio().getMinioBucket(), fileName, file.getInputStream());
				String fileId = AesCipherUtil.encrypt(systemConfig.getAesKey(), minioFile.getObjectName());
				fileMap.put("fileId", fileId);
				if(PREVIEW_FORMAT.contains(format.toLowerCase())) {
					fileMap.put("type", "preview");
					fileMap.put("url", systemConfig.getFileDomain() + minioFile.getRelativePath());
					MinioFile zipImage = this.thumbnailImage(userId, file, format);
					if(zipImage!=null){
						fileMap.put("scaleUrl", systemConfig.getFileDomain() + zipImage.getRelativePath());
					}else{
						fileMap.put("scaleUrl", systemConfig.getFileDomain() + minioFile.getRelativePath());
					}
				}else{
					fileMap.put("type", "download");
					fileMap.put("url", systemConfig.getFileDomain() + minioFile.getRelativePath());
				}
				result.setStatus(RespCode.SUCCESS);
				result.setData(fileMap);

				try{
					minioFile.setFileUrl(fileMap.get("url"));
					this.addMsgFile(userId, fileId, minioFile);
				}catch(Exception e){
					logger.error("upload", e);
				}
			}else{
				result.setMessage("认证失败");
			}
		}catch(Exception e){
			result.setMessage("文件上传失败");
			logger.error("upload", e);
		}
		return result.toString();
	}

	/**
	 * 新增文件信息
	 * @param userId
	 * @param fileId
	 * @param minioFile
	 */
	private void addMsgFile(String userId, String fileId, MinioFile minioFile){
		MsgFile msgFile = new MsgFile();
		msgFile.setUserId(userId);
		msgFile.setFileId(fileId);
		msgFile.setBucket(systemConfig.getMinio().getMinioBucket());
		msgFile.setFileName(minioFile.getOriName());
		msgFile.setFileUrl(minioFile.getFileUrl());
		msgFile.setRelativePath(minioFile.getObjectName());
		msgFile.setFileFormat(minioFile.getFormat());
		msgFile.setCreateTime(new Date());
		msgFileService.insert(msgFile);
	}

	/**
	 * 压缩图片
	 * @param userId
	 * @param file
	 * @param format
	 * @return
	 */
	private MinioFile thumbnailImage(String userId, MultipartFile file, String format){
		//大于1M将压缩图片
		if(file.getSize() > 1024*1024){
			File dir = new File(systemConfig.getSaveFilePath() + "/thumb/" + userId);
			if(!dir.exists()){
				dir.mkdirs();
			}
			try{
				String fileId = UUidUtil.buildUUID();
				File saveFile = new File(dir.getPath() + "/" + UUidUtil.buildUUID() + fileId + format);
				Thumbnails.of(file.getInputStream()).scale(0.3f).toFile(saveFile);
				MinioFile minioFile = minioHelper.upload(systemConfig.getMinio().getMinioBucket(), saveFile);
				this.addMsgFile(userId, fileId, minioFile);
				saveFile.delete();
				return minioFile;
			}catch(Exception e){
				logger.error("thumbnailImage", e);
			}
		}
		return null;
	}

	/**
	 * 下载文件
	 * @param token
	 * @param fileId
	 * @return
	 */
	public ResponseEntity<FileSystemResource> download(@RequestParam String token, @RequestParam String fileId){
		ResponseEntity<FileSystemResource> fileResource = null;
		try{
			RespResult<Map<String, String>> verifyResult = this.verifyToken(token, systemConfig.getJwt().getSecretKey());
			if(RespCode.SUCCESS.getCode().equals(verifyResult.getCode())) {
				String filePath = AesCipherUtil.decrypt(systemConfig.getAesKey(), fileId);
				File file = new File(filePath);
				if (file.exists() && file.isFile()) {
					HttpHeaders headers = new HttpHeaders();
					headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
					headers.add("Content-Disposition", "attachment; filename=" + file.getName());
					headers.add("Pragma", "no-cache");
					headers.add("Expires", "0");
					fileResource = ResponseEntity.ok().headers(headers).contentLength(file.length())
							.contentType(MediaType.parseMediaType("application/octet-stream"))
							.body(new FileSystemResource(file));
				}
			}
		}catch(Exception e){
			logger.error("download", e);
		}
		return fileResource;
	}

	/**
	 * 文件下载
	 * @param token
	 * @param fileId
	 */
	@PostMapping(value = "/download")
	public ResponseEntity<byte[]> downloadFile(@RequestParam String token, @RequestParam String fileId){
		ResponseEntity<byte[]> fileResource = null;
		RespResult<Map<String, String>> verifyResult = this.verifyToken(token, systemConfig.getJwt().getSecretKey());
		if(RespCode.SUCCESS.getCode().equals(verifyResult.getCode())) {
			String objectName = null;
			try{
				objectName = AesCipherUtil.decrypt(systemConfig.getAesKey(), fileId);
			}catch(Exception e){
				logger.error("downloadFile", e);
			}
			try(InputStream in = minioHelper.readFile(systemConfig.getMinio().getMinioBucket(), objectName)){
				byte[] bytes = new byte[in.available()];
				in.read(bytes);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Content-Disposition", "attachment; filename=" + objectName);
				headers.add("Pragma", "no-cache");
				fileResource = ResponseEntity.ok().headers(headers).contentLength(bytes.length)
						.contentType(MediaType.parseMediaType("application/octet-stream"))
						.body(bytes);
			}catch(Exception e){
				logger.error("downloadFile", e);
			}
		}
		return fileResource;
	}
}
