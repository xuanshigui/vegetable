package com.aquatic.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    boolean uploadFile(MultipartFile request, String fileName);
}
