package com.aprilz.jsoup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @Classname MvcConfig
 * @Description TODO
 * @Date 2022/11/24 21:23
 * @Created by white
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${app.video-folder}")   //application.yml中配置的
    private String videoFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:static/","classpath:META-IFA/resources/");

        registry.addResourceHandler("/tv/**")
                .addResourceLocations("file:" + videoFolder + File.separator);
    }

}
