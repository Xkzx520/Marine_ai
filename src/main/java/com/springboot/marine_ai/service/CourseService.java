package com.springboot.marine_ai.service;

import com.springboot.marine_ai.entity.Course;
import com.springboot.marine_ai.vo.CourseVO;
import java.util.List;

public interface CourseService {
    List<CourseVO> getCourseList();
    CourseVO getCourseDetail(Long id);
}