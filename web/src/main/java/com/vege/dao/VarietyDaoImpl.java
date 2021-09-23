package com.vege.dao;

import com.vege.model.Variety;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class VarietyDaoImpl extends BaseDao implements VarietyDao {

    private static String SQL_INSERT = "INSERT INTO tb_sysuser (username, password, realname, salt, telephone, email,note) VALUES('%s', '%s','%s', '%s','%s', '%s', '%s')";
    private static String SQL_DELETE = "DELETE FROM tb_sysuser WHERE userid=%s";
    private static String SQL_UPDATE = "UPDATE tb_sysuser SET username='%s', password='%s', realname='%s', telephone='%s', email='%s', note='%s' WHERE userid=%s";
    private static String SQL_QUERY = "SELECT * FROM tb_sysuser WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM tb_sysuser WHERE 1=1 %s";
    private static String SQL_QUERY_BYID = "SELECT userid as userId, username as userName, password, realname as realName, salt, telephone as phone, email,note FROM tb_sysuser WHERE userid = %s";

    public VarietyDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean add(Variety data) {
        String sql = String.format(VarietyDaoImpl.SQL_INSERT, data.getVegeId(), data.getVarietyName(), data.getDescription(),data.getArea(),data.getImgUuid(),data.getSource());

        return exec(sql);
    }

    public boolean delete(String userid) {
        String sql = String.format(VarietyDaoImpl.SQL_DELETE, userid);

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
        String where = buildWhere(condition);
        String limit = buildLimit(condition);
        String sql = String.format(VarietyDaoImpl.SQL_QUERY, where, limit);

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
                variety.setSource(String.valueOf(row.get("source")));
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
