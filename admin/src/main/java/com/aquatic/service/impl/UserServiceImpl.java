package com.aquatic.service.impl;

import com.aquatic.service.UserService;
import com.aquatic.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public boolean delRecord() {
        return userDao.delete();
    }
}
