package com.sdw.soft.repository;

import com.sdw.soft.meta.MyEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

/**
 * Created by shangyd on 2018/4/26.
 */
public interface MyEventRepository extends ReactiveMongoRepository<MyEvent,Long> {

    @Tailable
    public Flux<MyEvent> findBy();
}
