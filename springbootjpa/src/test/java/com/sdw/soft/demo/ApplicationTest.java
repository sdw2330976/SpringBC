package com.sdw.soft.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by shangyindong on 2017/3/1.
 */
@Configuration
/*@PropertySources(
        {
                @PropertySource("classpath:config.properties"),
                @PropertySource("classpath:db.properties")
        }
)*/
@ComponentScan
@EnableAutoConfiguration
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class);
    }
}
