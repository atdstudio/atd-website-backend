package com.atd.official.mapper;

import com.atd.official.entity.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoMapper {

    Video getById(String Id);

    Integer click(Integer Id);

    List<Video> getHotVideo();

    List<Video> getLatestVideo();

    List<Video> getVideoByClass(String videoClass, Integer page);

    Integer getVideoByClassNum(String videoClass);

    List<Video> searchVideo(String key);

    Integer insertVideo(Video video);

    Integer delVideo(String Id);

    List<Video> allVideo();

    Integer tempChanger(Video video);
}
