package com.sdw.soft.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shangyindong on 2017/9/26.
 */
@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@RestController
public class EurekaClientApplication {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home() {
        return "hello eureka";
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
}
