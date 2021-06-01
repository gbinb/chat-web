package cn.fetosoft.chat.test.minio;

import cn.fetosoft.chat.core.minio.MinioFile;
import cn.fetosoft.chat.core.minio.MinioHelper;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * @author guobingbing
 * @create 2020/12/26 11:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MinioFileTest {

	@Autowired
	private MinioHelper minioHelper;

	@Test
	public void updateImage() throws Exception {
		MinioFile minioFile = minioHelper.upload("chat", new File("F:\\165349qpg24xka0j08kwsy.jpg"));
		System.out.println(JSON.toJSONString(minioFile));
	}
}
