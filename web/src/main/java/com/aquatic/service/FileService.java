package com.aquatic.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    boolean uploadFile(MultipartFile request, String fileName);

    List<String> getFileData(String filePath);
}
