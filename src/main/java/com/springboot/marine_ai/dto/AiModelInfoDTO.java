package com.springboot.marine_ai.dto;

import lombok.Data;

@Data
public class AiModelInfoDTO {
    private String modelName;
    private String version;
    private Integer classCount;
    private String updateTime;
}