package com.springboot.marine_ai.mapper;

import com.springboot.marine_ai.entity.Biology;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BiologyMapper {
    List<Biology> selectAll();
    Biology selectByName(String bioName);
}