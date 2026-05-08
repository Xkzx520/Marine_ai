package com.springboot.marine_ai.vo;

import lombok.Data;

@Data
public class CourseVO {
    private Long id;
    private String courseName;
    private String intro;
    private String coverUrl;
    private Integer courseType;
    private String createTime;
}