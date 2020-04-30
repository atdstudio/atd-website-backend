package com.atd.official.service;

import com.alibaba.fastjson.JSONObject;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

public interface VideoService {

    JSONObject click(Integer Id);

    JSONObject latestVideo() throws InvalidPortException, InvalidEndpointException;

    JSONObject hotVideo();

    JSONObject videoByClass(String videoClass, Integer Page);

    JSONObject searchVideo(String key);

    JSONObject addVideo(JSONObject requestjson);

    JSONObject delVideo(String Id);

    JSONObject allVideo();

    JSONObject tempChanger(JSONObject requestjson);
}
