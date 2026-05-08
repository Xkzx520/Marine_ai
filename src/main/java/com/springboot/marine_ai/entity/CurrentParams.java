package com.springboot.marine_ai.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CurrentParams {
    private Long id;
    private BigDecimal viscosity;
    private BigDecimal diffusion;
    private BigDecimal coriolisIntensity;
    private Integer maxWindPower;
    private Integer particleMaxCount;
    private LocalDateTime updateTime;
}