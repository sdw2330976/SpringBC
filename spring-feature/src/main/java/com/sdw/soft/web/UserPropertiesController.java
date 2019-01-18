package com.sdw.soft.web;

import com.sdw.soft.config.User2Properties;
import com.sdw.soft.config.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserPropertiesController {

    @Autowired
    private UserProperties userProperties;

    @Autowired
    private User2Properties user2Properties;

    @RequestMapping(value = "/1",method = RequestMethod.GET)
    public String user1() {
        return userProperties.toString();
    }

    @GetMapping(value = "/2")
    public String user2() {
        return user2Properties.toString();
    }
}
