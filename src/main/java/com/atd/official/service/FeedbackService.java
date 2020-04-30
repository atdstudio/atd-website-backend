package com.atd.official.service;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.entity.Feedback;

import javax.servlet.http.HttpServletRequest;

public interface FeedbackService {
    JSONObject submitFeedback(JSONObject requestJson, HttpServletRequest request);

    JSONObject showFeedback();
}
