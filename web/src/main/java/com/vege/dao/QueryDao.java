package com.vege.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QueryDao extends BaseDao {

    private static String SQL_INSERT = "INSERT INTO %s (name, lprice, mprice, hprice, classify, unit, date) VALUES('%s', %s, %s, %s, '%s', '%s', '%s')";
    private static String SQL_DELETE = "DELETE FROM %s WHERE id=%s";
    private static String SQL_UPDATE = "UPDATE %s SET name='%s', lprice=%s, mprice=%s, hprice=%s, classify='%s', unit='%s', date='%s' WHERE id=%s";
    private static String SQL_QUERY = "SELECT * FROM %s WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM %s WHERE 1=1 %s";

    public QueryDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean add(String type, Map<String, String> data) {
        String tableName = getTable(type);
        String sql = String.format(QueryDao.SQL_INSERT, tableName, data.get("name"), data.get("lprice"), data.get("mprice"), data.get("hprice"), data.get("classify"), data.get("unit"), data.get("date"));


        return exec(sql);
    }

    public boolean delete(String type, String id) {
        String tableName = getTable(type);
        String sql = String.format(QueryDao.SQL_DELETE, tableName, id);

        return exec(sql);
    }

    public boolean update(String type, Map<String, String> data) {
        String tableName = getTable(type);
        String sql = String.format(QueryDao.SQL_UPDATE, tableName, data.get("name"), data.get("lprice"), data.get("mprice"), data.get("hprice"), data.get("classify"), data.get("unit"), data.get("date"), data.get("id"));

        return exec(sql);
    }

    public List<Map<String, Object>> query(String type, Map<String, String> condition) {
        String tableName = getTable(type);
        String where = buildWhere(condition);
        String limit = buildLimit(condition);
        String sql = String.format(QueryDao.SQL_QUERY, tableName, where, limit);

        List<Map<String, Object>> result = new ArrayList<>();
        try {
            List rows = jdbcTemplate.queryForList(sql);
            for (Object row1 : rows) {
                Map row = (Map) row1;
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

        return getTotal(sql);
    }
}
