package com.atd.official.mapper;

import com.atd.official.entity.Image;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {
    Image getById(String Id);

    List<Image> getDisplay();

    List<Image> getAll();

    Integer insertImage(Image image);

    Integer removeImage(String Id);

    Integer updateText(Image image);

    Integer updateDisplay(Image image);
}
