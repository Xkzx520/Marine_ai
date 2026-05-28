package com.springboot.marine_ai.service.impl;

import com.springboot.marine_ai.dto.CurrentPredictDTO;
import com.springboot.marine_ai.dto.CurrentPredictRequest;
import com.springboot.marine_ai.dto.CurrentRecordDTO;
import com.springboot.marine_ai.entity.CurrentKnowledge;
import com.springboot.marine_ai.entity.CurrentParams;
import com.springboot.marine_ai.entity.CurrentScene;
import com.springboot.marine_ai.mapper.CurrentKnowledgeMapper;
import com.springboot.marine_ai.mapper.CurrentParamsMapper;
import com.springboot.marine_ai.mapper.CurrentSceneMapper;
import com.springboot.marine_ai.service.CurrentService;
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

    @Override
    public CurrentPredictDTO predict(CurrentPredictRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("预测参数不能为空");
        }
        int wind = clamp(request.getWindPower() != null ? request.getWindPower() : 5, 0, 10);
        int angle = normAngle(request.getWindAngle() != null ? request.getWindAngle() : 90);
        boolean coriolis = request.getCoriolisEnabled() == null || request.getCoriolisEnabled();
        int layout = clamp(request.getContinentLayout() != null ? request.getContinentLayout() : 1, 0, 3);
        float temp = request.getTempDiff() != null ? request.getTempDiff() : 0f;
        float salinity = request.getSalinity() != null ? request.getSalinity() : 35f;

        float manualAngle = request.getManualDriftAngle() != null
                ? request.getManualDriftAngle()
                : windToDriftAngle(angle, wind, coriolis, layout, 0f, salinity);

        float aiAngle = windToDriftAngle(angle, wind, coriolis, layout, temp, salinity);
        String manualDir = angleToDirection(manualAngle);
        String aiDir = angleToDirection(aiAngle);

        CurrentPredictDTO dto = new CurrentPredictDTO();
        dto.setManualTrend(String.format("未来 24h 洋流主体向%s偏移，风速因子 %.1f", manualDir, wind / 10.0));
        dto.setAiTrend(String.format("AI 预测 7 日趋势：主流向%s，受温度差 %.1f℃、盐度 %.1f‰ 与大陆分布修正",
                aiDir, temp, salinity));
        dto.setDominantDirection(aiDir);
        dto.setConfidence(0.62f + wind * 0.03f + (coriolis ? 0.05f : 0f));
        dto.setComparison(buildComparison(manualDir, aiDir, temp, layout));
        dto.setExplanation(
                "人工推演主要依据当前风场与地转偏向；AI 模型额外融合海温梯度与海陆分布，"
                        + "在复杂场景下可给出更长期的趋势估计。");
        return dto;
    }

    private static String buildComparison(String manualDir, String aiDir, float temp, int layout) {
        if (manualDir.equals(aiDir)) {
            return "本次人工推演与 AI 预测一致，说明在当前风场主导下趋势较稳定。";
        }
        String layoutHint = switch (layout) {
            case 0 -> "开阔大洋";
            case 1 -> "赤道太平洋型";
            case 2 -> "北大西洋型";
            default -> "多岛屿型";
        };
        return String.format(
                "差异原因：AI 考虑了%s布局与 %.1f℃ 温度差，使流向由%s修正为%s。",
                layoutHint, temp, manualDir, aiDir);
    }

    private static float windToDriftAngle(int windAngle, int wind, boolean coriolis, int layout, float temp, float salinity) {
        float salinityEffect = (salinity - 35f) * 1.5f;
        float drift = windAngle + (coriolis ? 25f : 0f) + temp * 4f + salinityEffect;
        drift += switch (layout) {
            case 1 -> 15f;
            case 2 -> -20f;
            case 3 -> 8f;
            default -> 0f;
        };
        drift += (wind - 5) * 3f;
        return normAngleFloat(drift);
    }

    private static String angleToDirection(float angle) {
        String[] dirs = {"东", "东北", "北", "西北", "西", "西南", "南", "东南"};
        int idx = Math.round(angle / 45f) % 8;
        return dirs[idx];
    }

    private static int normAngle(int a) {
        int v = a % 360;
        return v < 0 ? v + 360 : v;
    }

    private static float normAngleFloat(float a) {
        float v = a % 360f;
        return v < 0 ? v + 360f : v;
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }
}