package com.atd.official.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.entity.Image;
import com.atd.official.entity.Video;
import com.atd.official.exception.CommonJsonException;
import com.atd.official.mapper.ImageMapper;
import com.atd.official.service.ImageService;
import com.atd.official.tools.*;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ImageServiceimpl  implements ImageService {
    @Autowired
    ImageMapper imageMapper;

    @Resource
    private RedisUtil redisUtil;

    //返回所有想要展示轮播图信息
    @Override
    public JSONObject getDisplay() {
        if(redisUtil.hasKey("imageDisplay")){
            return CommonUtil.successJson(redisUtil.get("imageDisplay"));
        }

        try {
            List<Image> result = imageMapper.getDisplay();
            MinioUtil minioUtil = new MinioUtil();
            result = minioUtil.downloadImageURL(result);
            redisUtil.set("imageDisplay",result,5 * 60 * 60);
            return CommonUtil.successJson(result);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
    }

    //返回所有数据库中图片原始信息
    @Override
    public JSONObject getAll() {
        List<Image> result = imageMapper.getAll();
        return CommonUtil.successJson(result);
    }

    //添加一个图片
    @Override
    public JSONObject addImage(JSONObject requestjson) {
        if(redisUtil.hasKey("imageDisplay")){
            redisUtil.del("imageDisplay");
        }

        Image image = new Image();
        JSONObject result = new JSONObject();

        image.setName(requestjson.getString("Name"));
        image.setDisplay(requestjson.getString("Display"));
        image.setLink(requestjson.getString("Link"));
        image.setLocation(requestjson.getString("Location"));
        image.setDate(String.valueOf(new Date().getTime()));

        if(imageMapper.insertImage(image) == 1){
            result.put("msg", Constants.SUCCESS_MSG);
            return CommonUtil.successJson(result);
        }

        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

    //删除一个图片
    @Override
    public JSONObject delImage(JSONObject requestjson) {
        if(redisUtil.hasKey("imageDisplay")){
            redisUtil.del("imageDisplay");
        }

        JSONObject result = new JSONObject();
        Image image = imageMapper.getById(requestjson.get("Id").toString());
        try {
            String prefix = "index/";
            MinioUtil minioUtil = new MinioUtil();
            minioUtil.delFile("image",prefix+image.getLocation());
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }

        if(imageMapper.removeImage(requestjson.get("Id").toString()) == 1){
            result.put("msg", Constants.SUCCESS_MSG);
            return CommonUtil.successJson(result);
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

    //修改轮播图信息
    @Override
    public JSONObject changeText(JSONObject requestjson) {
        if(redisUtil.hasKey("imageDisplay")){
            redisUtil.del("imageDisplay");
        }
        Image image = new Image();

        image.setName(requestjson.get("Name").toString());
        image.setId(requestjson.get("Id").toString());

        if(imageMapper.updateText(image) == 1){
            return CommonUtil.successJson();
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

    //修改图片展示状态
    @Override
    public JSONObject changeDisplay(JSONObject requestjson) {
        if(redisUtil.hasKey("imageDisplay")){
            redisUtil.del("imageDisplay");
        }
        Image image = new Image();

        image.setDisplay(requestjson.getString("Display"));
        image.setId(requestjson.getString("Id"));

        if(imageMapper.updateDisplay(image) == 1){
            return CommonUtil.successJson();
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }


}
