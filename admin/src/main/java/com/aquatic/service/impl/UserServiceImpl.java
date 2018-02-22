package com.aquatic.service.impl;

import com.aquatic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean delRecord() {
        jdbcTemplate.update("delete from test where id=3");
        return true;
    }
}
