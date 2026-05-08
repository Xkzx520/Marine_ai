package com.springboot.marine_ai.vo;

import lombok.Data;

@Data
public class UserVO {
    private Long userId;
    private String username;
    private String nickname;
    private String role;
}