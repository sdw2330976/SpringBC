package com.sdw.soft.demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * Created by shangyd on 17/4/3.
 */
public class CommandWithFallbackViaNetwork extends HystrixCommand<String> {

    private final String name;
    public CommandWithFallbackViaNetwork(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueCommand"))
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("RemoteServiceFallback")));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
//        return RemoteService.doBusiness();
        throw new RuntimeException("Force failture for demonstrate");
    }

    @Override
    protected String getFallback() {
        return new FallbackCommand(100).execute();
    }

    private static class FallbackCommand extends HystrixCommand<String>{

        private final int id;
        public FallbackCommand(int id) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteService"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueCommand")));
            this.id = id;
        }

        @Override
        protected String run() throws Exception {
            return "Read from Memcached";
        }

        @Override
        protected String getFallback() {
            return null;
        }
    }

    public static void main(String[] args) {
        String result = new CommandWithFallbackViaNetwork("FallbackCommand").execute();
        System.out.println("result->" + result);
    }
}
