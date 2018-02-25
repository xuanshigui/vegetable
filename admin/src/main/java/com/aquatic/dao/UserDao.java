package com.aquatic.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean delete() {
        jdbcTemplate.update("delete from test where id=3");
        return true;
    }
}
