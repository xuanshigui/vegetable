package com.vege.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String uploadFile(MultipartFile request, String fileName);

    List<String> getFileData(String filePath);
}
