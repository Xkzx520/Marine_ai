package com.springboot.marine_ai.service.impl;

import com.springboot.marine_ai.dto.AiModelInfoDTO;
import com.springboot.marine_ai.dto.BiologyDetectionDTO;
import com.springboot.marine_ai.service.AiService;
import com.springboot.marine_ai.service.YoloDetectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Service
public class AiServiceImpl implements AiService {

    private final YoloDetectionService yoloDetectionService;

    @Value("${ai.yolo.weights:./yolo/weights/marine_yolo.pt}")
    private String weightsPath;

    @Value("${ai.yolo.classes:./yolo/data/marine_classes.txt}")
    private String classesPath;

    public AiServiceImpl(YoloDetectionService yoloDetectionService) {
        this.yoloDetectionService = yoloDetectionService;
    }

    @Override
    public AiModelInfoDTO getModelInfo() {
        AiModelInfoDTO info = new AiModelInfoDTO();
        File weights = resolveFile(weightsPath);
        info.setModelName(weights.exists() ? weights.getName() : "yolov8n.pt (预训练兜底)");
        info.setVersion("YOLOv8");
        info.setClassCount(countClasses());
        info.setUpdateTime(LocalDate.now().toString());
        return info;
    }

    @Override
    public BiologyDetectionDTO detectBiology(MultipartFile file) {
        return yoloDetectionService.detect(file);
    }

    @Override
    public boolean isReady() {
        File script = resolveFile("./yolo/detect.py");
        return script.exists();
    }

    private int countClasses() {
        try {
            File classes = resolveFile(classesPath);
            if (!classes.exists()) {
                return 0;
            }
            List<String> lines = Files.readAllLines(classes.toPath());
            return (int) lines.stream().map(String::trim).filter(s -> !s.isEmpty()).count();
        } catch (Exception e) {
            return 0;
        }
    }

    private File resolveFile(String path) {
        File file = new File(path);
        if (!file.isAbsolute()) {
            file = new File(System.getProperty("user.dir"), path);
        }
        return file;
    }
}