package com.egt_interactive.practice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Problem1 {
    private ReentrantLock lock;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        final int limit = 1_000_000;
        final int numberOfThreads = 5;

        CountToN countToN = new CountToN(lock);

        ExecutorService es = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < limit; i++) {
            es.submit(countToN);
        }

        Thread.sleep(1500);

        System.out.println("Sum: " + countToN.getSum());
    }
}

class CountToN implements Runnable {
    private static int counter = 0;
    private ReentrantLock lock;

    public CountToN(ReentrantLock lock) {
        this.lock = lock;
    }

    public int getSum() {
        return counter;
    }

    public void increment() {
        lock.lock();
        try {
            counter++;
            System.out.println(String.format("Thread [%s], %d", Thread.currentThread().getName(), counter));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        increment();
    }
}
