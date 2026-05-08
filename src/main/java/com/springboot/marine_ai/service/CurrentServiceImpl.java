package com.springboot.marine_ai.service;

import com.springboot.marine_ai.dto.CurrentRecordDTO;
import com.springboot.marine_ai.entity.CurrentKnowledge;
import com.springboot.marine_ai.entity.CurrentParams;
import com.springboot.marine_ai.entity.CurrentScene;
import com.springboot.marine_ai.mapper.CurrentKnowledgeMapper;
import com.springboot.marine_ai.mapper.CurrentParamsMapper;
import com.springboot.marine_ai.mapper.CurrentSceneMapper;
import com.springboot.marine_ai.vo.SceneSaveRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentServiceImpl implements CurrentService {

    private final CurrentSceneMapper sceneMapper;
    private final CurrentParamsMapper paramsMapper;
    private final CurrentKnowledgeMapper knowledgeMapper;

    public CurrentServiceImpl(CurrentSceneMapper sceneMapper, 
                              CurrentParamsMapper paramsMapper,
                              CurrentKnowledgeMapper knowledgeMapper) {
        this.sceneMapper = sceneMapper;
        this.paramsMapper = paramsMapper;
        this.knowledgeMapper = knowledgeMapper;
    }

    @Override
    public List<CurrentScene> getSceneList() {
        return sceneMapper.selectAll();
    }

    @Override
    public CurrentScene getSceneById(Long sceneId) {
        if (sceneId == null) {
            throw new IllegalArgumentException("场景ID不能为空");
        }
        CurrentScene scene = sceneMapper.selectById(sceneId);
        if (scene == null) {
            throw new IllegalArgumentException("场景不存在");
        }
        return scene;
    }

    @Override
    public boolean saveScene(SceneSaveRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("请求参数不能为空");
        }
        if (request.getSceneName() == null || request.getSceneName().trim().isEmpty()) {
            throw new IllegalArgumentException("场景名称不能为空");
        }
        CurrentScene scene = new CurrentScene();
        scene.setSceneName(request.getSceneName());
        scene.setWindPower(request.getWindPower() != null ? request.getWindPower() : 5);
        scene.setCoriolisEnabled(request.getCoriolisEnabled() != null ? request.getCoriolisEnabled() : true);
        scene.setContinentLayout(request.getContinentLayout() != null ? request.getContinentLayout() : 0);
        scene.setParticleCount(request.getParticleCount() != null ? request.getParticleCount() : 300);
        scene.setSpeedCoefficient(request.getSpeedCoefficient() != null ? request.getSpeedCoefficient() : new java.math.BigDecimal("1.00"));
        scene.setDescription(request.getDescription());
        return sceneMapper.insert(scene) > 0;
    }

    @Override
    public CurrentParams getParams() {
        return paramsMapper.selectOne();
    }

    @Override
    public boolean submitRecord(CurrentRecordDTO record) {
        if (record == null) {
            throw new IllegalArgumentException("记录不能为空");
        }
        if (record.getUserId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        System.out.println("模拟记录: userId=" + record.getUserId() + ", sceneId=" + record.getSceneId() + 
                          ", useTime=" + record.getUseTimeSec() + "s, windChanged=" + record.getWindChangedCount());
        return true;
    }

    @Override
    public List<CurrentKnowledge> getKnowledge() {
        return knowledgeMapper.selectAll();
    }
}