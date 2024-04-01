package com.vins_nerf.user.controller;

import com.vins_nerf.core.utils.DateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    private static final String URI = "hello-world";

    @RequestMapping(path = URI)
    public String helloWorld() {
        return DateUtil.getGMTDateTime();
    }
}
