package com.kaishngit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chuzhaohui
 * @Date: Created in 22:45 2018/9/5
 */
public class ExchangeTest {
    public static void main(String[] args) {

        Exchanger<List<String>> exchanger = new Exchanger();

        List<String> buffer1 = new ArrayList<String>();
        List<String> buffer2 = new ArrayList<String>();

        Producer producer = new Producer(buffer1,exchanger);
        Customer customer = new Customer(buffer2,exchanger);

        new Thread(producer).start();
        new Thread(customer).start();

    }
}
class Producer implements Runnable{

    private List<String> buffer;
    private Exchanger exchanger;

    public Producer(List<String> buffer, Exchanger exchanger){
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("生产者第" + i + "提供");
            for (int j = 0; j < 3; j++) {
                System.out.println("生产者装入" + i  + "--" + j);
                buffer.add("buffer：" + i + "--" + j);
            }
            System.out.println("生产者装满，等待与消费者交换...");

            try {
                TimeUnit.SECONDS.sleep(1);
                //TODO 是否要接收？
                buffer = (List<String>) exchanger.exchange(buffer);
                //exchanger.exchange(buffer);
                System.out.println("生产者交换完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Customer implements Runnable{

    private List<String> buffer;
    private Exchanger exchanger;

    public Customer(List<String> buffer, Exchanger exchanger){
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                //TODO 是否要接收？
                buffer = (List<String>) exchanger.exchange(buffer);
                System.out.println("消费者交换完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费者第" + i + "次提取");
            for (int j = 0; j < 3; j++) {
                System.out.println(buffer.get(0));
                buffer.remove(0);
            }
        }

    }
}