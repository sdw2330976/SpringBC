package com.sdw.soft.repository;

import com.sdw.soft.meta.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * Created by shangyd on 2018/4/25.
 */
public interface UserReactorRepository extends ReactiveCrudRepository<User,String> {

    Mono<User> findByUsername(String username);

    Mono<Long> deleteByUsername(String username);
}
