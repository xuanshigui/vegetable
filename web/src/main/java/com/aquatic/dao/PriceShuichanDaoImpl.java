package com.aquatic.dao;


import java.util.*;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class PriceShuichanDaoImpl implements PriceShuichanDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Object> findYears() {
        String sb = "SELECT YEAR(price_shuichan.date) as year " +
                " FROM price_shuichan" +
                " GROUP BY YEAR(price_shuichan.date)";

        List<Object> result = new ArrayList<>();
        List rows = jdbcTemplate.queryForList(sb);
        Iterator iterator = rows.iterator();
        while (iterator.hasNext()) {
            Map row = (Map) iterator.next();
            result.add(row.get("year"));
        }

        return result;
    }

    @Override
    public List<Object[]> findNames() {
        String sb = "SELECT price_shuichan.name as name, COUNT(price_shuichan.name) AS total " +
                " FROM price_shuichan" +
                " GROUP BY price_shuichan.name" +
                " HAVING COUNT(price_shuichan.name)>=500";

        List<Object[]> result = new ArrayList<>();
        List rows = jdbcTemplate.queryForList(sb);
        Iterator iterator = rows.iterator();
        while (iterator.hasNext()) {
            Map row = (Map) iterator.next();
            Object[] item = new Object[2];
            item[0] = row.get("name");
            item[1] = row.get("total");
            result.add(item);
        }

        return result;
    }


}
