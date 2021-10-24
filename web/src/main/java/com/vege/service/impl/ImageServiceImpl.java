package com.vege.service.impl;

import com.vege.constants.Constants;
import com.vege.dao.ImageRepository;
import com.vege.model.Image;
import com.vege.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class ImageServiceImpl extends BaseService implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public String add(String imgPath, String className) {
        if(imgPath==null||"".equals(imgPath)){
            return "12412918-82cc-47b3-af8d-3a9c5b4f1151";
        }
        Image image = new Image();
        image.setImgPath(imgPath);
        image.setImgName(className);
        image.setTimestamp(new Timestamp(System.currentTimeMillis()));
        String uuid = imgPath.substring(imgPath.lastIndexOf("/")+1,imgPath.lastIndexOf("."));
        image.setNote("无");
        image.setUuid(uuid);
        image.setTableName(Constants.ENTITY_TABLE_MAP.get(className));
        imageRepository.save(image);
        return uuid;
    }

    @Override
    public Image queryByUuid(String imgUuid){
        return imageRepository.findByUuid(imgUuid);
    }

    @Override
    public String queryPathByUuid(String imgUuid){
        Image image = imageRepository.findByUuid(imgUuid);
        return image.getImgPath();
    }

    @Override
    public boolean delete(String imgid) {
        try {
            Image image = imageRepository.findByImgId(Integer.parseInt(imgid));
            imageRepository.delete(image);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Image image) {

        try {
            imageRepository.save(image);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Image> query(Map<String, String> condition) {

        String imgNameStr = condition.get("imgName");
        String tableNameStr = condition.get("tableName");
        String startTimeStr = condition.get("startTime");
        String endTimeStr = condition.get("endTime");
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
        Date startTime = null;
        Date endTime = null;
        if(startTimeStr != null && !startTimeStr.equals("")){
            try {
                startTime = formatter.parse(startTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(endTimeStr != null && !endTimeStr.equals("")){
            try {
                endTime = formatter.parse(endTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Pageable pageable = getPageable(condition);
        if(imgNameStr != null && !imgNameStr.equals("")){
            imgNameStr = "%" + imgNameStr + "%";
            if(startTime != null && !startTime.equals("")){
                if(endTime != null && !endTime.equals("")){
                    return imageRepository.findByTimestampBetweenAndImgNameLike(startTime,endTime,imgNameStr,pageable);
                }
                return imageRepository.findByTimestampAfterAndImgNameLike(startTime,imgNameStr,pageable);
            }
            if(endTime != null && !endTime.equals("")){
                return imageRepository.findByTimestampBeforeAndImgNameLike(endTime,imgNameStr,pageable);
            }
            return imageRepository.findByImgNameLike(imgNameStr,getPageable(condition));
        }
        if(startTime != null && !startTime.equals("")){
            if(endTime != null && !endTime.equals("")){
                return imageRepository.findByTimestampBetween(startTime,endTime,pageable);
            }
            return imageRepository.findByTimestampAfter(startTime,pageable);
        }
        if(endTime != null && !endTime.equals("")){
            return imageRepository.findByTimestampBefore(endTime,pageable);
        }
        return imageRepository.findAll(getPageable(condition));
    }

    @Override
    public Image queryById(String imgid){

        return imageRepository.findByImgId(Integer.parseInt(imgid));
    }

    @Override
    public boolean clearUnadministableIamges(){

        return true;
    }
}
