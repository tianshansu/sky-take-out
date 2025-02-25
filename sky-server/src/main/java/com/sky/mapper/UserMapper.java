package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid=#{openid}")
    User getUserByOpenId(String openId);

    /**
     * add new user
     * @param newUser newUser
     */
    void insert(User newUser);

    /**
     * user count
     * @param beginTime beginTime
     * @param endTime endTime
     * @return total user count within the time range
     */
    Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime);
}
