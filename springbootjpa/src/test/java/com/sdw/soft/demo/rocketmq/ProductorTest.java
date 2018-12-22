package com.sdw.soft.demo.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by shangyd on 2018/5/4.
 */
public class ProductorTest extends RocketMQConfig {



    @Test
    public void testSyncProductor() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message(topic,"tagA",("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }


    @Test
    public void testAsyncProductor() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 100; i++) {
            final int index = i;
            Message msg = new Message(topic, "TagA", "orderId188",
                    "hello rocketmq".getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        producer.shutdown();
    }


    @Test
    public void testOnewayProductor() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.start();
        for(int i=0;i<100;i++) {
            Message message = new Message(topic,"tagA",("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(message);
        }
        producer.shutdown();
    }
}
