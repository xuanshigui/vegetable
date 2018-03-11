package com.aquatic.service.impl;

import com.aquatic.service.FileService;
import com.aquatic.utils.PathHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * created by zbs on 2018/3/11
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public boolean uploadFile(MultipartFile file, String fileName) {
        try {
            InputStream inputStream = file.getInputStream();
            File newFile = new File(PathHelper.getExamplePath() + fileName);
            OutputStream outputStream = new FileOutputStream(newFile);

            byte temp[] = new byte[1024];
            int size;
            while ((size = inputStream.read(temp)) != -1) {
                outputStream.write(temp, 0, size);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
