package com.sdw.soft.demo.jmx;

/**
 * Created by shangyindong on 2018/5/5.
 */
public class User implements UserMBean {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void printHello() {
        System.out.println("hello " + name);
    }

    @Override
    public void printHello(String name) {
        System.out.println("hello " + name);
    }
}
