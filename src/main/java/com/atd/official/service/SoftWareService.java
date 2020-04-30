package com.atd.official.service;

import com.alibaba.fastjson.JSONObject;

public interface SoftWareService {

    JSONObject click(Integer Id);

    JSONObject latestSoftWare();

    JSONObject hotSoftWare();

    JSONObject softWareByClass(String videoClass, Integer Page);

    JSONObject searchSoftWare(String key);

    JSONObject addSoftWare(JSONObject requestjson);

    JSONObject delSoftWare(String Id);

    JSONObject allSoftWare();

    JSONObject tempChanger(JSONObject requestjson);
}
