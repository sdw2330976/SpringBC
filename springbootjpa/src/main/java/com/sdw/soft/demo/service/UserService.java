package com.sdw.soft.demo.service;

import com.sdw.soft.demo.mapper.UserMapper;
import com.sdw.soft.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shangyd on 2018/4/21.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User findUserByName(String name) {
        return userMapper.findByName(name);
    }

    public void insert() {
        addUser();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser() {
        User user = new User();
        user.setUserName("Rose");
        user.setAge(26);
        userMapper.addUser(user);
        throw new RuntimeException("throws unchecked exception to rollback data");
    }

    public void deleteUser(User user) {
        userMapper.deleteUser(user);
    }
}
