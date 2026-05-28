package com.springboot.marine_ai.service;

import com.springboot.marine_ai.dto.AiModelInfoDTO;
import com.springboot.marine_ai.dto.BiologyDetectionDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AiService {
    AiModelInfoDTO getModelInfo();

    BiologyDetectionDTO detectBiology(MultipartFile file);

    boolean isReady();
}