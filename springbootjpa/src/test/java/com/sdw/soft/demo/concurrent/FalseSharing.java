package com.sdw.soft.demo.concurrent;

/**
 * @author: shangyd
 * @create: 2019-07-25 17:51:46
 **/
public class FalseSharing implements Runnable{

    private static final int thread_number = 4;
    private static final int COUNT = 500 * 1000 * 1000;
    private final int threadIndex;
    private static SharingLong[] sharingLongs = new SharingLong[thread_number];

    public FalseSharing(int threadIndex) {
        this.threadIndex = threadIndex;
    }

    public static void main(String[] args) {

        for (int i = 0; i < thread_number; i++) {
            sharingLongs[i] = new SharingLong();
        }
        long begin = System.currentTimeMillis();

        Thread[] threads = new Thread[thread_number];
        for (int i = 0; i < thread_number; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long cost = System.currentTimeMillis() - begin;
        System.out.println("cost:" + cost);
    }
    @Override
    public void run() {
        int i = COUNT;
        while (i > 0) {
            sharingLongs[threadIndex].value = i;
            i--;
        }

    }


    public static final class SharingLong {

        public volatile long value = 0l;
        public long p1,p2,p3,p4, p5;

    }

}
