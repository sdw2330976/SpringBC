package com.sdw.soft.feign.fallback;

import com.sdw.soft.feign.service.TestClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class HystrixClientFallbackFactory implements FallbackFactory<TestClient> {
    @Override
    public TestClient create(Throwable throwable) {
        return ()->"feign hystrix service is down.";
    }
}
