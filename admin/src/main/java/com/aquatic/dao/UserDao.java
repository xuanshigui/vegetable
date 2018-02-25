package com.aquatic.dao;

class UserDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public boolean delete(){
        jdbcTemplate.update("delete from test where id=3");
        return true;
    }
}