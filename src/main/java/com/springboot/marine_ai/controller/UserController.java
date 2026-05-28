package com.springboot.marine_ai.controller;

import com.springboot.marine_ai.common.Result;
import com.springboot.marine_ai.service.UserService;
import com.springboot.marine_ai.vo.ChangePasswordRequest;
import com.springboot.marine_ai.vo.LoginRequest;
import com.springboot.marine_ai.vo.RegisterRequest;
import com.springboot.marine_ai.vo.UserVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody LoginRequest request) {
        try {
            UserVO user = userService.login(request);
            return Result.success("登录成功", user);
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody RegisterRequest request) {
        try {
            UserVO user = userService.register(request);
            return Result.success("注册成功", user);
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public Result<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            userService.changePassword(request);
            return Result.success("密码修改成功", null);
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        }
    }

    @GetMapping("/info/{userId}")
    public Result<UserVO> getUserInfo(@PathVariable Long userId) {
        try {
            UserVO user = userService.getUserInfo(userId);
            return Result.success(user);
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        }
    }
}
