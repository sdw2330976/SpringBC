package com.sdw.soft.demo.spi;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by shangyindong on 2017/8/10.
 */
public class TestSPI {

    @Test
    public void test01() {
        ServiceLoader<SpiInterface> serviceLoader = ServiceLoader.load(SpiInterface.class);
        Iterator<SpiInterface> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            SpiInterface next = iterator.next();
            next.sayHello();
            System.out.println("interface:" + next.getClass() + ",classloader="+next.getClass().getClassLoader());
        }
        System.out.println("current thread classloader=" + Thread.currentThread().getContextClassLoader());

        System.out.println("current classloader="+TestSPI.class.getClassLoader());

        System.out.println("ServiceLoader classloader="+ServiceLoader.class.getClassLoader());

    }
}
