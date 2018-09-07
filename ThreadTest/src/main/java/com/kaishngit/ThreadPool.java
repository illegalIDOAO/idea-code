package com.kaishngit;

import java.util.concurrent.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:40 2018/9/5
 */
public class ThreadPool {

    public static void main(String[] args) {
        int corePoolSize = 3;
        int maximumPoolSize = 5;
        long keepAliveTime = 10L;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(3);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                workQueue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 10; i++) {
            MyTask myTast  = new MyTask(i);
            poolExecutor.execute(myTast);
            System.out.println("线程池中线程数目：" +
                    poolExecutor.getPoolSize() + "，队列中等待执行的任务数目："+
                    poolExecutor.getQueue().size() + "，已执行完的任务数目："+
                    poolExecutor.getCompletedTaskCount());
        }

        poolExecutor.shutdown();
    }
}

class MyTask implements Runnable{

    private int num;

    public MyTask(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行: " + num);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行完毕:" + num);
    }
}
