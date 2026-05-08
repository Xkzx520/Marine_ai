package com.springboot.marine_ai.controller;

import com.springboot.marine_ai.common.Result;
import com.springboot.marine_ai.service.UserService;
import com.springboot.marine_ai.vo.LoginRequest;
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