package com.sdw.soft.demo.rxjava;

import com.google.common.base.Joiner;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by shangyd on 2017/5/13.
 */
public class TestRxJava {

    @Test
    public void test01() {
        Observable observable = Observable.just("hello").map(new Func1() {
            @Override
            public Object call(Object o) {
                return Joiner.on(" ").useForNull("").join(new Object[]{o,"world"});
            }
        });
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println(o.toString());
            }
        });
    }
}
