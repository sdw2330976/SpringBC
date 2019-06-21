package com.sdw.soft.netty.client;

import io.netty.util.HashedWheelTimer;
import lombok.extern.log4j.Log4j2;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: shangyd
 * @create: 2019-03-26 14:34:18
 **/
@Log4j2
public class NettyTCPClient {


    public static void main(String[] args) {
        HashedWheelTimer timer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS, 16);
        System.out.println(LocalTime.now());

        timer.newTimeout(timeout -> {
            System.out.println(LocalTime.now());
            System.out.println(timeout);
        }, 5, TimeUnit.SECONDS);

        timer.newTimeout(timeout ->
                System.out.println("hello"), 10, TimeUnit.SECONDS);
        LockSupport.park();
    }

}
