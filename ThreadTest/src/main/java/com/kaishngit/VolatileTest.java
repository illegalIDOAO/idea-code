package com.kaishngit;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:25 2018/9/5
 */
public class VolatileTest {

    public static void main(String[] args) {
        VolatileDemo demo = new VolatileDemo();
        new Thread(demo).start();

        while (true){
            if(demo.isFlag()){
                System.out.println("stop...");
                break;
            }
            /*try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

}

class VolatileDemo implements Runnable{

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private volatile boolean flag = false;
    //private boolean flag = false;

    public void run() {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag change to true");


    }



}