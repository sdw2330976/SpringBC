package com.sdw.soft.service;

import com.sdw.soft.meta.User;
import com.sdw.soft.repository.UserReactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by shangyd on 2018/4/25.
 */
@Service
public class UserService {

    @Autowired
    private UserReactorRepository userReactorRepository;

    public Mono<User> save(User user) {
       return userReactorRepository.save(user)
        .onErrorResume(e -> userReactorRepository.findByUsername(user.getUsername())
                .flatMap(originalUser -> {
                    user.setId(originalUser.getId());
                    return userReactorRepository.save(user);
                }));

    }

    public Mono<Long> deleteByUsername(String username) {
        return userReactorRepository.deleteByUsername(username);
    }

    public Mono<User> findByUsername(String username) {
        return userReactorRepository.findByUsername(username);
    }

    public Flux<User> findAll() {
        return userReactorRepository.findAll();
    }
}
