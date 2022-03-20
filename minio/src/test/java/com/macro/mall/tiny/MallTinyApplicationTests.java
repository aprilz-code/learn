package com.macro.mall.tiny;

import com.macro.mall.tiny.controller.MinioController;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@SpringBootTest(classes = MallTinyApplication.class)
public class MallTinyApplicationTests {

    @Autowired
    MinioController minioController;

    @Test
    public void contextLoads() throws Exception {
        File file = new File("E:\\download\\4d052e3e2493613f237df089ff9cf96d.jpeg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        minioController.upload(multipartFile);
    }

}
