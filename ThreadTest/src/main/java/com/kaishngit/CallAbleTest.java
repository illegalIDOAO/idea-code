package com.kaishngit;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: chuzhaohui
 * @Date: Created in 15:01 2018/9/5
 */
public class CallAbleTest {

    public static void main(String[] args) {

        CallableDemo callableDemo = new CallableDemo();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callableDemo);

        System.out.println("1-----------------");
        new Thread(futureTask).start();
        System.out.println("2-----------------");
        new Thread(futureTask).start();
        System.out.println("3-----------------");
        new Thread(futureTask).start();
        System.out.println("4-----------------");

        try {
            Integer num = futureTask.get();
            System.out.println(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class CallableDemo implements Callable<Integer>{

    public Integer call() throws Exception {
        int num = 0;
        for(int i = 0; i <= 100; i++) {
            num = num + i;
        }
        System.out.println(Thread.currentThread().getName());
        return num;
    }
}
