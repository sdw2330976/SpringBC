package com.sdw.soft.demo.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author: shangyd
 * @create: 2019-07-24 09:43:56
 **/
public class OrderEventHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println("消费了 orderEvent:" + orderEvent.getValue());
    }
}
