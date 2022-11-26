package com.aprilz.jsoup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Classname IndexController
 * @Description TODO
 * @Date 2022/11/24 21:47
 * @Created by white
 */
@Controller
public class IndexController {

    @GetMapping(value = {"/","/index"},name = "首页")
    public String index(){
        return "index";
    }
}
