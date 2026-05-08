package com.springboot.marine_ai.mapper;

import com.springboot.marine_ai.entity.CurrentKnowledge;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CurrentKnowledgeMapper {
    List<CurrentKnowledge> selectAll();
}