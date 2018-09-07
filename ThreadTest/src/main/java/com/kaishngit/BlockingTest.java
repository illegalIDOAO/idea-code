package com.kaishngit;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chuzhaohui
 * @Date: Created in 23:22 2018/9/5
 */
public class BlockingTest {
    public static void main(String[] args) {

        final BlockingQueue<Integer> queue = new LinkedBlockingDeque<Integer>(10);

        new Thread(new Runnable() {
            int num = 0;
            @Override
            public void run() {
                while (true){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("生产 " + num);
                        queue.offer(num);
                        num ++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

        new Thread(new Runnable() {
            int num = 0;
            @Override
            public void run() {
                while (true){
                    try {
                        System.out.println("消费 " + queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}


