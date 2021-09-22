package com.vege.service;

import com.vege.model.Image;

import java.util.List;
import java.util.Map;

public interface ImageService {

    public String add(String imgPath, String className);

    boolean delete(String imgId);

    boolean update(Image data);

    public Image queryByUuid(String imgUuid);

    public String queryPathByUuid(String imgUuid);

    List<Image> query(Map<String, String> condition);

    int queryTotal(Map<String, String> condition);

    Image queryById(String imgId);
}
