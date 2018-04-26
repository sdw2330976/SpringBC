package com.sdw.soft.web.controller;

import com.sdw.soft.meta.MyEvent;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Created by shangyd on 2018/4/26.
 */
public class MyEventControllerTest {

    @Test
    public void testLoadEvents() {
        Flux<MyEvent> eventFlux = Flux.interval(Duration.ofSeconds(1))
                .map(l -> new MyEvent(System.currentTimeMillis(), "message-" + l)).take(5);
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient.post().uri("/events")
        .contentType(MediaType.APPLICATION_STREAM_JSON)
        .body(eventFlux,MyEvent.class)
        .retrieve()
        .bodyToMono(Void.class)
        .block();

    }

}