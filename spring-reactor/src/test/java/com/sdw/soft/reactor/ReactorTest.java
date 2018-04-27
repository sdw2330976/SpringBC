package com.sdw.soft.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by shangyd on 2018/4/27.
 */
public class ReactorTest {

    @Test
    public void testMono() {
        String[] strs = new String[]{"1","2","3"};
        List<String> list = Arrays.asList(strs);
        Stream<String> stream = list.stream();
        Flux<String> stringFlux = Flux.fromStream(stream);
        stringFlux.subscribe(System.out::println,System.err::println,()->System.out.println("complete"));
    }
}
