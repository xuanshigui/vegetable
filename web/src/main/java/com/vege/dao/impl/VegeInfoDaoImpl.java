package com.vege.dao.impl;

import com.vege.constants.Constants;
import com.vege.dao.VegeInfoDao;
import com.vege.model.VegeInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VegeInfoDaoImpl extends BaseDao implements VegeInfoDao {

    private static String SQL_INSERT = "INSERT INTO tb_vegeInfo (vegename, alias, imguuid, introduction, classification, note, updatetime) VALUES('%s', '%s','%s', '%s', %s, '%s', '%s')";
    private static String SQL_DELETE = "DELETE FROM tb_vegeInfo WHERE vegeid=%s";
    private static String SQL_UPDATE = "UPDATE tb_vegeinfo SET vegename='%s', alias='%s', imguuid='%s', introduction='%s', classification='%s', note='%s', updatetime='%s' WHERE vegeid=%s";
    private static String SQL_QUERY = "SELECT * FROM tb_vegeinfo WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM tb_vegeinfo WHERE 1=1 %s";
    private static String SQL_QUERY_BYID = "SELECT vegeid as vegeId, vegename as vegeName, alias,imguuid, introduction, classification, note, updatetime as updateTime FROM tb_vegeinfo WHERE vegeid = %s";

    public VegeInfoDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean add(VegeInfo data) {
        String sql = String.format(VegeInfoDaoImpl.SQL_INSERT, data.getVegeName(), data.getAlias(), data.getImgUuid(), data.getIntroduction(), Integer.parseInt(data.getClassification()), data.getNote(), data.getUpdateTime());

        return exec(sql);
    }

    public boolean delete(String vegeId) {
        String sql = String.format(VegeInfoDaoImpl.SQL_DELETE, vegeId);

        return exec(sql);
    }

    public boolean update(VegeInfo data) {
        String sql = String.format(VegeInfoDaoImpl.SQL_UPDATE, data.getVegeName(), data.getAlias(), data.getImgUuid(), data.getIntroduction(), data.getClassification(), data.getNote(), data.getUpdateTime(),data.getVegeId());

        return exec(sql);
    }

    public VegeInfo queryById(String vegeId){

        String sql = String.format(VegeInfoDaoImpl.SQL_QUERY_BYID, vegeId);
        RowMapper<VegeInfo> rowMapper = new BeanPropertyRowMapper<VegeInfo>(VegeInfo.class);
        VegeInfo vegeInfo = jdbcTemplate.queryForObject(sql,rowMapper);
        return vegeInfo;
    }

    public List<VegeInfo> query(Map<String, String> condition) {
        String buildCondition = condition.get("vegeName");
        StringBuilder where = new StringBuilder();
        if(buildCondition != null && !buildCondition.equals("")){
            where.append(" AND vegename like '%").append(buildCondition).append("%'");
        }
        String limit = buildLimit(condition);
        String sql = String.format(VegeInfoDaoImpl.SQL_QUERY, where, limit);

        List<VegeInfo> result = new ArrayList<>();
        try {
            List rows = jdbcTemplate.queryForList(sql);
            for (Object row1 : rows) {
                Map row = (Map) row1;
                VegeInfo vegeInfo = new VegeInfo();
                vegeInfo.setVegeId(Integer.parseInt(String.valueOf(row.get("vegeId"))));
                vegeInfo.setVegeName(String.valueOf(row.get("vegename")));
                vegeInfo.setAlias(String.valueOf(row.get("alias")));
                vegeInfo.setImgUuid(String.valueOf(row.get("imguuid")));
                vegeInfo.setIntroduction(String.valueOf(row.get("introduction")));
                vegeInfo.setClassification(Constants.VEGE_CLASS_MAP.get(row.get("classification").toString()));
                vegeInfo.setNote(String.valueOf(row.get("note")));
                Long updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(row.get("updatetime"))).getTime();
                vegeInfo.setUpdateTime(new Timestamp(updateTime));
                result.add(vegeInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int queryTotal(Map<String, String> condition) {
        String where = buildWhere(condition);
        String sql = String.format(VegeInfoDaoImpl.SQL_QUERY_TOTAL, where);

        return getTotal(sql);
    }

    public Map<String,String> getVegeIdAndName(){
        String sql = "SELECT vegeid, vegeName FROM tb_vegeinfo";
        Map<String,String> result = new HashMap<>();
        try {
            List rows = jdbcTemplate.queryForList(sql);
            for (Object row : rows) {
                Map vege = (Map) row;
                result.put(String.valueOf(vege.get("vegeid")),String.valueOf(vege.get("vegename")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getVegeNameById(String vegeId){
        String sql = "SELECT vegeName FROM tb_vegeinfo WHERE vegeid = " + vegeId;
        return jdbcTemplate.queryForObject(sql,String.class);
    }

}
