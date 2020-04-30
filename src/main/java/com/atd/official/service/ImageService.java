package com.atd.official.service;


import com.alibaba.fastjson.JSONObject;

public interface ImageService {
    JSONObject getDisplay();

    JSONObject getAll();

    JSONObject addImage(JSONObject requestjson);

    JSONObject delImage(JSONObject requestjson);

    JSONObject changeText(JSONObject requestjson);

    JSONObject changeDisplay(JSONObject requestjson);
}
