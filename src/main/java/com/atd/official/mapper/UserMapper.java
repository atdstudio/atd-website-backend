package com.atd.official.mapper;

import com.atd.official.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    User findByUsername(String userName);

    User findByEmail(String email);

    Integer insertUser(User user);

    Integer updateUserPass(User user);

    User getUserProfile(String userName);

    Integer hasUserName(String userName);

    Integer hasEmail(String email);
}
