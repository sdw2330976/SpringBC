package com.sdw.soft.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserProperties {
    private String username;
    private String password;
}
