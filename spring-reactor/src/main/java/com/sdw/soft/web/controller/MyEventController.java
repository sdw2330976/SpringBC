package com.sdw.soft.web.controller;

import com.sdw.soft.meta.MyEvent;
import com.sdw.soft.repository.MyEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by shangyd on 2018/4/26.
 */
@RestController
@RequestMapping("/events")
public class MyEventController {

    @Autowired
    private MyEventRepository myEventRepository;


    @PostMapping(path = "",consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Void> loadEvents(@RequestBody Flux<MyEvent> events) {
        return myEventRepository.insert(events).then();
    }

    @GetMapping(path = "",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<MyEvent>getEvents() {
        return myEventRepository.findBy();
    }

}
