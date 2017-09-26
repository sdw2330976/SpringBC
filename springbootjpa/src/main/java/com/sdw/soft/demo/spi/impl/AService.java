package com.sdw.soft.demo.spi.impl;

import com.sdw.soft.demo.spi.SpiInterface;

/**
 * Created by shangyindong on 2017/8/10.
 */
public class AService implements SpiInterface {
    @Override
    public void sayHello() {
        System.out.println("A Service");
    }
}
