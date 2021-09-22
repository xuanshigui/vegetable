package com.vege.service.impl;

import com.vege.service.FileService;
import com.vege.utils.PathHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * created by zbs on 2018/3/11
 */
@Service
public class FileServiceImpl implements FileService {
    private static int READ_ROWS = 10;

    @Override
    public String uploadFile(MultipartFile file, String fileName) {
        String path = "";
        try {
            InputStream inputStream = file.getInputStream();
            String newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."),fileName.length());
            File newFile = new File(PathHelper.getUploadPath() + newFileName);
            OutputStream outputStream = new FileOutputStream(newFile);

            byte temp[] = new byte[1024];
            int size;
            while ((size = inputStream.read(temp)) != -1) {
                outputStream.write(temp, 0, size);
            }
            path = newFile.getAbsolutePath();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    public List<String> getFileData(String filePath) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            for (int i = 0; i < READ_ROWS; i++) {
                String tempString = reader.readLine();
                if (tempString == null) {
                    break;
                }
                fileContent.add(tempString);
            }

        } catch (Exception e) {
        }

        return fileContent;
    }
}