package com.springboot.marine_ai.mapper;

import com.springboot.marine_ai.entity.CurrentParams;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CurrentParamsMapper {
    CurrentParams selectOne();
}