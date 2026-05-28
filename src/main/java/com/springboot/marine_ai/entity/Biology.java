package com.springboot.marine_ai.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Biology {
    private Long id;
    private String bioName;
    private String enName;
    private String intro;
    private String habits;
    private String distribution;
    private String protectionLevel;
    private String aiFeatureTip;
    private String imgUrl;
    private LocalDateTime createTime;
}