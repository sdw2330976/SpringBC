package com.sdw.soft.demo.proxy;

import com.sdw.soft.demo.proxy.dao.UserDao;
import com.sdw.soft.demo.proxy.dao.UserDaoImpl;
import com.sdw.soft.demo.proxy.jdk.JdkProxyHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by shangyindong on 2018/4/28.
 */
public class ProxyTest {


    @Test
    public void testJdk() {
        UserDao userDao = (UserDao)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{UserDao.class}, new JdkProxyHandler(new UserDaoImpl()));
        userDao.insert();
    }
}
