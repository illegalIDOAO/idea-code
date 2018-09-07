package com.kaishngit;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:44 2018/9/5
 */
public class CyslicTest {
    public static void main(String[] args) {

        Task task = new Task();
        try {
            new Thread(task).start();
            Thread.sleep(1000);
            new Thread(task).start();
            Thread.sleep(1000);
            new Thread(task).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Task implements Runnable{
    CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    public void run() {
        System.out.println(Thread.currentThread().getName() + "------start-----" + System.currentTimeMillis());
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "------end-----" + System.currentTimeMillis());
    }
}

