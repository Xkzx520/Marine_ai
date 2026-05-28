package com.springboot.marine_ai.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;
    private String username;
    private String nickname;
    private String role;
    private String password;
    private LocalDateTime createTime;
}