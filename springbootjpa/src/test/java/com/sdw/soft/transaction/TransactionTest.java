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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void transactionTest() throws Exception{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Class.forName("com.mysql.jdbc.Driver");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test");
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT UserInfo (username,age) VALUES (?,?)");
            preparedStatement.setString(1,"Tom");
            preparedStatement.setInt(2, 23);
            preparedStatement.execute();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }finally {
            if (preparedStatement !=null) preparedStatement.close();
            if (connection != null) connection.close();
        }

    }
}
