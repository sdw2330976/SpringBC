package com.sdw.soft.ribbon.web.controller;

import com.sdw.soft.ribbon.service.HelloService;
import com.sdw.soft.ribbon.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by shangyd on 2017/9/28.
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        return helloService.hello();
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public List<User> listUser() {
        return helloService.listUser();
    }
}
