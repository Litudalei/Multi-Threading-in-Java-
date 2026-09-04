# Multi-Threading-in-Java-
How Thread and processor will work together
So here Two THreads are waiting for each other for acquiring for lock on the perticular Resources.
Deadlock in Java Multithreading

A deadlock occurs in multithreading when two or more threads are waiting for each other forever, so none of them can continue execution.

Think of it like this:

Thread 1 is holding Resource A and waiting for Resource B.
Thread 2 is holding Resource B and waiting for Resource A.
Neither thread releases what it has → deadlock.

1. Simple Real-Life Example

Imagine two people:

Person A has Pen and wants Book
Person B has Book and wants Pen

Neither person will give up what they already have.

Person A
|
| holds Pen
↓
waits for Book
↑
|
Person B
|
| holds Book
↓
waits for Pen

Both wait forever.

The same thing can happen with Java threads and locks.

2. Java Deadlock Example

Consider two resources:

class ResourceA {
}

class ResourceB {
}

Now create two threads.

public class DeadlockExample {

    public static void main(String[] args) {

        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread t1 = new Thread(() -> {

            synchronized (lock1) {

                System.out.println("Thread 1: Holding lock1");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Thread 1: Waiting for lock2");

                synchronized (lock2) {
                    System.out.println("Thread 1: Holding lock1 and lock2");
                }
            }
        });

        Thread t2 = new Thread(() -> {

            synchronized (lock2) {

                System.out.println("Thread 2: Holding lock2");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Thread 2: Waiting for lock1");

                synchronized (lock1) {
                    System.out.println("Thread 2: Holding lock2 and lock1");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
What happens?

Initially:

Thread 1 → gets lock1
Thread 2 → gets lock2

Then:

Thread 1 → wants lock2
Thread 2 → wants lock1

But:

lock1 → owned by Thread 1
lock2 → owned by Thread 2

Therefore:

Thread 1
|
| owns lock1
↓
waits for lock2
↑
|
owns lock2
|
Thread 2

Neither thread can proceed.

Deadlock!

3. Why Does This Happen?

Deadlock generally requires four conditions.

These are known as the Coffman conditions.

1. Mutual Exclusion

Only one thread can hold a particular lock at a time.

synchronized (lock1) {
// only one thread can execute here
}
2. Hold and Wait

A thread is holding one lock while waiting for another.

Thread 1:

holds lock1
+
waits for lock2
3. No Preemption

A thread cannot simply take a lock away from another thread.

If Thread 2 owns lock2, Thread 1 cannot forcibly take it.

The owner must release it.

4. Circular Wait

Threads form a circular dependency.

Thread 1
↓
waiting for lock2
↓
Thread 2
↓
waiting for lock1
↓
Thread 1

This is the most important condition to recognize.

4. How to Prevent Deadlock

There are several techniques.

Technique 1: Always Acquire Locks in the Same Order

This is one of the most common solutions.

Bad

Thread 1:

synchronized (lock1) {
synchronized (lock2) {
}
}

Thread 2:

synchronized (lock2) {
synchronized (lock1) {
}
}

The order is different.

Thread 1: lock1 → lock2

Thread 2: lock2 → lock1

This can cause deadlock.

Good

Make both threads acquire locks in the same order:

synchronized (lock1) {

    synchronized (lock2) {

        // critical section

    }
}

And Thread 2 also:

synchronized (lock1) {

    synchronized (lock2) {

        // critical section

    }
}

Now:

Thread 1: lock1 → lock2
Thread 2: lock1 → lock2

A circular wait is avoided.