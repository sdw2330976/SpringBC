package com.sdw.soft.demo.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by shangyd on 17/4/3.
 */
public class CommandCollapserGetValueForKey extends HystrixCollapser<List<String>,String,Integer>{

    private final Integer key;
    public CommandCollapserGetValueForKey(Integer key) {
        this.key = key;
    }

    @Override
    public Integer getRequestArgument() {
        return key;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> requests) {
        return new BatchCommand(requests);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> requests) {
        int count = 0;
        for (CollapsedRequest<String, Integer> request : requests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static class BatchCommand extends HystrixCommand<List<String>> {

        private final Collection<CollapsedRequest<String,Integer>> requests;

        public BatchCommand(Collection<CollapsedRequest<String,Integer>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CollapserGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
            this.requests = requests;
        }

        @Override
        protected List<String> run() throws Exception {
            List<String> response = new ArrayList<String>();
            for (CollapsedRequest<String, Integer> request : requests) {
                response.add("ValueForKey:" + request.getArgument());
            }
            return response;
        }
    }


    public static class JunitTest{

        @Test
        public void testCollapserRequest() throws Exception{
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                Future<String> f1 = new CommandCollapserGetValueForKey(1).queue();
                Future<String> f2 = new CommandCollapserGetValueForKey(2).queue();
                Future<String> f3 = new CommandCollapserGetValueForKey(3).queue();
                Future<String> f4 = new CommandCollapserGetValueForKey(4).queue();
                System.out.println("f1->" + f1.get());
                System.out.println("f2->" + f2.get());
                System.out.println("f3->" + f3.get());
                System.out.println("f4->" + f4.get());
                System.out.println("AllExecutedCommandSize->" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());

                HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
                System.out.println("GetValueForKey->" + command.getCommandKey().name());
                System.out.println(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
                System.out.println(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
            }finally {
                context.shutdown();
            }
        }
    }
}
