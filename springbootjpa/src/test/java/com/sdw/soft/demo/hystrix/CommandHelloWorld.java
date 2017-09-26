package com.sdw.soft.demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import static org.junit.Assert.*;

import java.util.concurrent.Future;

/**
 * Created by shangyd on 17/4/2.
 */
public class CommandHelloWorld extends HystrixCommand<String>{

    private String name;
    private boolean fallback;
    private int id;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    public CommandHelloWorld(String name,boolean fallback) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FallbackGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
        this.fallback = fallback;
        this.name = name;
    }

    public CommandHelloWorld(int id) {
        super(HystrixCommandGroupKey.Factory.asKey("RequestCacheGroup"));
        this.id = id;
    }
    @Override
    protected String run() throws Exception {
        if (fallback) {
            Thread.sleep(1000);
        }
        if(id != 0){
            System.out.println(Thread.currentThread().getName() + "execute id=" + id);
            return "executed=" + id;
        }
        return "Hello :" + name + " thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "This is a fallback";
    }

    /*@Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }*/

    public static class JunitTest{
        @Test
        public void testSynchronize() {
            String result = new CommandHelloWorld("World").execute();
            System.out.println("testSynchronize:"+result);
        }


        @Test
        public void testAsynchronize() throws Exception{
            Future<String> future = new CommandHelloWorld("World").queue();
            String result = future.get();
            System.out.println("testAsynchronize:" + result);
        }

        @Test
        public void testObserable() throws Exception {
            Observable<String> observe = new CommandHelloWorld("World").observe();
            //blocking
            System.out.println("testObserable:" + observe.toBlocking().single() + "thread:" + Thread.currentThread().getName());
            //non-blocking
            observe.subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    System.out.println("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(String s) {
                    System.out.println("onNext:" + s + " thread:" + Thread.currentThread().getName());
                }
            });
            observe.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println("call=" + s + " thread:" + Thread.currentThread().getName());
                }
            });
        }

        @Test
        public void testFallback() {
            String result = new CommandHelloWorld("World", true).execute();
            System.out.println("result:" + result);
        }

        @Test
        public void testRequestCache() {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                CommandHelloWorld requestCacheCommand1 = new CommandHelloWorld(100);
                CommandHelloWorld requestCacheCommand2 = new CommandHelloWorld(100);
                System.out.println("requestCacheCommand1=" + requestCacheCommand1.execute());
                assertFalse(requestCacheCommand1.isResponseFromCache());
                System.out.println("requestCacheCommand2=" + requestCacheCommand2.execute());
                assertTrue(requestCacheCommand2.isResponseFromCache());
            }finally {
                context.shutdown();
            }
            context = HystrixRequestContext.initializeContext();
            try {
                CommandHelloWorld requestCacheCommand3 = new CommandHelloWorld(100);
                System.out.println("requestCacheCommand3=" + requestCacheCommand3.execute());
                assertFalse(requestCacheCommand3.isResponseFromCache());
            }finally {
                context.shutdown();
            }
        }
    }
}
