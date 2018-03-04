package com.aquatic.service.impl;

import com.aquatic.dao.QueryDao;
import com.aquatic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryServiceImpl implements QueryService {

    private final QueryDao queryDao;

    @Autowired
    public QueryServiceImpl(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    @Override
    public boolean add(String type, Map<String, String> data) {
        return queryDao.add(type, data);
    }

    @Override
    public boolean delete(String type, String id) {
        return queryDao.delete(type, id);
    }

    @Override
    public boolean update(String type, Map<String, String> data) {
        return queryDao.update(type, data);
    }

    @Override
    public List<Map<String, Object>> query(String type, Map<String, String> condition) {
        return queryDao.query(type, condition);
    }
}
