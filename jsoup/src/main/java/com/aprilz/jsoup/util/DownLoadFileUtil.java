package com.aprilz.jsoup.util;

import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Classname DownLoadFileUtil
 * @Description TODO
 * @Date 2022/11/24 17:41
 * @Created by white
 */
public class DownLoadFileUtil {

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr url的路径
     * @throws IOException
     */
    public static void downLoadByUrl(String urlStr, String savePath) {
        String fileName = getFileName(urlStr);
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) { // 没有就创建该文件
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            fos.write(getData);


            System.out.println("the file: " + url + " download success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();

                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4 * 1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        try {
            String filePath = "https://docs.spring.io/spring-framework/docs/4.2.0.RC1/spring-framework-reference/pdf//spring-framework-reference.pdf";
            filePath = "https://t20.cdn2020.com/video/m3u8/2022/11/19/2bb8c16e/index.m3u8";

            downLoadByUrl(filePath, "E://tv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从src文件路径获取文件名
     *
     * @param srcRealPath src文件路径
     * @return 文件名
     */
    private static String getFileName(String srcRealPath) {
        return StrUtil.subAfter(srcRealPath, "/", true);
    }
}
