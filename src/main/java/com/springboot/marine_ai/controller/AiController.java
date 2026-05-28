package com.springboot.marine_ai.controller;

import com.springboot.marine_ai.common.Result;
import com.springboot.marine_ai.dto.AiModelInfoDTO;
import com.springboot.marine_ai.dto.BiologyDetectionDTO;
import com.springboot.marine_ai.service.AiService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/ai", produces = "application/json;charset=UTF-8")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/health")
    public Result<String> health() {
        if (aiService.isReady()) {
            return Result.success("YOLO 服务就绪");
        }
        return Result.serverError("YOLO 脚本未找到，请检查 yolo/detect.py");
    }

    @GetMapping("/info")
    public Result<AiModelInfoDTO> getModelInfo() {
        try {
            AiModelInfoDTO info = aiService.getModelInfo();
            return Result.success(info);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/detect")
    public Result<BiologyDetectionDTO> detect(@RequestParam("file") MultipartFile file) {
        try {
            BiologyDetectionDTO detection = aiService.detectBiology(file);
            return Result.success("识别成功", detection);
        } catch (IllegalArgumentException e) {
            return Result.paramError(e.getMessage());
        } catch (IllegalStateException e) {
            return Result.serverError(e.getMessage());
        } catch (Exception e) {
            return Result.serverError("识别失败: " + e.getMessage());
        }
    }
}