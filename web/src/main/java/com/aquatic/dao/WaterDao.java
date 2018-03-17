package com.aquatic.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WaterDao extends BaseDao {

    private static String SQL_INSERT = "INSERT INTO water (imsi, waterTemp, dissolvedOxygen, workingVoltage, chargingVoltage, operatingTemp, timestamp) VALUES('%s', %s, %s, %s, %s, %s, '%s')";
    private static String SQL_DELETE = "DELETE FROM water WHERE id=%s";
    private static String SQL_UPDATE = "UPDATE water SET imsi='%s', waterTemp=%s, dissolvedOxygen=%s, workingVoltage=%s, chargingVoltage=%s, operatingTemp=%s, timestamp='%s' WHERE id=%s";
    private static String SQL_QUERY = "SELECT * FROM water WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM water WHERE 1=1 %s";

    public WaterDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean add(Map<String, String> data) {
        String sql = String.format(WaterDao.SQL_INSERT, data.get("imsi"), data.get("water_temp"), data.get("dissolved_oxygen"), data.get("working_voltage"), data.get("charging_voltage"), data.get("operating_temp"), data.get("timestamp"));

        return exec(sql);
    }

    public boolean delete(String id) {
        String sql = String.format(WaterDao.SQL_DELETE, id);

        return exec(sql);
    }

    public boolean update(Map<String, String> data) {
        String sql = String.format(WaterDao.SQL_UPDATE, data.get("imsi"), data.get("water_temp"), data.get("dissolved_oxygen"), data.get("working_voltage"), data.get("charging_voltage"), data.get("operating_temp"), data.get("timestamp"), data.get("id"));

        return exec(sql);
    }

    public List<Map<String, Object>> query(Map<String, String> condition) {
        String where = buildWhere(condition);
        String limit = buildLimit(condition);
        String sql = String.format(WaterDao.SQL_QUERY, where, limit);

        List<Map<String, Object>> result = new ArrayList<>();
        try {
            List rows = jdbcTemplate.queryForList(sql);
            for (Object row1 : rows) {
                Map row = (Map) row1;
                Map<String, Object> item = new HashMap<>();
                item.put("id", row.get("id"));
                item.put("imsi", row.get("imsi"));
                item.put("water_temp", row.get("waterTemp"));
                item.put("dissolved_oxygen", row.get("dissolvedOxygen"));
                item.put("working_voltage", row.get("workingVoltage"));
                item.put("charging_voltage", row.get("chargingVoltage"));
                item.put("operating_temp", row.get("operatingTemp"));
                item.put("timestamp", row.get("timestamp"));
                result.add(item);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int queryTotal(Map<String, String> condition) {
        String where = buildWhere(condition);
        String sql = String.format(WaterDao.SQL_QUERY_TOTAL, where);

        return getTotal(sql);
    }

}
