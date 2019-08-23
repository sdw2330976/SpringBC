package com.sdw.soft.route.config;

import com.sdw.soft.route.service.ServerRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: shangyd
 * @create: 2019-08-23 17:27:07
 **/
@Configuration
@ConditionalOnClass(ServerRouteService.class)
@EnableConfigurationProperties(ServerRouteProperties.class)
public class ServerRouteAutoConfigure {

    private final ServerRouteProperties properties;

    @Autowired
    public ServerRouteAutoConfigure(ServerRouteProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "server.route", value = "enable", havingValue = "true")
    ServerRouteService serverRouteService() {
        return new ServerRouteService();
    }
}
