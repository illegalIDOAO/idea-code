package com.kaishngit;

/**
 * @Author: chuzhaohui
 * @Date: Created in 22:30 2018/9/5
 */
public class DeadLockTest {
    public static void main(String[] args) {

        final DeadLock deadLock = new DeadLock();

        new Thread(new Runnable() {
            public void run() {
               deadLock.leftRight();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
              deadLock.rightLeft();
            }
        }).start();
    }
}

class DeadLock{

    private Object left = new Object();
    private Object right = new Object();

    public void leftRight(){
        synchronized (left){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (right){
                System.out.println("left --> right");
            }
        }
    }

    public void rightLeft(){
        synchronized (right){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (left){
                System.out.println("right --> left");
            }
        }
    }


}
