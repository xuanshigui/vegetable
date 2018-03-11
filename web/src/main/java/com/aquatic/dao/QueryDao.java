package com.aquatic.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QueryDao {
    private final JdbcTemplate jdbcTemplate;

    private static String SQL_INSERT = "INSERT INTO %s (name, lprice, mprice, hprice, classify, unit, date) VALUES('%s', %s, %s, %s, '%s', '%s', '%s')";
    private static String SQL_DELETE = "DELETE FROM %s WHERE id=%s";
    private static String SQL_UPDATE = "UPDATE %s SET name='%s', lprice=%s, mprice=%s, hprice=%s, classify='%s', unit='%s', date='%s' WHERE id=%s";
    private static String SQL_QUERY = "SELECT * FROM %s WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM %s WHERE 1=1 %s";

    @Autowired
    public QueryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean add(String type, Map<String, String> data) {
        String tableName = getTable(type);
        String sql = String.format(QueryDao.SQL_INSERT, tableName, data.get("name"), data.get("lprice"), data.get("mprice"), data.get("hprice"), data.get("classify"), data.get("unit"), data.get("date"));


        try {
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String type, String id) {
        String tableName = getTable(type);
        String sql = String.format(QueryDao.SQL_DELETE, tableName, id);

        try {
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String type, Map<String, String> data) {
        String tableName = getTable(type);
        String sql = String.format(QueryDao.SQL_UPDATE, tableName, data.get("name"), data.get("lprice"), data.get("mprice"), data.get("hprice"), data.get("classify"), data.get("unit"), data.get("date"), data.get("id"));

        try {
            jdbcTemplate.execute(sql);
            System.out.println(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> query(String type, Map<String, String> condition) {
        String tableName = getTable(type);
        String where = buildWhere(condition);
        String limit = buildLimit(condition);
        String sql = String.format(QueryDao.SQL_QUERY, tableName, where, limit);

        List<Map<String, Object>> result = new ArrayList<>();
        try {
            List rows = jdbcTemplate.queryForList(sql);
            Iterator iterator = rows.iterator();
            while (iterator.hasNext()) {
                Map row = (Map) iterator.next();
                Map<String, Object> item = new HashMap<>();
                item.put("id", row.get("id"));
                item.put("name", row.get("name"));
                item.put("lprice", row.get("lprice"));
                item.put("mprice", row.get("mprice"));
                item.put("hprice", row.get("hprice"));
                item.put("classify", row.get("classify"));
                item.put("unit", row.get("unit"));
                item.put("date", row.get("date"));
                result.add(item);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int queryTotal(String type, Map<String, String> condition) {
        String tableName = getTable(type);
        String where = buildWhere(condition);
        String sql = String.format(QueryDao.SQL_QUERY_TOTAL, tableName, where);

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

    private static String buildWhere(Map<String, String> condition) {
        StringBuilder where = new StringBuilder();
        String name = condition.get("name");
        if (name != null && !name.equals("")) {
            where.append(" AND name = '").append(name).append("'");
        }

        String startDate = condition.get("start_date");
        if (startDate != null && !startDate.equals("")) {
            where.append(" AND date >= '").append(startDate).append("'");
        }

        String endDate = condition.get("end_date");
        if (endDate != null && !endDate.equals("")) {
            where.append(" AND date <= '").append(endDate).append("'");
        }

        return where.toString();
    }

    private static String buildLimit(Map<String, String> condition) {
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

    private static String getTable(String type) {
//        for (QueryTable table : QueryTable.values()) {
//            if (table.getType().equals(type)) {
//                return table.getTable();
//            }
//        }

        return "price_xuqin_b";
        //return QueryTable.SC.getTable();
    }
}
