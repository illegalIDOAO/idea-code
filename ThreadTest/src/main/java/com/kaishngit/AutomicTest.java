package com.kaishngit;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: chuzhaohui
 * @Date: Created in 15:15 2018/9/5
 */
public class AutomicTest {
    public static void main(String[] args) {
        AutomicDemo automicDemo = new AutomicDemo();
        for(int i = 0 ; i < 100; i++){
            new Thread(automicDemo).start();
        }
        /*IntDemo intDemo = new IntDemo();
        for(int i = 0 ; i < 100; i++){
            new Thread(intDemo).start();
        }*/
    }
}

class AutomicDemo implements Runnable{
    AtomicInteger num = new AtomicInteger(0);
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " -- " + num.getAndIncrement());
    }
}

class IntDemo implements Runnable{
    int num = 0;
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " -- " + num++);
    }
}