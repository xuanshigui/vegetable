package com.vege.service;

import com.vege.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    boolean add(User data);

    boolean delete(String id);

    boolean update(User user);

    List<User> query(Map<String, String> condition);

    int queryTotal(Map<String, String> condition);

    User queryById(String userid);
}
