package com.springboot.marine_ai.dto;

import lombok.Data;

@Data
public class CurrentRecordDTO {
    private Long userId;
    private Long sceneId;
    private Integer useTimeSec;
    private Integer windChangedCount;
    private Integer continentPlacedCount;
}