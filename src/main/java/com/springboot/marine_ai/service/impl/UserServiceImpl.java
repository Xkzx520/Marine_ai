package com.springboot.marine_ai.service;

import com.springboot.marine_ai.common.ResultCode;
import com.springboot.marine_ai.entity.User;
import com.springboot.marine_ai.mapper.UserMapper;
import com.springboot.marine_ai.vo.LoginRequest;
import com.springboot.marine_ai.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserVO login(LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        User user = userMapper.selectByUsername(request.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return convertToVO(user);
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return convertToVO(user);
    }

    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        vo.setUserId(user.getUserId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setRole(user.getRole());
        return vo;
    }
}