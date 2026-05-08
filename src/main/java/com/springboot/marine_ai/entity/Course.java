package com.springboot.marine_ai.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Course {
    private Long id;
    private String courseName;
    private String intro;
    private String coverUrl;
    private Integer courseType;
    private LocalDateTime createTime;
}