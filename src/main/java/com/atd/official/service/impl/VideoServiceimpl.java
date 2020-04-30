package com.atd.official.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atd.official.entity.Video;
import com.atd.official.exception.CommonJsonException;
import com.atd.official.mapper.VideoMapper;
import com.atd.official.service.VideoService;
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
public class VideoServiceimpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    private RedisUtil redisUtil;

    //视频点击量递增一
    @Override
    public JSONObject click(Integer Id) {
        videoMapper.click(Id);
        return CommonUtil.successJson();
    }

    //返回最新3个视频
    @Override
    public JSONObject latestVideo(){
        if(redisUtil.hasKey("latestVideo")){
            return CommonUtil.successJson(redisUtil.get("latestVideo"));
        }
        //向minio查询真实资源访问链接
        try {
            List<Video> result = videoMapper.getLatestVideo();
            MinioUtil minioUtil = new MinioUtil();
            result = minioUtil.downloadVideoURL(result);
            redisUtil.set("latestVideo",result,5*60*60);
            return CommonUtil.successJson(result);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
    }

    //返回点击量最高的3个视频
    @Override
    public JSONObject hotVideo() {
        if(redisUtil.hasKey("hotVideo")){
            return CommonUtil.successJson(redisUtil.get("hotVideo"));
        }

        try {
            List<Video> result = videoMapper.getHotVideo();
            MinioUtil minioUtil = new MinioUtil();
            result = minioUtil.downloadVideoURL(result);
            redisUtil.set("hotVideo",result,5*60*60);
            return CommonUtil.successJson(result);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
    }

    //通过视频类型返回结果，6个为一页
    @Override
    public JSONObject videoByClass(String videoClass, Integer Page) {
        try {
            JSONObject result = new JSONObject();
            int num;
            num = videoMapper.getVideoByClassNum(videoClass);
            Page = (Page-1)*5;
            List<Video> li = videoMapper.getVideoByClass(videoClass, Page);
            /*从Minio获取下载链接*/
            MinioUtil minioUtil = new MinioUtil();
            li = minioUtil.downloadVideoURL(li);
            result.put("num",num);
            result.put("list",li);
            return CommonUtil.successJson(result);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
    }

    //根据关键词搜索视频
    @Override
    public JSONObject searchVideo(String key){
        try {
            List<Video> result = videoMapper.searchVideo(key);
            MinioUtil minioUtil = new MinioUtil();
            result = minioUtil.downloadVideoURL(result);
            return CommonUtil.successJson(result);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }
    }

    //添加一个视频
    @Override
    public JSONObject addVideo(JSONObject requestjson) {
        Video video = new Video();
        video.setName(requestjson.getString("Name"));
        video.setIntro(requestjson.getString("Intro"));
        video.setAuthor(requestjson.getString("Author"));
        video.setVideo_class(requestjson.getString("Video_class"));
        video.setCover_img(requestjson.getString("Cover_img"));
        video.setLocation(requestjson.getString("Location"));
        video.setDate(String.valueOf(new Date().getTime()));
        video.setTemp(requestjson.getString("Temp_link"));
        video.setTemp(requestjson.getString("Temp"));

        if(videoMapper.insertVideo(video) == 1){
            if(redisUtil.hasKey("latestVideo")){
                redisUtil.del("latestVideo");
            }
            if(redisUtil.hasKey("hotVideo")){
                redisUtil.del("hotVideo");
            }

            return CommonUtil.successJson();
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

    //删除一个视频
    @Override
    public JSONObject delVideo(String Id) {
        Video video = videoMapper.getById(Id);
        try {
            String videoFile = video.getVideo_class()+"/"+video.getLocation();
            String videoCover = "videoCover/" + video.getVideo_class()+"/"+video.getLocation();
            MinioUtil minioUtil = new MinioUtil();
            minioUtil.delFile("video",videoFile);
            minioUtil.delFile("image",videoCover);
        }catch (InvalidPortException | InvalidEndpointException e){
            throw new CommonJsonException(ErrorEnum.E_00000);
        }

        if(videoMapper.delVideo(Id) == 1){
            if(redisUtil.hasKey("latestVideo")){
                redisUtil.del("latestVideo");
            }
            if(redisUtil.hasKey("hotVideo")){
                redisUtil.del("hotVideo");
            }

            return CommonUtil.successJson();
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

    //返回所有视频原始数据库内容
    @Override
    public JSONObject allVideo() {
        List<Video> result = videoMapper.allVideo();
        return CommonUtil.successJson(result);
    }

    //改变使用临时链接状态
    @Override
    public JSONObject tempChanger(JSONObject requestjson) {
        Video video = new Video();
        video.setId(requestjson.getString("Id"));
        video.setTemp(requestjson.getString("Temp"));
        video.setTemp_link(requestjson.getString("Temp_link"));

        if(videoMapper.tempChanger(video) == 1){
            if(redisUtil.hasKey("latestVideo")){
                redisUtil.del("latestVideo");
            }
            if(redisUtil.hasKey("hotVideo")){
                redisUtil.del("hotVideo");
            }

            return CommonUtil.successJson();
        }
        return CommonUtil.errorJson(ErrorEnum.E_00000);
    }

}
