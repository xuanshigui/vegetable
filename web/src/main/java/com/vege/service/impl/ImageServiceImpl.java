package com.vege.service.impl;

import com.vege.constants.Constants;
import com.vege.dao.ImageDao;
import com.vege.model.Image;
import com.vege.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public String add(String imgPath, String className) {
        if(imgPath==null||"".equals(imgPath)){
            return "12412918-82cc-47b3-af8d-3a9c5b4f1151";
        }
        Image image = new Image();
        image.setImgPath(imgPath);
        image.setImgName(className);
        image.setTimestamp(new Timestamp(System.currentTimeMillis()));
        String uuid = UUID.randomUUID().toString();
        image.setNote("无");
        image.setUuid(uuid);
        image.setTableName(Constants.ENTITY_TABLE_MAP.get(className));
        imageDao.add(image);
        return uuid;
    }

    @Override
    public Image queryByUuid(String imgUuid){
        return imageDao.queryByUuid(imgUuid);
    }

    @Override
    public String queryPathByUuid(String imgUuid){
        return imageDao.queryByUuid(imgUuid).getImgPath();
    }

    @Override
    public boolean delete(String imgid) {
        return imageDao.delete(imgid);
    }

    @Override
    public boolean update(Image image) {
        return imageDao.update(image);
    }

    @Override
    public List<Image> query(Map<String, String> condition) {
        return imageDao.query(condition);
    }

    @Override
    public int queryTotal(Map<String, String> condition) {
        return imageDao.queryTotal(condition);
    }

    public Image queryById(String imgid){
        return imageDao.queryById(imgid);
    }
}
