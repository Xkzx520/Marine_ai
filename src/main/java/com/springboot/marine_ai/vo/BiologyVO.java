package com.springboot.marine_ai.vo;

import lombok.Data;

@Data
public class BiologyVO {
    private Long id;
    private String bioName;
    private String enName;
    private String intro;
    private String habits;
    private String distribution;
    private String protectionLevel;
    private String aiFeatureTip;
    private String imgUrl;
}