package com.sdw.soft.demo.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by shangyindong on 2018/4/28.
 */
public class JdkProxyHandler<T> implements InvocationHandler {

    private T target;

    public JdkProxyHandler(T target) {
        this.target = target;
    }

    public void setTarget(T target) {
        this.target = target;
    }

    public T getTarget() {
        return target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-------------处理前--------------");
        Object invoke = method.invoke(target, args);
        System.out.println("-------------处理后--------------");
        return invoke;
    }
}
