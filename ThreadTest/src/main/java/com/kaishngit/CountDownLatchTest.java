package com.kaishngit;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: chuzhaohui
 * @Date: Created in 19:50 2018/9/5
 */
public class CountDownLatchTest {
    private static CountDownLatch countDownLatch = new CountDownLatch(5);

    public static void main(String[] args) {
        BoossThread boossThread = new BoossThread(countDownLatch);
        EmployeeThread employeeThread = new EmployeeThread(countDownLatch);

        new Thread(boossThread).start();
        for (int i = 0; i < countDownLatch.getCount(); i++) {
            new Thread(employeeThread).start();
        }

    }
}

class EmployeeThread implements Runnable{

    private CountDownLatch countDownLatch ;
    public EmployeeThread(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + "到达");
        countDownLatch.countDown();
    }
}

class BoossThread implements Runnable{
    private CountDownLatch countDownLatch ;
    public BoossThread(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        System.out.println("等待");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("开始");
    }
}
