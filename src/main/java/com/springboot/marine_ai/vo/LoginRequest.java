package com.springboot.marine_ai.vo;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String role;
}