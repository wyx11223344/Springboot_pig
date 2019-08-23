package com.mrwan.pigcount.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class webMessageControl {

    @Api(value="网页基础数据获取" , tags="网页基础数据获取")
    @RestController
    @RequestMapping(value = "web" , method = RequestMethod.POST)
    public class webMessage {

    }

}
