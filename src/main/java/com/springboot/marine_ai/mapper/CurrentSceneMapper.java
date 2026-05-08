package com.springboot.marine_ai.mapper;

import com.springboot.marine_ai.entity.CurrentScene;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CurrentSceneMapper {
    List<CurrentScene> selectAll();
    CurrentScene selectById(Long sceneId);
    int insert(CurrentScene scene);
}