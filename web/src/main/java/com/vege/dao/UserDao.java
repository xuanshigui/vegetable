package com.vege.dao;

import com.vege.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    public boolean add(User data);

    public boolean delete(String userid);

    public boolean update(User data);

    public User queryById(String userid);

    public List<User> query(Map<String, String> condition);

    public int queryTotal(Map<String, String> condition);
}
