package com.vege.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestDaoImpl extends BaseDao  {

    private static String SQL_INSERT = "INSERT INTO tb_content (content) VALUES('%s')";

    public TestDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean add(String data) {
        String sql = String.format(TestDaoImpl.SQL_INSERT, data);

        return exec(sql);
    }


}
