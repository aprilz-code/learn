package com.lsh.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Classname UploadController
 * @Description TODO
 * @Date 2022/3/18 14:20
 * @Created by white
 */
@RestController
public class UploadController {

    private final static String utf8 = "utf-8";

    @PostMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //分片
        response.setCharacterEncoding(utf8);
        Integer schunk = null;//记录当前分片数
        Integer schunks = null;//总分片数
        String name = null; //文件名
        String uploadPath = "E:\\fileItem";//文件存储目录
        BufferedOutputStream os = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024);//缓冲区
            factory.setRepository(new File(uploadPath));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(5l* 1024l * 1024l * 1024l);//byte
            upload.setSizeMax(10l * 1024l * 1024l * 1024l);
            List<FileItem> items = upload.parseRequest(request);

            for (FileItem item : items) {
                if (item.isFormField()) {
                    if ("chunk".equals(item.getFieldName())) {
                        schunk = Integer.parseInt(item.getString(utf8));
                    }
                    if ("chunks".equals(item.getFieldName())) {
                        schunks = Integer.parseInt(item.getString(utf8));
                    }
                    if ("name".equals(item.getFieldName())) {
                        name = item.getString(utf8);
                    }
                }
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String temFileName = name;
                    if (name != null) {
                        if (schunk != null) {
                            temFileName = schunk + "_" + name;
                        }
                        File temFile = new File(uploadPath, temFileName);
                        if (!temFile.exists()) {//断点续传
                            item.write(temFile);
                        }
                    }
                }
            }
            //文件合并
            if (schunk != null && schunk.intValue() == schunks.intValue() - 1) {
                File tempFile = new File(uploadPath, name);
                os = new BufferedOutputStream(new FileOutputStream(tempFile));

                for (int i = 0; i < schunks; i++) {
                    File file = new File(uploadPath, i + "_" + name);
                    while (!file.exists()) {
                        Thread.sleep(100);
                    }
                    byte[] bytes = FileUtils.readFileToByteArray(file);
                    os.write(bytes);
                    os.flush();
                    file.delete();
                }
                os.flush();
            }
            response.getWriter().write("上传成功" + name);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
