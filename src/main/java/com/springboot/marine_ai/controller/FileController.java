package com.springboot.marine_ai.controller;

import com.springboot.marine_ai.common.Result;
import com.springboot.marine_ai.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public Result<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return Result.paramError("请选择上传文件");
            }
            String url = fileService.uploadFile(file.getBytes(), file.getOriginalFilename());
            Map<String, String> result = new HashMap<>();
            result.put("url", url);
            return Result.success("上传成功", result);
        } catch (IOException e) {
            return Result.serverError("文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}