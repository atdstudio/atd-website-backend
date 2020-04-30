package com.atd.official.controller;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.service.FeedbackService;
import com.atd.official.tools.ParameterChecker;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value="/submit",method= RequestMethod.POST)
    public JSONObject feedback(@RequestBody JSONObject requestJson, HttpServletRequest request){
        ParameterChecker.feedbackCheck(requestJson);
        return feedbackService.submitFeedback(requestJson, request);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value="/show",method= RequestMethod.GET)
    public JSONObject showFeedback(){
        return feedbackService.showFeedback();
    }

}
