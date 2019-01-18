package com.sdw.soft.endpoint;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtEndpointConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public UserEndpoint userEndpoint() {
        return new UserEndpoint();
    }
}
