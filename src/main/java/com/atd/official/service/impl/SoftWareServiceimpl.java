package com.atd.official.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.entity.SoftWare;
import com.atd.official.entity.Video;
import com.atd.official.exception.CommonJsonException;
import com.atd.official.mapper.SoftWareMapper;
import com.atd.official.service.SoftWareService;
import com.atd.official.tools.CommonUtil;
import com.atd.official.tools.ErrorEnum;
import com.atd.official.tools.MinioUtil;
import com.atd.official.tools.RedisUtil;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SoftWareServiceimpl implements SoftWareService {
    @Autowired
    SoftWareMapper softWareMapper;

    @Resource
    private RedisUtil redisUtil;

    //对应ID软件点击量递增1
    @Override
    public JSONObject click(Integer Id) {
        softWareMapper.click(Id);
        return CommonUtil.successJson();
    }

    //返回最新4个软件信息
    @Override
    public JSONObject latestSoftWare() {
        if(redisUtil.hasKey("latestSoftWare")){
            return CommonUtil.successJson(redisUtil.get("latestSoftWare"));
        }

        try {
            List<SoftWare> result = softWareMapper.getLatestSoftWare();
            MinioUtil minioUtil = new MinioUtil();
            result = minioUtil.downloadSoftWareURL(result);
            redisUtil.set("latestSoftWare",result, 5 * 60 * 60);
            return CommonUtil.successJson(result);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
    }

    //返回最热4个软件信息
    @Override
    public JSONObject hotSoftWare() {
        if(redisUtil.hasKey("hotSoftWare")){
            return CommonUtil.successJson(redisUtil.get("hotSoftWare"));
        }

        try {
            List<SoftWare> result = softWareMapper.getHotSoftWare();
            MinioUtil minioUtil = new MinioUtil();
            result = minioUtil.downloadSoftWareURL(result);
            redisUtil.set("hotSoftWare",result, 5 * 60 * 60);
            return CommonUtil.successJson(result);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
    }

    //通过软件类型返回结果，6个为一页
    @Override
    public JSONObject softWareByClass(String softWareClass, Integer Page) {
        try {
            JSONObject result = new JSONObject();
            int num;
            num = softWareMapper.getSoftWareByClassNum(softWareClass);
            Page = (Page-1)*5;
            List<SoftWare> li = softWareMapper.getSoftWareByClass(softWareClass, Page);
            MinioUtil minioUtil = new MinioUtil();
            li = minioUtil.downloadSoftWareURL(li);
            result.put("num",num);
            result.put("list",li);
            return CommonUtil.successJson(result);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
    }

    //根据软件关键字搜索视频
    @Override
    public JSONObject searchSoftWare(String key) {
        try {
            List<SoftWare> result = softWareMapper.searchSoftWare(key);
            MinioUtil minioUtil = new MinioUtil();
            result = minioUtil.downloadSoftWareURL(result);
            return CommonUtil.successJson(result);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
    }

    //添加新的软件
    @Override
    public JSONObject addSoftWare(JSONObject requestjson) {

        SoftWare softWare = new SoftWare();
        softWare.setName(requestjson.getString("Name"));
        softWare.setIntro(requestjson.getString("Intro"));
        softWare.setLocation(requestjson.getString("Location"));
        softWare.setSoftWare_class(requestjson.getString("Software_class"));
        softWare.setCover_img(requestjson.getString("Cover_img"));
        softWare.setExplain(requestjson.getString("Explain"));
        softWare.setDate(String.valueOf(new Date().getTime()));
        softWare.setTemp(requestjson.getString("Temp"));
        softWare.setTemp_link(requestjson.getString("Temp_link"));

        if(softWareMapper.insertSoftWare(softWare) == 1){
            if(redisUtil.hasKey("latestSoftWare")){
                redisUtil.del("latestSoftWare");
            }
            if(redisUtil.hasKey("hotSoftWare")){
                redisUtil.del( "hotSoftWare");
            }

            return CommonUtil.successJson();
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

    //删除一个软件
    @Override
    public JSONObject delSoftWare(String Id) {
        if(softWareMapper.delSoftWare(Id)== 1){
            if(redisUtil.hasKey("latestSoftWare")){
                redisUtil.del("hotSoftWare");
            }
            if(redisUtil.hasKey("hotSoftWare")){
                redisUtil.del( "hotSoftWare");
            }

            return CommonUtil.successJson();
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

    //返回所有数据库中软件原始信息
    @Override
    public JSONObject allSoftWare() {
        List<SoftWare> result = softWareMapper.allSoftWare();
        return CommonUtil.successJson(result);
    }

    //修改临时链接状态
    @Override
    public JSONObject tempChanger(JSONObject requestjson) {
        SoftWare softWare = new SoftWare();
        softWare.setId(requestjson.getString("Id"));
        softWare.setTemp(requestjson.getString("Temp"));
        softWare.setTemp_link(requestjson.getString("Temp_link"));

        if(softWareMapper.tempChanger(softWare)== 1){
            return CommonUtil.successJson();
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

}
