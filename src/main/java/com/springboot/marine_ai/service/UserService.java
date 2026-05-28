package com.springboot.marine_ai.service;

import com.springboot.marine_ai.vo.ChangePasswordRequest;
import com.springboot.marine_ai.vo.LoginRequest;
import com.springboot.marine_ai.vo.RegisterRequest;
import com.springboot.marine_ai.vo.UserVO;

public interface UserService {
    UserVO login(LoginRequest request);

    UserVO register(RegisterRequest request);

    void changePassword(ChangePasswordRequest request);

    UserVO getUserInfo(Long userId);
}
