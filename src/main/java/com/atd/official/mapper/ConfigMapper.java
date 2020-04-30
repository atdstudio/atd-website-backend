package com.atd.official.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfigMapper {

    String getConfig(String name);
}
