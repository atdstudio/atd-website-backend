package com.atd.official.mapper;

import com.atd.official.entity.SoftWare;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SoftWareMapper {

    Integer click(Integer Id);

    List<SoftWare> getHotSoftWare();

    List<SoftWare> getLatestSoftWare();

    List<SoftWare> getSoftWareByClass(String softWareClass, Integer page);

    Integer getSoftWareByClassNum(String softWareClass);

    List<SoftWare> searchSoftWare(String key);

    Integer insertSoftWare(SoftWare softWare);

    Integer delSoftWare(String Id);

    List<SoftWare> allSoftWare();

    Integer tempChanger(SoftWare softWare);
}
