package com.sdw.soft.ribbon.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sdw.soft.ribbon.vo.User;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by shangyd on 2017/9/28.
 */
@Slf4j
@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    public String hello() {
        return restTemplate.getForObject("http://EUREKA.SERVICE-01/hello", String.class);

    }


    @HystrixCommand(fallbackMethod = "defaultListUser")
    public List<User> listUser() {
        logger.info("-------------hystrix test-------------");
        return restTemplate.getForObject("http://EUREKA.SERVICE-01/user", List.class);
    }


    private List<User> defaultListUser() {
        List<User> users = Lists.newArrayList();
        User user = new User();
        user.setUserName("Tom");
        users.add(user);
        return users;
    }

}
