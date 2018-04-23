package com.sdw.soft.transaction;

import com.alibaba.fastjson.JSONObject;
import com.sdw.soft.demo.Application;
import com.sdw.soft.demo.service.UserService;
import com.sdw.soft.demo.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by shangyd on 2018/4/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionTest {


    @Autowired
    private UserService userService;

    @Test
    public void test01() {
        User tom = userService.findUserByName("Tom");
        System.out.println(JSONObject.toJSON(tom));
    }

    @Test
    public void testInsert() {
//        userService.addUser();
        userService.insert();
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setId(3l);
        userService.deleteUser(user);
    }
}
