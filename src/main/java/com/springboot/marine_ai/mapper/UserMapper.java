package com.springboot.marine_ai.mapper;

import com.springboot.marine_ai.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectByUsername(String username);
    User selectById(Long userId);
}