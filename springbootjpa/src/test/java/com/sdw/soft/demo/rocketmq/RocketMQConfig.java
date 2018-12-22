package com.sdw.soft.demo.rocketmq;

/**
 * Created by shangyd on 2018/5/6.
 */
public class RocketMQConfig {
    protected String nameServerAddr = "127.0.0.1:9876";
    protected String group = "mqdemo";
    protected String topic = "test_queue";
    protected String topic_order = "order_queue";
}
