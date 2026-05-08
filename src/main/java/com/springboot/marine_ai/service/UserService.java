package com.springboot.marine_ai.service;

import com.springboot.marine_ai.entity.User;
import com.springboot.marine_ai.vo.LoginRequest;
import com.springboot.marine_ai.vo.UserVO;

public interface UserService {
    UserVO login(LoginRequest request);
    UserVO getUserInfo(Long userId);
}