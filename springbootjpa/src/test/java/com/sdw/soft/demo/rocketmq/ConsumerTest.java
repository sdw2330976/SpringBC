package com.sdw.soft.demo.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created by shangyd on 2018/5/4.
 */
public class ConsumerTest extends RocketMQConfig{

    @Test
    public void testConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr(nameServerAddr);
        try {
            consumer.subscribe(topic,"*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.setInstanceName(UUID.randomUUID().toString());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt messageExt : msgs) {
                    String msg = new String(messageExt.getBody());
                    System.out.println("msg data from rocketMQ:" + msg);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        System.out.println("RocketMQConsumer started  group=" + consumer.getConsumerGroup() + " instace=" + consumer.getInstanceName());

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
