package com.springboot.marine_ai.service.impl;

import com.springboot.marine_ai.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.path:D:/Marine_ai/upload}")
    private String uploadPath;

    @Override
    public String uploadFile(byte[] fileData, String originalFilename) {
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }
        String newFilename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) 
                             + "_" + UUID.randomUUID().toString().substring(0, 8) + extension;
        File targetFile = new File(uploadPath, newFilename);
        try {
            java.nio.file.Files.write(targetFile.toPath(), fileData);
            return "/upload/" + newFilename;
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败: " + e.getMessage());
        }
    }
}