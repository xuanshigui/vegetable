package com.vege.service;

import com.vege.model.Image;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ImageService {

    String add(String imgPath, String className);

    boolean delete(String imgId);

    boolean update(Image data);

    Image queryByUuid(String imgUuid);

    String queryPathByUuid(String imgUuid);

    Page<Image> query(Map<String, String> condition);

    Image queryById(String imgId);

    boolean clearUnadministableIamges();
}
