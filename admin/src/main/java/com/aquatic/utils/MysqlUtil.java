package com.aquatic.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.tomcat.jdbc.pool.DataSource;

public class MysqlUtil extends JdbcTemplate {
    public static JdbcTemplate getJdbcTemplate(String url, String username, String password) {
        DataSource ds = new DataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);

        return new JdbcTemplate(ds);
    }
}
