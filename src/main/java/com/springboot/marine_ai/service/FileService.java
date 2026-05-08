package com.springboot.marine_ai.service;

public interface FileService {
    String uploadFile(byte[] fileData, String originalFilename);
}