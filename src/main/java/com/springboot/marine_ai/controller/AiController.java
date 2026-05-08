package com.springboot.marine_ai.controller;

import com.springboot.marine_ai.common.Result;
import com.springboot.marine_ai.dto.AiModelInfoDTO;
import com.springboot.marine_ai.service.AiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
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
}