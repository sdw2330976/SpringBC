package com.sdw.soft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Profile;

/**
 * Created by shangyindong on 2017/9/26.
 */
@EnableEurekaClient
@SpringBootApplication
public class EurekaClientApplication02 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication02.class, args);
    }
}
