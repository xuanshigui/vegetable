package com.vege.service.impl;

import com.vege.dao.UserDao;
import com.vege.model.User;
import com.vege.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean add(User user) {
        return userDao.add(user);
    }

    @Override
    public boolean delete(String userid) {
        return userDao.delete(userid);
    }

    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    @Override
    public List<User> query(Map<String, String> condition) {
        return userDao.query(condition);
    }

    @Override
    public int queryTotal(Map<String, String> condition) {
        return userDao.queryTotal(condition);
    }

    public User queryById(String userid){
        return userDao.queryById(userid);
    }
}
