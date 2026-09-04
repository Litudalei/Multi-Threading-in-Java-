package com.synch;

public class DeadLockDemo {

    public static  final String r1="Hello";
    public static  final String r2="Hi";

    public static void main(String[] args) {
        Thread th1=new Thread(
                ()->{
                    synchronized (r1)
                    {
                        System.out.println("Thread t1 locked:Resources r1");
                        synchronized (r2)
                        {
                            System.out.println("Thread t1locked : theResources R2");
                        }
                    }
                }
        );
        Thread th2=new Thread(
                ()->{
                    synchronized (r1)
                    {
                        System.out.println("Thread t2 locked:Resources r2");
                        synchronized (r2)
                        {
                            System.out.println("Thread t2 locked : theResources R1");
                        }
                    }
                }
        );
        th1.start();
        th2.start();
    }
}
