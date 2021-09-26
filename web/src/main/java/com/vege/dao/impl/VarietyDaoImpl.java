package com.vege.dao.impl;

import com.vege.constants.Constants;
import com.vege.dao.VarietyDao;
import com.vege.model.Variety;
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
public class VarietyDaoImpl extends BaseDao implements VarietyDao {

    private static String SQL_INSERT = "INSERT INTO tb_variety (vegeid, varietyname, description, area, imguuid, source,timestamp) VALUES(%s, '%s','%s', '%s','%s', %s, '%s')";
    private static String SQL_DELETE = "DELETE FROM tb_variety WHERE varietyid=%s";
    private static String SQL_UPDATE = "UPDATE tb_variety SET varietyname='%s', vegeId=%s, description='%s', area='%s', imguuid='%s', source='%s', timestamp='%s' WHERE varietyid=%s";
    private static String SQL_QUERY = "SELECT * FROM tb_variety WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM tb_variety WHERE 1=1 %s";
    private static String SQL_QUERY_BYID = "SELECT varietyid as varietyId, varietyname as varietyName, vegeid as vegeId, description, area, imguuid, source, timestamp FROM tb_variety WHERE varietyid = %s";

    public VarietyDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean add(Variety data) {
        String sql = String.format(VarietyDaoImpl.SQL_INSERT, data.getVegeId(), data.getVarietyName(), data.getDescription(),data.getArea(),data.getImgUuid(),Integer.parseInt(data.getSource()),data.getTimestamp());

        return exec(sql);
    }

    public boolean delete(String varietyid) {
        String sql = String.format(VarietyDaoImpl.SQL_DELETE, varietyid);

        return exec(sql);
    }

    public boolean update(Variety data) {
        String sql = String.format(VarietyDaoImpl.SQL_UPDATE,data.getVegeId(), data.getVarietyName(), data.getDescription(),data.getArea(),data.getImgUuid(),data.getSource(), data.getVarietyId());

        return exec(sql);
    }

    public Variety queryById(String varietyId){

        String sql = String.format(VarietyDaoImpl.SQL_QUERY_BYID, varietyId);
        RowMapper<Variety> rowMapper = new BeanPropertyRowMapper<Variety>(Variety.class);
        Variety variety = jdbcTemplate.queryForObject(sql,rowMapper);
        return variety;
    }

    public List<Variety> query(Map<String, String> condition) {

        //build Where
        String sql = "SELECT * FROM tb_variety WHERE 1=1 %s %s";
        String limit = buildLimit(condition);
        StringBuilder where = new StringBuilder();
        String vegeName = condition.get("vegeName");
        if (vegeName != null && !vegeName.equals("")) {
            sql = "SELECT * FROM tb_variety,tb_vegeinfo WHERE tb_variety.vegeid = tb_vegeinfo.vegeid %s %s";
            where.append(" AND tb_vegeinfo.vegeName like '%").append(vegeName).append("%'");
        }
        String varietyName = condition.get("varietyName");
        if (varietyName != null && !varietyName.equals("")) {
            where.append(" AND varietyName like '%").append(varietyName).append("%'");
        }

        sql = String.format(sql,where, limit);

        List<Variety> result = new ArrayList<>();
        try {
            List rows = jdbcTemplate.queryForList(sql);
            for (Object row1 : rows) {
                Map row = (Map) row1;
                Variety variety = new Variety();
                variety.setVarietyId(Integer.parseInt(String.valueOf(row.get("varietyid"))));
                variety.setVegeId(Integer.parseInt(String.valueOf(row.get("vegeid"))));
                variety.setVarietyName(String.valueOf(row.get("varietyname")));
                variety.setDescription(String.valueOf(row.get("description")));
                variety.setArea(String.valueOf(row.get("area")));
                variety.setImgUuid(String.valueOf(row.get("imguuid")));
                variety.setSource(Constants.VARIETY_SOURCE_MAP.get(String.valueOf(row.get("source"))));
                Long updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(row.get("timestamp"))).getTime();
                variety.setTimestamp(new Timestamp(updateTime));
                result.add(variety);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int queryTotal(Map<String, String> condition) {
        String where = buildWhere(condition);
        String sql = String.format(VarietyDaoImpl.SQL_QUERY_TOTAL, where);
        return getTotal(sql);
    }

}
