package com.aquatic.dao;

import com.aquatic.constants.QueryTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QueryDao {
    private final JdbcTemplate jdbcTemplate;

    private static String SQL_INSERT = "INSERT INTO %s (name, lprice, mprice, hprice, classify, unit, date) VALUES('%s', %s, %s, %s, '%s', '%s', '%s')";
    private static String SQL_DELETE = "DELETE FROM %s WHERE id=%s";
    private static String SQL_UPDATE = "UPDATE %s SET name='%s', lprice=%s, mprice=%s, hprice=%s, classify='%s', unit='%s', date='%s' WHERE id=%s";
    private static String SQL_QUERY = "SELECT * FROM %s WHERE 1=1 %s";

    @Autowired
    public QueryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean add(String type, Map<String, String> data) {
        String tableName = getTable(type);
        String sql = String.format(QueryDao.SQL_INSERT, tableName, data.get("name"), data.get("lprice"), data.get("mprice"), data.get("hprice"), data.get("classify"), data.get("unit"), data.get("date"));


        try (PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql)) {
            return ps.execute();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(String type, String id) {
        String tableName = getTable(type);
        String sql = String.format(QueryDao.SQL_DELETE, tableName, id);

        try (PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql)) {
            return ps.execute();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(String type, Map<String, String> data) {
        String tableName = getTable(type);
        String sql = String.format(QueryDao.SQL_UPDATE, tableName, data.get("name"), data.get("lprice"), data.get("mprice"), data.get("hprice"), data.get("classify"), data.get("unit"), data.get("date"), data.get("id"));

        try (PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql)) {
            return ps.execute();
        } catch (Exception e) {
            return false;
        }
    }

    public List<Map<String, Object>> query(String type, Map<String, String> condition) {
        String tableName = getTable(type);
        String where = buildWhere(condition);
        String sql = String.format(QueryDao.SQL_QUERY, tableName, where);

        List<Map<String, Object>> result = new ArrayList<>();
        try (PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("name", rs.getString("name"));
                item.put("lprice", rs.getInt("lprice"));
                item.put("mprice", rs.getInt("mprice"));
                item.put("hprice", rs.getInt("hprice"));
                item.put("classify", rs.getString("classify"));
                item.put("unit", rs.getString("unit"));
                item.put("date", rs.getString("date"));
                result.add(item);
            }

        } catch (Exception e) {
        }
        return result;
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

    private static String getTable(String type) {
        for (QueryTable table : QueryTable.values()) {
            if (table.getType().equals(type)) {
                return table.getTable();
            }
        }

        return "price_xuqin_b";
        //return QueryTable.SC.getTable();
    }
}
