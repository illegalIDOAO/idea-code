package com.kaishngit;

import java.util.Random;

/**
 * @Author: chuzhaohui
 * @Date: Created in 22:19 2018/9/5
 */
public class ThreadLocalTest {
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName() + " put data " + data);
                    threadLocal.set(data);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Integer result = threadLocal.get();
                    System.out.println(Thread.currentThread().getName() + " get data " + result);
                }
            }).start();
        }


    }
}
