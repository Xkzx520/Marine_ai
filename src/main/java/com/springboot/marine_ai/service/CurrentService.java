package com.springboot.marine_ai.service;

import com.springboot.marine_ai.dto.CurrentPredictDTO;
import com.springboot.marine_ai.dto.CurrentPredictRequest;
import com.springboot.marine_ai.dto.CurrentRecordDTO;
import com.springboot.marine_ai.entity.CurrentKnowledge;
import com.springboot.marine_ai.entity.CurrentParams;
import com.springboot.marine_ai.entity.CurrentScene;
import com.springboot.marine_ai.vo.SceneSaveRequest;

import java.util.List;

public interface CurrentService {
    List<CurrentScene> getSceneList();
    CurrentScene getSceneById(Long sceneId);
    boolean saveScene(SceneSaveRequest request);
    CurrentParams getParams();
    boolean submitRecord(CurrentRecordDTO record);
    List<CurrentKnowledge> getKnowledge();

    CurrentPredictDTO predict(CurrentPredictRequest request);
}