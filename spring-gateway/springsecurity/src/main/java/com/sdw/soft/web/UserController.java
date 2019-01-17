package com.sdw.soft.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/")
public class UserController {

    @GetMapping("/user/info")
    public String userInfo() {
        return "user age is " + new Random().nextInt(100);
    }
}
