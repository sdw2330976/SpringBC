package com.sdw.soft.demo.hystrix;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

/**
 * Created by shangyd on 17/4/3.
 */
public class CommandFacadeWithPrimarySecondary extends HystrixCommand<String> {

    private static final DynamicBooleanProperty usePrimary = DynamicPropertyFactory.getInstance().getBooleanProperty("primarySecondary.usePrimary",true);
    private final int id;
    public CommandFacadeWithPrimarySecondary(int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("PrimarySecondaryCommand"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.id = id;
    }
    @Override
    protected String run() throws Exception {
        if (usePrimary.get()) {
            return new PrimaryCommand(id).execute();
        }else {
            return new SecondaryCommand(id).execute();
        }
    }

    @Override
    protected String getFallback() {
        return "static-fallback-" + id;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    private static class PrimaryCommand extends HystrixCommand<String>{

        private final int id;
        public PrimaryCommand(int id){
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("PrimaryCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("PrimaryCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(600)));
            this.id = id;
        }
        @Override
        protected String run() throws Exception {
            return "ResponseFromPrimary-" + id;
        }
    }

    private static class SecondaryCommand extends HystrixCommand<String> {

        private final int id;
        public SecondaryCommand(int id) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("SecondaryCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("SecondaryCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(100)));
            this.id = id;
        }

        @Override
        protected String run() throws Exception {
            return "ResponseFromSecondary-" + id;
        }
    }


    public static class JunitTest{

        @Test
        public void testPrimary() {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try{
                ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary",true);
                System.out.println(new CommandFacadeWithPrimarySecondary(20).execute());
            }finally {
                context.shutdown();
                ConfigurationManager.getConfigInstance().clear();
            }
        }

        @Test
        public void testSecondary() {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try{
                ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary",false);
                System.out.println(new CommandFacadeWithPrimarySecondary(20).execute());
            }finally {
                context.shutdown();
            }
        }
    }
}
