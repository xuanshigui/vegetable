package com.vege.dao;

import com.vege.model.Image;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ImageDaoImpl extends BaseDao implements ImageDao {

    private static String SQL_INSERT = "INSERT INTO tb_img (imgname, imguuid, timestamp, imgpath, note, tablename) VALUES('%s', '%s','%s', '%s','%s','%s')";
    private static String SQL_DELETE = "DELETE FROM tb_img WHERE userid=%s";
    private static String SQL_UPDATE = "UPDATE tb_img SET imgname='%s', imgpath='%s', timestamp='%s', note='%s' WHERE imgid=%s";
    private static String SQL_QUERY = "SELECT * FROM tb_img WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM tb_img WHERE 1=1 %s";
    private static String SQL_QUERY_BYID = "SELECT imgid as imgId, imguuid as uuid, imgname as imgName, timestamp, imgpath as imgPath, note, tablename as tableName FROM tb_img WHERE imgid = %s";
    private static String SQL_QUERY_BYUUID = "SELECT imgid as imgId, imguuid as uuid, imgname as imgName, timestamp, imgpath as imgPath, note, tablename as tableName FROM tb_img WHERE imguuid = '%s'";

    public ImageDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean add(Image data) {
        String sql = String.format(ImageDaoImpl.SQL_INSERT,data.getImgName(), data.getUuid(), data.getTimestamp(), data.getImgPath(), data.getNote(),data.getTableName());

        return exec(sql);
    }

    public boolean delete(String imgId) {
        String sql = String.format(ImageDaoImpl.SQL_DELETE, imgId);

        return exec(sql);
    }

    public boolean update(Image data) {
        String sql = String.format(ImageDaoImpl.SQL_UPDATE, data.getImgName(), data.getImgPath(),data.getTimestamp(), data.getNote(), data.getImgId());

        return exec(sql);
    }

    public Image queryById(String imgId){

        String sql = String.format(ImageDaoImpl.SQL_QUERY_BYID, imgId);
        RowMapper<Image> rowMapper = new BeanPropertyRowMapper<Image>(Image.class);
        Image image = jdbcTemplate.queryForObject(sql,rowMapper);
        return image;
    }

    public Image queryByUuid(String imgUuid){

        String sql = String.format(ImageDaoImpl.SQL_QUERY_BYUUID, imgUuid);
        RowMapper<Image> rowMapper = new ImgRowMapper();
        Image image = jdbcTemplate.queryForObject(sql,rowMapper);
        return image;
    }

    public List<Image> query(Map<String, String> condition) {

        StringBuilder where = new StringBuilder();
        String imgName = condition.get("imgName");
        if(imgName != null && !imgName.equals("")){
            where.append(" AND imgname like '%").append(imgName).append("%'");
        }
        String tableName = condition.get("tableName");
        if(tableName != null && !tableName.equals("")){
            where.append(" AND tablename = '").append(imgName).append("'");
        }
        String startDate = condition.get("startTime");
        if (startDate != null && !startDate.equals("")) {
            where.append(" AND timestamp >= '").append(startDate).append("'");
        }

        String endDate = condition.get("endTime");
        if (endDate != null && !endDate.equals("")) {
            where.append(" AND timestamp <= '").append(endDate).append("'");
        }

        String limit = buildLimit(condition);
        String sql = String.format(ImageDaoImpl.SQL_QUERY, where, limit);

        List<Image> result = new ArrayList<>();
        try {
            List rows = jdbcTemplate.queryForList(sql);
            for (Object row1 : rows) {
                Map row = (Map) row1;
                Image image = new Image();
                image.setImgId(Integer.parseInt(String.valueOf(row.get("imgid"))));
                image.setImgName(String.valueOf(row.get("imgname")));
                image.setUuid(String.valueOf(row.get("imguuid")));
                Long timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(row.get("timestamp"))).getTime();
                image.setTimestamp(new Timestamp(timestamp));
                image.setImgPath(String.valueOf(row.get("imgpath")));
                image.setTableName(String.valueOf(row.get("tablename")));
                image.setNote(String.valueOf(row.get("note")));
                result.add(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int queryTotal(Map<String, String> condition) {
        String where = buildWhere(condition);
        String sql = String.format(ImageDaoImpl.SQL_QUERY_TOTAL, where);

        return getTotal(sql);
    }

}
