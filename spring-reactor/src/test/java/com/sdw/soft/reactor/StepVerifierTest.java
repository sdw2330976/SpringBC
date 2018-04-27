package com.sdw.soft.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by shangyd on 2018/4/27.
 */
public class StepVerifierTest {


    private Flux<Integer> generateInteger() {
        return Flux.just(1, 2, 3, 4, 5, 6);
    }

    private Mono<Integer> generateMonoWithException() {
        return Mono.error(new Exception("error signal"));
    }
    @Test
    public void testSteperfier() {
        StepVerifier.create(generateInteger())
                .expectNext(1, 2, 3, 4, 5, 6)
                .expectComplete()
                .verify();
        StepVerifier.create(generateMonoWithException())
                .expectErrorMessage("error signal")
                .verify();
    }

    @Test
    public void testMap() {
        StepVerifier.create(Flux.range(1, 6).map(i -> i * i))
                .expectNext(1,4,9,16,25,36)
                .verifyComplete();
    }

    @Test
    public void testFlatMap() {
        StepVerifier.create(Flux.just("Flux", "Mono")
                .flatMap(s -> Flux.fromArray(s.split("\\s*"))
                .delayElements(Duration.ofMillis(100)))
                .doOnNext(System.out::println)
        ).expectNextCount(8).verifyComplete();

    }

    @Test
    public void testFilter() {
        StepVerifier.create(Flux.range(1, 6)
                .filter(i -> i % 2 == 1)
                .map(i -> i * i)
        ).expectNext(1,9,25).verifyComplete();
    }


    @Test
    public void testZip() throws Exception{
        CountDownLatch latch = new CountDownLatch(1);
        Flux.zip(Flux.fromArray("with the expected single item, no item or an error".split("\\s+")),
                Flux.interval(Duration.ofMillis(200)))
                .subscribe(s -> System.out.println(s.getT1()+"-"+s.getT2()), null, latch::countDown);
        latch.await(10, TimeUnit.SECONDS);
    }


    private String syncPrint() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "hello sync print";
    }
    @Test
    public void testScheduler() throws Exception{
        CountDownLatch latch = new CountDownLatch(1);
        Mono.fromCallable(() -> syncPrint())
        .subscribeOn(Schedulers.elastic())
        .subscribe(System.out::println,null,latch::countDown);
        latch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void testErrorOperator() {

    }
}
