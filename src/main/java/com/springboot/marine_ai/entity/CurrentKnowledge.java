package com.springboot.marine_ai.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CurrentKnowledge {
    private Long id;
    private String title;
    private String content;
    private Integer sort;
    private LocalDateTime createTime;
}