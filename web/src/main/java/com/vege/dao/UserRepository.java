package com.vege.dao;

import com.vege.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {


    User findByUserId(Integer userId);

    Page<User> findAll(Pageable pageable);

    Page<User> findAllByUserNameLike(String userName, Pageable pageable);

    List<User> findAllByUserName(String userName);

    User findByUserName(String userName);
}
