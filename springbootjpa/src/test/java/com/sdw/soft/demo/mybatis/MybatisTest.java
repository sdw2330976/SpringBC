package com.sdw.soft.demo.mybatis;

import com.sdw.soft.demo.Application;
import com.sdw.soft.demo.mapper.UserMapper;
import com.sdw.soft.demo.vo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by shangyd on 2017/9/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MybatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01() {
        User tom = userMapper.findByName("tom");
        Assert.assertEquals(26,tom.getAge());
    }
}
