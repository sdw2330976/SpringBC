package com.sdw.soft.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-provider")
public interface TestClient {

    @GetMapping(value = "/api/front/test/hello")
    String hello();
}
