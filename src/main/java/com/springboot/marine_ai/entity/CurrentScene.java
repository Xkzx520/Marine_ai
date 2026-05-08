package com.springboot.marine_ai.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CurrentScene {
    private Long sceneId;
    private String sceneName;
    private Integer windPower;
    private Boolean coriolisEnabled;
    private Integer continentLayout;
    private Integer particleCount;
    private BigDecimal speedCoefficient;
    private String description;
    private LocalDateTime createTime;
}