package com.atd.official.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface EmailService {

     Integer sendVerify(String username, String email);

     JSONObject verifyEmail(JSONObject requestJson, HttpServletRequest request);

     JSONObject forgetEmail(JSONObject requestJson, HttpServletRequest request);


}
