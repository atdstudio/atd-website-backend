package com.atd.official.controller;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.tools.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiresPermissions("admin")
public class AdminController {
    @ResponseBody
    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public JSONObject hello(){
        return CommonUtil.successJson();
    }

}
