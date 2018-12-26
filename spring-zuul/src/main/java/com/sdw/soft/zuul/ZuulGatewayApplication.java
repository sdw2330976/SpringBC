package com.sdw.soft.zuul;

import com.sdw.soft.zuul.filter.PasswordFilter;
import com.sdw.soft.zuul.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Created by shangyd on 2017/9/29.
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }


    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }

    @Bean
    public PasswordFilter passwordFilter() {
        return new PasswordFilter();
    }

}
