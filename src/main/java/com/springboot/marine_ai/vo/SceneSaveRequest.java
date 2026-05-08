package com.springboot.marine_ai.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SceneSaveRequest {
    private Long userId;
    private String sceneName;
    private Integer windPower;
    private Boolean coriolisEnabled;
    private Integer continentLayout;
    private Integer particleCount;
    private BigDecimal speedCoefficient;
    private String description;
}