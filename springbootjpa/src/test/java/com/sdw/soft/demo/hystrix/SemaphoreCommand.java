package com.sdw.soft.demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Test;

/**
 * Created by shangyd on 17/4/3.
 */
public class SemaphoreCommand extends HystrixCommand<String> {

    private final String name;

    public SemaphoreCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SemaphoreCommandGroup"))
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "HystrixThread->" + Thread.currentThread().getName();
    }


    public static class JunitTest{

        @Test
        public void testSemaphore(){
            SemaphoreCommand semaphore = new SemaphoreCommand("semaphore");
            String result = semaphore.execute();
            System.out.println(result);
            System.out.println("MainThread->" + Thread.currentThread().getName());
        }
    }
}
