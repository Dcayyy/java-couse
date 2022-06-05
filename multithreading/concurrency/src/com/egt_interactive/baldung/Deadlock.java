package com.egt_interactive.baldung;

import static java.lang.Thread.sleep;

public class Deadlock {

    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    // Example of deadlock
    // To fix the deadlock just change in any of the Threads lock sequence.

    public static void main(String args[]) {
        Thread threadA = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("ThreadA: Holding lock 1...");
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadA: Waiting for lock 2...");

                synchronized (lock1) {
                    System.out.println("ThreadA: Holding lock 1 & 2...");
                }
            }
        });
        Thread threadB = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("ThreadB: Holding lock 2...");
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadB: Waiting for lock 1...");

                synchronized (lock1) {
                    System.out.println("ThreadB: Holding lock 1 & 2...");
                }
            }
        });
        threadA.start();
        threadB.start();
    }
}
