package com.atd.official.controller;

import com.atd.official.entity.User;
import com.atd.official.service.impl.EmailServiceimpl;
import com.atd.official.service.impl.UserServiceimpl;
import com.atd.official.tools.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    private UserServiceimpl userService;

    @Autowired
    private EmailServiceimpl emailService;

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject login(@RequestBody JSONObject requestJson,
                            HttpServletRequest request) {
        ParameterChecker.loginCheck(requestJson);
        return userService.authLogin(requestJson, request);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public JSONObject logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        JSONObject info = new JSONObject();
        info.put("msg", "注销成功");
        redisUtil.del(session.getId());
        return CommonUtil.successJson(info);
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaUtil captcha = new CaptchaUtil();
        String ver = captcha.createCaptcha(request, response);
        String id = Constants.CODE_PREFIX + request.getSession().getId();
        redisUtil.set(id, ver, 300);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public JSONObject profile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User u = (User) redisUtil.get(session.getId());
        JSONObject info = new JSONObject();
        info.put("register_ip", u.getRegister_ip());
        info.put("user_registered", u.getUser_registered());
        info.put("email", u.getUser_email());
        info.put("username", u.getUser_login());
        return CommonUtil.successJson(info);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject register(@RequestBody JSONObject requestJson, HttpServletRequest request) {
        ParameterChecker.registerCheck(requestJson);

        return userService.registUser(requestJson, request);
    }

    @RequestMapping(value = "/alterPass", method = RequestMethod.POST)
    public JSONObject alterPass(@RequestBody JSONObject requestJson,
                                HttpServletRequest request) {
        ParameterChecker.alterPassCheck(requestJson);

        HttpSession session = request.getSession();
        User u = (User) redisUtil.get(session.getId());
        requestJson.put("username", u.getUser_login());

        return userService.changeUserPass(requestJson);
    }

    @RequestMapping(value = "/forgetPass", method = RequestMethod.POST)
    public JSONObject forgetPass(@RequestBody JSONObject requestJson, HttpServletRequest request) {
            ParameterChecker.forgetPassCheck(requestJson);
            return userService.changeUserPass(requestJson,request);
    }

    @RequestMapping(value = "/verifyEmail", method = RequestMethod.POST)
    public JSONObject emailVerify(@RequestBody JSONObject requestJson, HttpServletRequest request) {
        ParameterChecker.emailCheck(requestJson);
        return emailService.verifyEmail(requestJson, request);
    }

    @RequestMapping(value = "/forgetEmail", method = RequestMethod.POST)
    public JSONObject forgetEmail(@RequestBody JSONObject requestJson, HttpServletRequest request) {
        ParameterChecker.emailCheck(requestJson);
        return emailService.forgetEmail(requestJson,request);
    }

}
