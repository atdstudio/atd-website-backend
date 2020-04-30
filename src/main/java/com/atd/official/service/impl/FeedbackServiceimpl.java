package com.atd.official.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.entity.Feedback;
import com.atd.official.entity.User;
import com.atd.official.mapper.FeedbackMapper;
import com.atd.official.service.FeedbackService;
import com.atd.official.tools.CommonUtil;
import com.atd.official.tools.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceimpl implements FeedbackService {
    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    private RedisUtil redisUtil;

    //提交反馈
    @Override
    public JSONObject submitFeedback(JSONObject requestJson, HttpServletRequest request) {
        Feedback feedback = new Feedback();
        HttpSession session = request.getSession();
        User u = (User) redisUtil.get(session.getId());

        feedback.setUser_login(u.getUser_login());
        feedback.setName(requestJson.getString("name"));
        feedback.setTitle(requestJson.getString("title"));
        feedback.setContact(requestJson.getString("contact"));
        feedback.setDescription(requestJson.getString("description"));
        feedback.setDate(String.valueOf(new Date().getTime()));

        feedbackMapper.submitFeedback(feedback);

        return CommonUtil.successJson();
    }

    //返回所有反馈信息
    @Override
    public JSONObject showFeedback() {
        List<Feedback> result =  feedbackMapper.allFeedback();
        return CommonUtil.successJson(result);
    }
}
