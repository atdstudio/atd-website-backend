package com.atd.official.service.impl;

import com.atd.official.exception.CommonJsonException;
import com.atd.official.entity.User;
import com.atd.official.mapper.UserMapper;
import com.atd.official.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.atd.official.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.atd.official.tools.ErrorEnum.E_20010;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;

    //登录
    @Override
    public JSONObject authLogin(JSONObject requestJson, HttpServletRequest request) {
        String username = requestJson.getString("username");
        String password = requestJson.getString("password");
        String code = requestJson.getString("code");
        boolean rememberMe = requestJson.getString("rememberMe").equals("1");
        CaptchaUtil captcha = new CaptchaUtil();
        Object codeInRedis = redisUtil.get(Constants.CODE_PREFIX + request.getSession().getId());
        captcha.check(code, codeInRedis);
        JSONObject info = new JSONObject();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
        try {
            currentUser.login(token);
            info.put("msg", "登录成功！");

            User profile = userMapper.getUserProfile(username);
            redisUtil.set(currentUser.getSession().getId().toString(),profile,rememberMe?7*60*60*24:60*60*24);
            return CommonUtil.successJson(info);
        } catch (AuthenticationException e) {
            return CommonUtil.errorJson(E_20010);
        }
    }

    //注册
    @Override
    public JSONObject registUser(JSONObject requestJson, HttpServletRequest request) {
        User users = new User();
        String emailCode = requestJson.getString("emailCode");

        Object codeInRedis = redisUtil.get(Constants.EMAIL_PREFIX + request.getSession().getId());
        if (codeInRedis == null) {
            throw new CommonJsonException(ErrorEnum.E_3002);
        }
        if (StringUtils.isBlank(emailCode)) {
            throw new CommonJsonException(ErrorEnum.E_3001);
        }
        String code = String.valueOf(codeInRedis).split("@@@")[1];
        if (!StringUtils.equalsIgnoreCase(emailCode, code)) {
            throw new CommonJsonException(ErrorEnum.E_3003);
        }
        String email = String.valueOf(codeInRedis).split("@@@")[0];

        String username = requestJson.getString("username");
        if(userMapper.hasUserName(username) == 1){
            throw new CommonJsonException(ErrorEnum.E_10009);
        }

        String pass = EncryptionUtil.encryption(username,requestJson.getString("password"));
        users.setUser_login(username);
        users.setUser_pass(pass);
        users.setUser_email(email);
        users.setUser_registered(String.valueOf(new Date().getTime()));
        users.setRegister_ip(IpUtil.getIpAddr(request));
        if (userMapper.insertUser(users) == 1){
            return CommonUtil.successJson();
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

    //修改密码
    @Override
    public JSONObject changeUserPass(JSONObject requestJson) {
        User users = new User();
        String username = requestJson.getString("username");

        users.setUser_login(username);
        String newpass = requestJson.getString("newpass");
        String pass = EncryptionUtil.encryption(username,newpass);
        users.setUser_pass(pass);

        if (userMapper.updateUserPass(users) == 1){
            return CommonUtil.successJson();
        }
        else {
           return CommonUtil.errorJson(ErrorEnum.E_00000);
        }
    }

    //忘记密码
    @Override
    public JSONObject changeUserPass(JSONObject requestJson, HttpServletRequest request) {
        String emailCode = requestJson.getString("emailCode");
        String newpass = requestJson.getString("newpass");

        Object codeInRedis = redisUtil.get(Constants.EMAIL_PREFIX + request.getSession().getId());
        if (codeInRedis == null) {
            throw new CommonJsonException(ErrorEnum.E_3002);
        }
        if (StringUtils.isBlank(emailCode)) {
            throw new CommonJsonException(ErrorEnum.E_3001);
        }
        String code = String.valueOf(codeInRedis).split("@@@")[1];
        if (!StringUtils.equalsIgnoreCase(emailCode, code)) {
            throw new CommonJsonException(ErrorEnum.E_3003);
        }
        String email = String.valueOf(codeInRedis).split("@@@")[0];

        User user = userMapper.findByEmail(email);
        String username = user.getUser_login();
        String pass = EncryptionUtil.encryption(username,newpass);
        user.setUser_pass(pass);

        if(userMapper.updateUserPass(user) == 1){
            return CommonUtil.successJson();
        }
        else {
            return CommonUtil.errorJson(ErrorEnum.E_00000);
        }
    }


}
