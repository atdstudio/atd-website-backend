package com.atd.official.controller;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.exception.CommonJsonException;
import com.atd.official.service.ImageService;
import com.atd.official.tools.CommonUtil;
import com.atd.official.tools.ErrorEnum;
import com.atd.official.tools.MinioUtil;
import com.atd.official.tools.ParameterChecker;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "display", method= RequestMethod.GET)
    public JSONObject getDisplayImage(){
        return imageService.getDisplay();
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "all", method= RequestMethod.GET)
    public JSONObject getAll(){
        return imageService.getAll();
    }


    @RequiresPermissions("admin")
    @RequestMapping(value = "update", method= RequestMethod.POST)
    public JSONObject uploadImage(@RequestBody JSONObject requestJson){
        ParameterChecker.imageCheck(requestJson);
        return imageService.addImage(requestJson);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "delete", method= RequestMethod.POST)
    public JSONObject delImage(@RequestBody JSONObject requestJson){
        ParameterChecker.idCheck(requestJson);
        return imageService.delImage(requestJson);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "text", method= RequestMethod.POST)
    public JSONObject changeImageText(@RequestBody JSONObject requestJson){
        ParameterChecker.imageTextCheck(requestJson);
        return imageService.changeText(requestJson);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "flag", method= RequestMethod.POST)
    public JSONObject changeImageFlag(@RequestBody JSONObject requestJson){
        ParameterChecker.imageFlagCheck(requestJson);
        return imageService.changeDisplay(requestJson);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "upload", method= RequestMethod.GET)
    public JSONObject uploadCover(String objectName){
        if(objectName == null){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
        String Prefix = "index/";
        String uploadUrl = "";
        try {
            MinioUtil minioUtil = new MinioUtil();
            uploadUrl = minioUtil.uploadURL("image",Prefix + objectName, 60*60*24);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
        return CommonUtil.successJson(uploadUrl);
    }

}
