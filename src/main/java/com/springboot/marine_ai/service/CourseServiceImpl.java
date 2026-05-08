package com.springboot.marine_ai.service;

import com.springboot.marine_ai.common.ResultCode;
import com.springboot.marine_ai.entity.Course;
import com.springboot.marine_ai.mapper.CourseMapper;
import com.springboot.marine_ai.vo.CourseVO;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public List<CourseVO> getCourseList() {
        List<Course> courses = courseMapper.selectAll();
        return courses.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public CourseVO getCourseDetail(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("课程ID不能为空");
        }
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new IllegalArgumentException("课程不存在");
        }
        return convertToVO(course);
    }

    private CourseVO convertToVO(Course course) {
        CourseVO vo = new CourseVO();
        vo.setId(course.getId());
        vo.setCourseName(course.getCourseName());
        vo.setIntro(course.getIntro());
        vo.setCoverUrl(course.getCoverUrl());
        vo.setCourseType(course.getCourseType());
        vo.setCreateTime(course.getCreateTime() != null ? course.getCreateTime().format(DATE_FORMATTER) : null);
        return vo;
    }
}