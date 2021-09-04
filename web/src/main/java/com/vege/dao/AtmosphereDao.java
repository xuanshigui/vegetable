package com.vege.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AtmosphereDao extends BaseDao {

    private static String SQL_INSERT = "INSERT INTO atmosphere (relative_himidity, air_temp, atmos_pressure, wind_speed, wind_direction, soil_moisture, soil_temp,sun_radiation, rain_fall,  timestamp) VALUES(%s, %s, %s, %s, %s, %s,%s,%s,%s, '%s')";
    private static String SQL_DELETE = "DELETE FROM atmosphere WHERE id=%s";
    private static String SQL_UPDATE = "UPDATE atmosphere SET relative_himidity='%s', air_temp=%s, atmos_pressure=%s, wind_speed=%s, wind_direction=%s, soil_moisture=%s, soil_temp=%s,sun_radiation=%s,rain_fall=%s, timestamp='%s' WHERE id=%s";
    private static String SQL_QUERY = "SELECT * FROM atmosphere WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM atmosphere WHERE 1=1 %s";

    public AtmosphereDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean add(Map<String, String> data) {
        String sql = String.format(AtmosphereDao.SQL_INSERT, data.get("relative_himidity"), data.get("air_temp"), data.get("atmos_pressure"), data.get("wind_speed"), data.get("wind_direction"), data.get("soil_moisture"), data.get("soil_temp"), data.get("sun_radiation"), data.get("rain_fall"), data.get("timestamp"));

        return exec(sql);
    }

    public boolean delete(String id) {
        String sql = String.format(AtmosphereDao.SQL_DELETE, id);

        return exec(sql);
    }

    public boolean update(Map<String, String> data) {
        String sql = String.format(AtmosphereDao.SQL_UPDATE, data.get("relative_himidity"), data.get("air_temp"), data.get("atmos_pressure"), data.get("wind_speed"), data.get("wind_direction"), data.get("soil_moisture"), data.get("soil_temp"), data.get("sun_radiation"), data.get("rain_fall"), data.get("timestamp"), data.get("id"));

        return exec(sql);
    }

    public List<Map<String, Object>> query(Map<String, String> condition) {
        String where = buildWhere(condition);
        String limit = buildLimit(condition);
        String sql = String.format(AtmosphereDao.SQL_QUERY, where, limit);

        List<Map<String, Object>> result = new ArrayList<>();
        try {
            List rows = jdbcTemplate.queryForList(sql);
            for (Object row1 : rows) {
                Map row = (Map) row1;
                Map<String, Object> item = new HashMap<>();
                item.put("id", row.get("id"));
                item.put("relative_himidity", row.get("relative_himidity"));
                item.put("air_temp", row.get("air_temp"));
                item.put("atmos_pressure", row.get("atmos_pressure"));
                item.put("wind_speed", row.get("wind_speed"));
                item.put("wind_direction", row.get("wind_direction"));
                item.put("soil_moisture", row.get("soil_moisture"));
                item.put("soil_temp", row.get("soil_temp"));
                item.put("sun_radiation", row.get("sun_radiation"));
                item.put("rain_fall", row.get("rain_fall"));
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
        String sql = String.format(AtmosphereDao.SQL_QUERY_TOTAL, where);

        return getTotal(sql);
    }

}
