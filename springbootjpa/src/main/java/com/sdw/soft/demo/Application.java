package com.sdw.soft.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by shangyd on 2017/9/23.
 */
@SpringBootApplication
@MapperScan("com.sdw.soft.demo.mapper")
//@EnableEurekaClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
