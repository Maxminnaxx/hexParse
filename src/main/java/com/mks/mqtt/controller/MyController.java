package com.mks.mqtt.controller;

import com.mks.mqtt.dto.MyDto;
import com.mks.mqtt.service.MyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhb
 */
@RequestMapping("web")
@RestController
public class MyController {
    @Resource
    MyService myService;

    @GetMapping("myApi")
    public MyDto myApi(){
        return myService.func();
    }
}
