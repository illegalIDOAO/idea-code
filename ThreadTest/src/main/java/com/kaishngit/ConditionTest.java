package com.kaishngit;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: chuzhaohui
 * @Date: Created in 20:15 2018/9/5
 */
public class ConditionTest {
    public static void main(String[] args) {
        final ConditionDemo conditionDemo = new ConditionDemo();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    conditionDemo.loopA();
                }
            }
        },"A").start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    conditionDemo.loopB();
                }
            }
        },"B").start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    conditionDemo.loopC();
                }
            }
        },"C").start();

    }
}

class ConditionDemo{
    int num = 1;

    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void loopA(){
        lock.lock();
        try {
            if(num != 1){
                condition1.await();
            }

            System.out.print(Thread.currentThread().getName());

            num = 2;
            condition2.signal();

        } catch (InterruptedException e) {
                e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(){
        lock.lock();
        try {
            if(num != 2){
                condition2.await();
            }

            System.out.print(Thread.currentThread().getName());

            num = 3;
            condition3.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(){
        lock.lock();
        try {
            if(num != 3){
                condition3.await();
            }

            System.out.print(Thread.currentThread().getName());

            num = 1;
            condition1.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}