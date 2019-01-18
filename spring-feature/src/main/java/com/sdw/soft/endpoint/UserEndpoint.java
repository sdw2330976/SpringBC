package com.sdw.soft.endpoint;

import com.google.common.collect.Maps;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.Map;

@Endpoint(id = "user")
public class UserEndpoint {

    @ReadOperation
    public Map<String, String> hello() {
        Map<String, String> result = Maps.newHashMap();
        result.put("username", "Tom");
        result.put("age", "28");
        result.put("email", "test@163.com");
        return result;
    }
}
