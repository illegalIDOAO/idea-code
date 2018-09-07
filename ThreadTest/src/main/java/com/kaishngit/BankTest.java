package com.kaishngit;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:07 2018/9/5
 */
public class BankTest {

    public static void main(String[] args) {

        final Bank bank = new Bank();

        new Thread(new Runnable() {
            public void run() {
                bank.pickMoney();
            }
        },"A").start();

        new Thread(new Runnable() {
            public void run() {
                bank.pickMoney();
            }
        },"B").start();

    }

}

class Bank{

    public void pickMoney(){
        try {
            synchronized (this){
                System.out.println(Thread.currentThread().getName() + " -------------------- start");
                if(Thread.currentThread().getName().equals("A")){
                    for (int i = 1; i <= 30; i++) {
                        if(i % 10 == 0){
                            this.notify();
                            System.out.println("B get up, A sleeping...");
                            this.wait();
                        }
                        System.out.println(Thread.currentThread().getName() + " ------------- " + i);
                    }
                    System.out.println(Thread.currentThread().getName() + " ----------------------- end");
                }

                if(Thread.currentThread().getName().equals("B")){
                    for (int i = 1; i <= 30; i++) {
                        if(i % 10 == 0){
                            this.notify();
                            System.out.println("A get up, B sleeping...");
                            this.wait();
                        }
                        System.out.println(Thread.currentThread().getName() + " ------------- " + i);
                    }
                    System.out.println(Thread.currentThread().getName() + " ----------------------- end");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

