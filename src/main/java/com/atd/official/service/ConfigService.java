package com.atd.official.service;

import com.alibaba.fastjson.JSONObject;

public interface ConfigService {
    JSONObject getConfig(String name);
}
