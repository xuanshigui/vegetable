package com.vege.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BaseDao {
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public BaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected static String buildWhere(Map<String, String> condition) {
        StringBuilder where = new StringBuilder();
        String name = condition.get("userName");
        if (name != null && !name.equals("")) {
            where.append(" AND userName like '%").append(name).append("%'");
        }

        return where.toString();
    }

    protected static String buildLimit(Map<String, String> condition) {
        StringBuilder limit = new StringBuilder();
        int page = 0;
        int size = 0;
        String pageStr = condition.get("page");
        if (pageStr != null && !pageStr.equals("")) {
            page = Integer.valueOf(pageStr);
        }

        String sizeStr = condition.get("size");
        if (sizeStr != null && !sizeStr.equals("")) {
            size = Integer.valueOf(sizeStr);
        }

        if (page != 0 && size != 0) {
            int offset = (page - 1) * size;
            limit.append("limit ").append(String.valueOf(offset)).append(",").append(String.valueOf(size));
        }

        return limit.toString();
    }


    protected boolean exec(String sql) {
        try {
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected int getTotal(String sql){
        int total = 0;
        try {
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
            while (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
        }
        return total;
    }


}
