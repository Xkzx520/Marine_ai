package com.springboot.marine_ai.controller;

import com.springboot.marine_ai.common.Result;
import com.springboot.marine_ai.dto.CurrentPredictDTO;
import com.springboot.marine_ai.dto.CurrentPredictRequest;
import com.springboot.marine_ai.dto.CurrentRecordDTO;
import com.springboot.marine_ai.entity.CurrentKnowledge;
import com.springboot.marine_ai.entity.CurrentParams;
import com.springboot.marine_ai.entity.CurrentScene;
import com.springboot.marine_ai.service.CurrentService;
import com.springboot.marine_ai.vo.SceneSaveRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/current")
public class CurrentController {

    private final CurrentService currentService;

    public CurrentController(CurrentService currentService) {
        this.currentService = currentService;
    }

    @GetMapping("/scene/list")
    public Result<List<CurrentScene>> getSceneList() {
        try {
            List<CurrentScene> list = currentService.getSceneList();
            return Result.success(list);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @GetMapping("/scene/{sceneId}")
    public Result<CurrentScene> getSceneById(@PathVariable Long sceneId) {
        try {
            CurrentScene scene = currentService.getSceneById(sceneId);
            return Result.success(scene);
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/scene/save")
    public Result<Void> saveScene(@RequestBody SceneSaveRequest request) {
        try {
            boolean success = currentService.saveScene(request);
            if (success) {
                return Result.success("保存成功", null);
            }
            return Result.error(500, "保存失败");
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @GetMapping("/params")
    public Result<CurrentParams> getParams() {
        try {
            CurrentParams params = currentService.getParams();
            return Result.success(params);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/record/submit")
    public Result<Void> submitRecord(@RequestBody CurrentRecordDTO record) {
        try {
            boolean success = currentService.submitRecord(record);
            if (success) {
                return Result.success("提交成功", null);
            }
            return Result.error(500, "提交失败");
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/predict")
    public Result<CurrentPredictDTO> predict(@RequestBody CurrentPredictRequest request) {
        try {
            CurrentPredictDTO dto = currentService.predict(request);
            return Result.success("预测完成", dto);
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @GetMapping("/knowledge")
    public Result<List<CurrentKnowledge>> getKnowledge() {
        try {
            List<CurrentKnowledge> list = currentService.getKnowledge();
            return Result.success(list);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}