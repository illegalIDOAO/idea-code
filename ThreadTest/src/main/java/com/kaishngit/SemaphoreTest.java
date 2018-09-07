package com.kaishngit;

import java.util.concurrent.Semaphore;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:54 2018/9/5
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker(i,semaphore);
            new Thread(worker).start();
        }

    }
}

class Worker implements Runnable{
    int i;
    Semaphore semaphore ;
    public Worker(int i ,Semaphore semaphore){
        this.i = i;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            System.out.println(i + "start");
            semaphore.acquire();
            Thread.sleep(1000);
            System.out.println(i + "  - end -");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
