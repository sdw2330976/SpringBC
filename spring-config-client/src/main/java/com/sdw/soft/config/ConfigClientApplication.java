package com.sdw.soft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shangyd on 2017/9/28.
 */
@SpringBootApplication
@RestController
public class ConfigClientApplication {

    @Value("${name}")
    private String name;

    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String hello() {
        return name;
    }
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

}
