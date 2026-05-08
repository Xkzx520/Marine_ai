package com.springboot.marine_ai.mapper;

import com.springboot.marine_ai.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> selectAll();
    Course selectById(Long id);
}