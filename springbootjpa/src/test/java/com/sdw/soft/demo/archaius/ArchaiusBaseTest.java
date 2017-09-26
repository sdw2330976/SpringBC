package com.sdw.soft.demo.archaius;

import com.google.common.base.Joiner;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by shangyd on 2017/5/3.
 */
public class ArchaiusBaseTest {

    @Test
    public void test01(){
        //original
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello world");
                subscriber.onCompleted();
            }
        });

        final Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("subscriber finish.");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };
        observable.subscribe(subscriber);

        //simplify
        Observable.just("hello world").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("finish subscribe");
            }
        });

        Observable.from(new String[]{"Tom","Rose"}).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return Joiner.on(" ").useForNull("").join(new String[]{"Hello",s});
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }
}
