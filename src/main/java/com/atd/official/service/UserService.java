package com.atd.official.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    //登录
    JSONObject authLogin(JSONObject requestjson, HttpServletRequest request);
    //注册
    JSONObject registUser(JSONObject requestjson, HttpServletRequest request);
    //修改密码
    JSONObject changeUserPass(JSONObject requestjson);
    //忘记密码
    JSONObject changeUserPass(JSONObject requestjson, HttpServletRequest request);
}
