package com.springboot.marine_ai.service.impl;

import com.springboot.marine_ai.entity.User;
import com.springboot.marine_ai.mapper.UserMapper;
import com.springboot.marine_ai.service.UserService;
import com.springboot.marine_ai.util.PasswordUtil;
import com.springboot.marine_ai.vo.ChangePasswordRequest;
import com.springboot.marine_ai.vo.LoginRequest;
import com.springboot.marine_ai.vo.RegisterRequest;
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
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new IllegalArgumentException("密码至少 6 位");
        }
        User user = userMapper.selectByUsername(request.getUsername().trim());
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        if (request.getRole() != null && !request.getRole().trim().isEmpty()
                && !request.getRole().trim().equalsIgnoreCase(user.getRole())) {
            throw new IllegalArgumentException("身份与账号不匹配，请选择正确的教师/学生身份");
        }
        return convertToVO(user);
    }

    @Override
    public UserVO register(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new IllegalArgumentException("密码至少 6 位");
        }
        String username = request.getUsername().trim();
        if (userMapper.selectByUsername(username) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hash(request.getPassword()));
        user.setNickname(request.getNickname() != null && !request.getNickname().isBlank()
                ? request.getNickname().trim() : username);
        String role = request.getRole() != null ? request.getRole().trim() : "student";
        user.setRole(role.isEmpty() ? "student" : role);
        userMapper.insert(user);
        return convertToVO(userMapper.selectById(user.getUserId()));
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        if (request.getOldPassword() == null || request.getOldPassword().length() < 6) {
            throw new IllegalArgumentException("原密码至少 6 位");
        }
        if (request.getNewPassword() == null || request.getNewPassword().length() < 6) {
            throw new IllegalArgumentException("新密码至少 6 位");
        }
        User user = userMapper.selectById(request.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!PasswordUtil.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("原密码错误");
        }
        userMapper.updatePassword(user.getUserId(), PasswordUtil.hash(request.getNewPassword()));
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
