package com.sdw.soft.feign.service;

import com.sdw.soft.feign.fallback.HystrixClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "eureka-provider", fallbackFactory = HystrixClientFallbackFactory.class)
public interface TestClient {

    @GetMapping(value = "/api/front/test/hello")
    String hello();
}
