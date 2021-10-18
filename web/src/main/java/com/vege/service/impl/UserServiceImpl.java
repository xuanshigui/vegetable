package com.vege.service.impl;

import com.vege.dao.UserRepository;
import com.vege.model.User;
import com.vege.service.UserService;
import com.vege.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean add(User user) {
        try {
            String salt = MD5Utils.getRandomSalt();
            String password = MD5Utils.encodePassword(user.getPassword(),salt);
            user.setSalt(salt);
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String userId) {

        try {
            User user = userRepository.findByUserId(Integer.parseInt(userId));
            userRepository.delete(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<User> query(Map<String, String> condition) {
        String userNameStr = condition.get("userName");
        if(userNameStr != null && !userNameStr.equals("")){
            userNameStr = "%" + userNameStr + "%";
            return userRepository.findAllByUserNameLike(userNameStr,getPageable(condition));
        }
        return userRepository.findAll(getPageable(condition));
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return userRepository.count();
    }

    public User queryById(String userId) {
        return userRepository.findByUserId(Integer.parseInt(userId));
    }

    @Override
    public User login(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        boolean flag = false;
        if(user!=null){
            flag = MD5Utils.comparePassword(user.getPassword(),password,user.getSalt());
        }
        if(flag){
            return user;
        }else {
            return null;
        }
    }

    @Override
    public boolean isUnique(String userName) {
        List<User> userList = userRepository.findAllByUserName(userName);
        if(userList.size()==0 || userList ==null){
            return true;
        }
        return false;
    }


}
