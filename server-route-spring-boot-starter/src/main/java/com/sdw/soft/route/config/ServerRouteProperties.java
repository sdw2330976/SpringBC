package com.sdw.soft.route.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: shangyd
 * @create: 2019-08-23 17:22:40
 **/
@ConfigurationProperties("server.route")
@Data
public class ServerRouteProperties {

    @Data
    public static class Zk {
        private String url;
        private int port;
    }

    private Zk zk;

}
