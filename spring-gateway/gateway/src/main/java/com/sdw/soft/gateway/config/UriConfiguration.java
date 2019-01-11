package com.sdw.soft.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties
public class UriConfiguration {

    private String httpBin = "http://httpbin.org:80";


}
