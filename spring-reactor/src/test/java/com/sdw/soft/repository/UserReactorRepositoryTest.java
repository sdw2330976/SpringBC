package com.sdw.soft.repository;

import com.sdw.soft.AppServer;
import com.sdw.soft.meta.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

/**
 * Created by shangyd on 2018/4/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppServer.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserReactorRepositoryTest {


    @Autowired
    private UserReactorRepository userReactorRepository;

    @Test
    public void testMongoInsert() {
        Flux<User> all = userReactorRepository.findAll();
        System.out.println("----:"+all);
    }
}