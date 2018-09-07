package com.kaishngit;

import org.junit.Test;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:55 2018/9/5
 */
public class MainTest {
    public static void main(String[] args) {
        new SubThread().start();
        new SubThread().start();

        SubThread2 subThread = new SubThread2();
        new Thread(subThread).start();
        new Thread(subThread).start();
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i );
        }
    }


    @Test
    public void test1(){
        new SubThread().start();
        new SubThread().start();

        SubThread2 subThread = new SubThread2();
        new Thread(subThread).start();
        new Thread(subThread).start();
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i );
        }
    }

}

class SubThread extends Thread{
    @Override
    public void run(){
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " 1 " + i );
        }
    }
}

class SubThread2 implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " 2 " + i );
        }
    }
}

