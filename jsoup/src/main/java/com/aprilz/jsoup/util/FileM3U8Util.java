package com.aprilz.jsoup.util;


import cn.hutool.core.io.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FileM3U8Util {
    private static final String encoding = "utf-8";

    public static List<String> getM3U8Ts4HttlUrl(String httpUrl) throws IOException{
        String prefixHttpUrl = httpUrl.substring(0, httpUrl.lastIndexOf("/") + 1);
        URL url = new URL(httpUrl);
        URLConnection urlConn = url.openConnection();
        InputStream in = urlConn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding));
        String strTxt;
        List<String> ls = new ArrayList();
        while (null != (strTxt = br.readLine())) {
            if ("ts".equals(FileUtil.getSuffix(strTxt))) {
                ls.add( prefixHttpUrl +strTxt);
            }
        }
        if (null != br) {
            br.close();
        }
        if (null != in) {
            in.close();
        }
        return ls;
    }


}
