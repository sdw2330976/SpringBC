package com.sdw.soft.web.controller;

import com.sdw.soft.meta.User;
import com.sdw.soft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by shangyd on 2018/4/25.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("")
    public Mono<User> save(User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable("username") String username) {
        return userService.deleteByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("")
    public Flux<User> findAll() {
        return userService.findAll();
    }
}
