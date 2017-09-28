package com.sdw.soft.ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by shangyd on 2017/9/28.
 */
@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    public String hello() {
        return restTemplate.getForObject("http://EUREKA.SERVICE-01/hello", String.class);

    }
}
