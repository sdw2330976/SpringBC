package com.sdw.soft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shangyindong on 2017/9/26.
 */
@EnableEurekaClient
@RestController
@SpringBootApplication
public class EurekaClientApplication {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home() {
        return "hello eureka";
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
}
