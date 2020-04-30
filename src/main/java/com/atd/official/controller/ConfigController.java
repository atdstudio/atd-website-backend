package com.atd.official.controller;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.service.ConfigService;
import com.atd.official.service.impl.ConfigServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "config", method= RequestMethod.GET)
    public JSONObject getLatestVideo(String name){
        return configService.getConfig(name);
    }
}
