package com.atd.official.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.atd.official.entity.User;
import com.atd.official.mapper.UserMapper;
import com.atd.official.service.EmailService;
import com.atd.official.tools.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class EmailServiceimpl implements EmailService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    final String accessKeyId = "accessKeyId";
    final String secret = "secret";
    final String AccountName = "AccountName@test.com";
    final String FromAlias = "ATD计算机协会";

    private IAcsClient getClient(){
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        return new DefaultAcsClient(profile);
    }

    private String getCode(String email, String sessionId){
        String code =  RandomStringUtils.randomAlphanumeric(6);
        redisUtil.set(Constants.EMAIL_PREFIX + sessionId, email + "@@@" + code,60*60);
        //用@@@来做邮箱和验证码的分隔符
        return code;
    }

    public Integer sendVerify(String sessionId,String email, String username){

        String Code = getCode(email, sessionId);
        String emailBody = (Constants.VERIFY_EMAIL.replace("%",username)).replace("$",Code);

        IAcsClient client = getClient();

        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName(AccountName);
            request.setFromAlias(FromAlias);
            request.setAddressType(1);
            //request.setTagName("控制台创建的标签");
            request.setReplyToAddress(false);
            request.setToAddress(email);
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress("邮箱1,邮箱2");
            request.setSubject("ATD计算机协会官网邮箱验证");
            //如果采用byte[].toString的方式的话请确保最终转换成utf-8的格式再放入htmlbody和textbody，若编码不一致则会被当成垃圾邮件。
            //注意：文本邮件的大小限制为3M，过大的文本会导致连接超时或413错误
            request.setHtmlBody(emailBody);
            //SDK 采用的是http协议的发信方式, 默认是GET方法，有一定的长度限制。
            //若textBody、htmlBody或content的大小不确定，建议采用POST方式提交，避免出现uri is not valid异常
            request.setMethod(MethodType.POST);
            //开启需要备案，0关闭，1开启
            //request.setClickTrace("0");
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            return 1;
        } catch (ClientException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
            return 0;
        }

    }

    public Integer sendVerify(String sessionId, String email){

        String Code = getCode(email, sessionId);
        String emailBody = (Constants.VERIFY_EMAIL.replace("%","")).replace("$",Code);

        IAcsClient client = getClient();

        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName(AccountName);
            request.setFromAlias(FromAlias);
            request.setAddressType(1);
            //request.setTagName("控制台创建的标签");
            request.setReplyToAddress(false);
            request.setToAddress(email);
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress("邮箱1,邮箱2");
            request.setSubject("ATD计算机协会官网邮箱验证");
            //如果采用byte[].toString的方式的话请确保最终转换成utf-8的格式再放入htmlbody和textbody，若编码不一致则会被当成垃圾邮件。
            //注意：文本邮件的大小限制为3M，过大的文本会导致连接超时或413错误
            request.setHtmlBody(emailBody);
            //SDK 采用的是http协议的发信方式, 默认是GET方法，有一定的长度限制。
            //若textBody、htmlBody或content的大小不确定，建议采用POST方式提交，避免出现uri is not valid异常
            request.setMethod(MethodType.POST);
            //开启需要备案，0关闭，1开启
            //request.setClickTrace("0");
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            return 1;
        } catch (ClientException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public JSONObject verifyEmail(JSONObject requestJson, HttpServletRequest request) {
        String email = requestJson.getString("email");
        String code = requestJson.getString("code");
        String sessionId = request.getSession().getId();

        //验证码检验
        CaptchaUtil captcha = new CaptchaUtil();
        Object codeInRedis = redisUtil.get(Constants.CODE_PREFIX + sessionId);
        captcha.check(code, codeInRedis);

        if(userMapper.hasEmail(email) == 1){
            return CommonUtil.errorJson(ErrorEnum.E_10010);
        }

        if(this.sendVerify(sessionId,email) == 0){
            return CommonUtil.errorJson(ErrorEnum.E_00000);
        }

        return CommonUtil.successJson();
    }

    @Override
    public JSONObject forgetEmail(JSONObject requestJson, HttpServletRequest request) {
        String email = requestJson.getString("email");
        String code = requestJson.getString("code");
        String sessionId = request.getSession().getId();
        //验证码检验
        CaptchaUtil captcha = new CaptchaUtil();
        Object codeInRedis = redisUtil.get(Constants.CODE_PREFIX + sessionId);
        captcha.check(code, codeInRedis);

        if(userMapper.hasEmail(email) == 0){
            return CommonUtil.errorJson(ErrorEnum.E_10011);
        }

        User user = userMapper.findByEmail(email);
        String username = user.getUser_login();

        if(this.sendVerify(sessionId, email, username) == 0){
            return CommonUtil.errorJson(ErrorEnum.E_00000);
        }

        return CommonUtil.successJson();
    }


}