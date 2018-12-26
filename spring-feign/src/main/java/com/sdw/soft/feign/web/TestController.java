package com.sdw.soft.feign.web;

import com.sdw.soft.feign.service.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestClient testClient;

    @GetMapping(value = "/hello")
    public String hello() {
        return testClient.hello();
    }
}
