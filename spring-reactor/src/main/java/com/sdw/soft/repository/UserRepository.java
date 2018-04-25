package com.sdw.soft.repository;

import com.sdw.soft.meta.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by shangyd on 2018/4/24.
 */
public interface UserRepository {

    Mono<User> getUser(int id);

    Flux<User> allUser();

    Mono<User> saveUser(Mono<User> person);
}
