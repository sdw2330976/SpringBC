package com.sdw.soft.demo.proxy.dao;

/**
 * Created by shangyindong on 2018/4/28.
 */
public class UserDaoImpl implements UserDao {
    @Override
    public void insert() {
        System.out.println("user insert complete.");
    }
}
