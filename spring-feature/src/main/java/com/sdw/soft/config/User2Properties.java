package com.sdw.soft.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:user.properties")
@ConfigurationProperties(prefix = "user2")
public class User2Properties {

    private String username;
    private String password;
}
