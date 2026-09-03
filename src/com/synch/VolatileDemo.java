package com.synch;

public class VolatileDemo {
    public static void main(String[] args)throws Exception {
        SharedResources sr=new SharedResources();
        Thread th1=new Thread(()->
                sr.doWork()

        );
        Thread th2=new Thread(
                ()-> {
                    try {
                        Thread.sleep(2000);
                        sr.setStopFlag();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        th1.start();
       // th1.join();
        th2.start();
        //th2.join();
        th1.join();
        th2.join();
        System.out.println("Main thread work is done");
    }
}
