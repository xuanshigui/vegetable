package com.vege.dao;

import com.vege.model.Image;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImgRowMapper implements RowMapper{

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Image img = new Image();
        img.setImgId(rs.getInt("imgid"));
        img.setImgPath(rs.getString("imgpath"));
        img.setNote(rs.getString("note"));
        img.setTimestamp(rs.getTimestamp("timestamp"));
        img.setImgName(rs.getString("imgname"));
        img.setTableName(rs.getString("tablename"));
        img.setUuid(rs.getString("imguuid"));
        return img;
    }
}
