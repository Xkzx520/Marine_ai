package com.springboot.marine_ai.service.impl;

import com.springboot.marine_ai.dto.AiModelInfoDTO;
import com.springboot.marine_ai.service.AiService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AiServiceImpl implements AiService {

    @Override
    public AiModelInfoDTO getModelInfo() {
        AiModelInfoDTO info = new AiModelInfoDTO();
        info.setModelName("marine_bio_v2.tflite");
        info.setVersion("2.0");
        info.setClassCount(20);
        info.setUpdateTime(LocalDate.now().toString());
        return info;
    }
}