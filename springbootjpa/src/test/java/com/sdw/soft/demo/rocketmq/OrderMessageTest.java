package com.sdw.soft.demo.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by shangyd on 2018/5/6.
 */
public class OrderMessageTest extends RocketMQConfig{

    @Test
    public void productor() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        MQProducer producer = new DefaultMQProducer(group);
        producer.start();
        String[] tags = new String[]{"tagA", "tagB", "tagC", "tagD", "tagE"};
        for (int i = 0; i < 100; i++) {
            int orderId = i % 10;
            Message msg = new Message(topic_order, tags[i % tags.length], "Key" + i, ("hello rocketmq " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, orderId);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }

    @Test
    public void consumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(topic_order);

    }
}
