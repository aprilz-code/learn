package com.aprilz.jsoup;

import com.aprilz.jsoup.util.DownLoadFileUtil;
import com.aprilz.jsoup.util.FileM3U8Util;
import com.aprilz.jsoup.util.UrlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class JsoupApplicationTests {

    @Value("${app.video-folder}")
    private  String deskUrl;

    @Test
    void contextLoads() throws Exception {
        String url = String.format("https://localhost/index.php/art/type/id/18/page/%s.html?wd=",1);
        Document doc = Jsoup.connect(url).get();
        String title = doc.title();
        System.out.println(title);
        Elements pa = doc.select("div.pa");
        String text = pa.text();
        String substring = text.substring(text.lastIndexOf("/"));
        String page = extractNumber(substring);
        // 第一步获取总页数

        Elements a = doc.select("div.obxobxpd_md > div > a");

        for(int i = 0; i< a.size() ;i++){
            Element aEle = a.get(i);
            String href = aEle.attr("href");
            Elements imgEle = aEle.select("img.img_1");
            String src = imgEle.attr("src");

           // System.out.println(href +"===" +src);
            UrlUtil.UrlEntity parse = UrlUtil.parse(href);
            Map<String, String> params = parse.params;
            //m3u8 路径
            String v = params.get("v");
            //文件名
            String m = params.get("m");
            String prefixUrl = deskUrl + File.separator + m;
            // src图片下载到本地去
            DownLoadFileUtil.downLoadByUrl(src,prefixUrl);
            // 把m3u8下载到本地去
            DownLoadFileUtil.downLoadByUrl(v,prefixUrl);
            //解析m3u8中的所有ts文件
            List<String> m3U8Ts4HttlUrl = FileM3U8Util.getM3U8Ts4HttlUrl(v);
            System.out.println(m3U8Ts4HttlUrl);
            //TODO 考虑开多线程下载ts文件
            m3U8Ts4HttlUrl.parallelStream().forEach(ts -> {
                DownLoadFileUtil.downLoadByUrl(ts,prefixUrl);
            });

        }

    }

    private static String extractNumber(String input){
        return  input.replaceAll("[^\\d]", "");
    }



}
