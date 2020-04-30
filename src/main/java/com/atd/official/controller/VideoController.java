package com.atd.official.controller;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.exception.CommonJsonException;
import com.atd.official.service.VideoService;
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
@RequestMapping("video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @RequestMapping(value = "click", method= RequestMethod.GET)
    public JSONObject doclick(Integer Id){
        return videoService.click(Id);
    }

    @RequestMapping(value = "latest", method= RequestMethod.GET)
    public JSONObject getLatestVideo() throws InvalidPortException, InvalidEndpointException {
        return videoService.latestVideo();
    }

    @RequestMapping(value = "hot", method= RequestMethod.GET)
    public JSONObject getHotVideo(){
        return videoService.hotVideo();
    }

    @RequestMapping(value = "class", method= RequestMethod.GET)
    public JSONObject getVideoByClass(String Class, Integer Page){
        if(Class != null && Page != null){
            if(Page<1){
                throw new CommonJsonException(ErrorEnum.E_90003);
            }
            return videoService.videoByClass(Class, Page);
        }
        else {
            throw new CommonJsonException(ErrorEnum.E_90003);
        }

    }

    @RequestMapping(value = "search", method= RequestMethod.GET)
    public JSONObject searchVideo(String key){
        if(key == null){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
        return videoService.searchVideo(key);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "update", method= RequestMethod.POST)
    public JSONObject updateVideo(@RequestBody JSONObject requestJson){
        ParameterChecker.videoCheck(requestJson);
        return videoService.addVideo(requestJson);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "delete", method= RequestMethod.POST)
    public JSONObject delVideo(@RequestBody JSONObject requestJson){
        ParameterChecker.idCheck(requestJson);
        String Id = requestJson.get("Id").toString();
        return videoService.delVideo(Id);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "show", method= RequestMethod.GET)
    public JSONObject showVideo(){
        return videoService.allVideo();
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "uploadFile", method= RequestMethod.GET)
    public JSONObject uploadFile(String objectName){
        if(objectName == null){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
        String uploadUrl = "";
        try {
            MinioUtil minioUtil = new MinioUtil();
            uploadUrl = minioUtil.uploadURL("video",objectName, 60*60*24);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
        return CommonUtil.successJson(uploadUrl);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "uploadCover", method= RequestMethod.GET)
    public JSONObject uploadCover(String objectName){
        if(objectName == null){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
        String coverPrefix = "videoCover/";
        String uploadUrl = "";
        try {
            MinioUtil minioUtil = new MinioUtil();
            uploadUrl = minioUtil.uploadURL("image",coverPrefix + objectName, 60*60*24);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
        return CommonUtil.successJson(uploadUrl);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "temp", method= RequestMethod.POST)
    public JSONObject tempVideo(@RequestBody JSONObject requestJson){
        ParameterChecker.tempCheck(requestJson);
        return videoService.tempChanger(requestJson);
    }

}
