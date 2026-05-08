package com.springboot.marine_ai.controller;

import com.springboot.marine_ai.common.Result;
import com.springboot.marine_ai.service.CourseService;
import com.springboot.marine_ai.vo.CourseVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/list")
    public Result<List<CourseVO>> getCourseList() {
        try {
            List<CourseVO> list = courseService.getCourseList();
            return Result.success(list);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    public Result<CourseVO> getCourseDetail(@PathVariable Long id) {
        try {
            CourseVO detail = courseService.getCourseDetail(id);
            return Result.success(detail);
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}