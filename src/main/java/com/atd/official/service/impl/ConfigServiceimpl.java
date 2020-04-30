package com.atd.official.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.mapper.ConfigMapper;
import com.atd.official.service.ConfigService;
import com.atd.official.tools.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceimpl implements ConfigService {
    @Autowired
    ConfigMapper configMapper;

    @Override
    public JSONObject getConfig(String name) {
        String result = configMapper.getConfig(name);
        return CommonUtil.successJson(result);
    }
}
