package com.sdw.soft.demo.jmx;

/**
 * Created by shangyindong on 2018/5/5.
 */
public interface UserMBean {

    public String getName();

    public void setName(String name);

    public void printHello();

    public void printHello(String name);
}
