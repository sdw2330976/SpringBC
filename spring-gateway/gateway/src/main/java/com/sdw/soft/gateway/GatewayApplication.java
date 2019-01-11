package com.sdw.soft.gateway;

import com.sdw.soft.gateway.config.UriConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@EnableConfigurationProperties(UriConfiguration.class)
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", p -> p.path("/get")
                        .filters(f -> f.addRequestHeader("hello", "world"))
                        .uri("http://httpbin.org:80"))
                .route("hystrix_fallback_route", p -> p.host("*.hystrix.com")
                        .filters(f -> f.hystrix(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://httpbin.org:80"))
                .route("rewrite_route", p -> p.host("*.rewrite.org")
                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segement}"))
                        .uri("http://httpbin.org:80"))
                .route("limit_route", p -> p.host("*.limited.org").and().path("/anything/**")
                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
                        .uri("http://httpbin.org:80"))
                .route("websocket_route", p -> p.path("/echo").uri("ws://localhost:9000"))
                .build();
       /* return builder.routes()
                .route(r -> r.host("**.springbc.org").and().header("X-Next-Url").uri("http://www.baidu.com"))
                .route(r -> r.host("**.springbc.org").and().query("utils").uri("http://www.baidu.com"))
                .build();*/
    }


    @Bean
    RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 2);
    }

    @GetMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
}
