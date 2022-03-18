package com.lsh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2022/3/18 14:13
 * @Created by white
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return  "test";
    }
}
