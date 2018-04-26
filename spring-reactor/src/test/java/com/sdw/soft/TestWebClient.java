package com.sdw.soft;

import com.sdw.soft.meta.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

/**
 * Created by shangyd on 2018/4/26.
 */
public class TestWebClient {

    @Test
    public void webClientTest() throws Exception{
        WebClient webClient = WebClient.create("http://localhost:8080");
        Flux<String> response = webClient.get().uri("/hello")
                .retrieve()
                .bodyToFlux(String.class);
        response.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);

    }

    @Test
    public void webClientFlux() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        webClient.get().uri("/user")
        .accept(MediaType.APPLICATION_STREAM_JSON)
        .exchange()
        .flatMapMany(response -> response.bodyToFlux(User.class))
        .doOnNext(System.out::println)
        .blockLast();
    }


    @Test
    public void webClientPush() {
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient.get().uri("/times")
        .accept(MediaType.TEXT_EVENT_STREAM)
        .retrieve()
        .bodyToFlux(String.class)
        .log()
        .take(10)
        .blockLast();
    }
}
