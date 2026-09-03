package com.synch;

public class SharedResources {
    public volatile boolean stopFlag=false;
    public void setStopFlag()
    {
        stopFlag=true;
    }
    public void doWork()
    {
        while (!stopFlag)
        {
            System.out.println("it is working");
        }
        System.out.println("work is stopped");
    }
}
