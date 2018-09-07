package com.kaishngit;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:48 2018/9/5
 */
public class UseTimeTest {

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            new Thread(threadDemo).start();
        }


        try {
            threadDemo.countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

class ThreadDemo implements Runnable{
    CountDownLatch countDownLatch = new CountDownLatch(10);

    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
        countDownLatch.countDown();
        System.out.println(Thread.currentThread().getName() + "执行完毕");

    }



}