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
    private static String SQL_UPDATE = "UPDATE tb_img SET username='%s', password='%s', realname='%s', telephone='%s', email='%s', note='%s' WHERE userid=%s";
    private static String SQL_QUERY = "SELECT * FROM tb_img WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM tb_img WHERE 1=1 %s";
    private static String SQL_QUERY_BYID = "SELECT imgid as imgId, imgUuid as imguuid, imgname as imgName, timestamp, imgpath as imgPath, note, tablename as tableName FROM tb_img WHERE imgid = %s";
    private static String SQL_QUERY_BYUUID = "SELECT imgid as imgId, imgUuid as imguuid, imgname as imgName, timestamp, imgpath as imgPath, note, tablename as tableName FROM tb_img WHERE imguuid = '%s'";

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
        String sql = String.format(ImageDaoImpl.SQL_UPDATE, data.getUuid(), data.getImgName(), data.getTimestamp(), data.getImgPath(), data.getNote(), data.getImgId());

        return exec(sql);
    }

    public Image queryById(String imgid){

        String sql = String.format(ImageDaoImpl.SQL_QUERY_BYID, imgid);
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
        String where = buildWhere(condition);
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
                image.setUuid(String.valueOf(row.get("uuid")));
                Long timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(row.get("timestamp"))).getTime();
                image.setTimestamp(new Timestamp(timestamp));
                image.setImgPath(String.valueOf(row.get("imgpath")));
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
