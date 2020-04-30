package com.atd.official.controller;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.exception.CommonJsonException;
import com.atd.official.service.SoftWareService;
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
@RequestMapping("software")
public class SoftWareController {
    @Autowired
    private SoftWareService softWareService;

    @RequestMapping(value = "click", method= RequestMethod.GET)
    public JSONObject doclick(Integer Id){
        if(Id<0 || Id==null){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
        return softWareService.click(Id);
    }

    @RequestMapping(value = "latest", method= RequestMethod.GET)
    public JSONObject getLatestSoftWare(){
        return softWareService.latestSoftWare();
    }

    @RequestMapping(value = "hot", method= RequestMethod.GET)
    public JSONObject getHotSoftWare(){
        return softWareService.hotSoftWare();
    }

    @RequestMapping(value = "class", method= RequestMethod.GET)
    public JSONObject getSoftWareByClass(String Class, Integer Page){
        if(Page<1){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
        return softWareService.softWareByClass(Class, Page);
    }

    @RequestMapping(value = "search", method= RequestMethod.GET)
    public JSONObject searchSoftWare(String key){
        return softWareService.searchSoftWare(key);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "update", method= RequestMethod.POST)
    public JSONObject updateSoftWare(@RequestBody JSONObject requestJson){
        ParameterChecker.softWareCheck(requestJson);
        return softWareService.addSoftWare(requestJson);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "delete", method= RequestMethod.POST)
    public JSONObject delSoftWare(@RequestBody JSONObject requestJson){
        String Id = requestJson.get("Id").toString();
        ParameterChecker.idCheck(requestJson);
        return softWareService.delSoftWare(Id);
    }

    @RequiresPermissions("admin")
    @RequestMapping(value = "show", method= RequestMethod.GET)
    public JSONObject showSoftWare(){
        return softWareService.allSoftWare();
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
            uploadUrl = minioUtil.uploadURL("software",objectName, 60*60*24);
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
        String coverPrefix = "softwareCover/";
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
        return softWareService.tempChanger(requestJson);
    }

}
