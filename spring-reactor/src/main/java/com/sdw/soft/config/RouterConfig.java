package com.sdw.soft.config;

import com.sdw.soft.handler.TimeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by shangyd on 2018/4/24.
 */
@Configuration
public class RouterConfig {

    @Autowired
    private TimeHandler timeHandler;

    @Bean
    public RouterFunction<ServerResponse> timeRouter() {
        return route(GET("/time"),request -> timeHandler.getTime(request))
                .andRoute(GET("/date"),timeHandler::getDate)
                .andRoute(GET("/times"),timeHandler::sendTimePerSec);
    }
}
