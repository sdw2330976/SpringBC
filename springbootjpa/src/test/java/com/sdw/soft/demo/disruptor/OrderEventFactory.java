package com.sdw.soft.demo.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author: shangyd
 * @create: 2019-07-24 09:43:20
 **/
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
