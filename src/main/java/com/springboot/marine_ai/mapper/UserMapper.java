package com.springboot.marine_ai.mapper;

import com.springboot.marine_ai.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    User selectByUsername(String username);

    User selectById(Long userId);

    List<User> selectByRole(@Param("role") String role);

    int insert(User user);

    int updatePassword(Long userId, String password);
}
