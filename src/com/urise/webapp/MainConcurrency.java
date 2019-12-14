package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {

    private static int counter;

    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    private static final int THREAD_NUMBER = 10_000;

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getClass() + " " + getName() + " " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(getClass() + " " + Thread.currentThread().getName() + " " + Thread.currentThread().getState());
            }
        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREAD_NUMBER);

        for (int i = 0; i < THREAD_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

// Thread.sleep(500);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);

        // HW11 version02 DeadLock
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> {
                try {
                    mainConcurrency.callOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
            thread.start();
        }
    }

    private synchronized void inc() { // private static synchronized void inc() = synchronized (MainConcurrency.class){}
        double sin = Math.sin(13.);
//        synchronized (LOCK) { -  synchronize on static final Object
        //      synchronized (this) { // this =  MainConcurrency mainConcurrency =  private synchronized void inc()
        counter++;
        //       }
    }

    // HW11 version02 DeadLock
    private void callOne() throws InterruptedException {
        synchronized (LOCK1) {
            System.out.println(Thread.currentThread().getName() + "After first LOCK1");
            Thread.sleep(100);
            synchronized (LOCK2) {
                System.out.println(Thread.currentThread().getName() + "After first LOCK2");
            }
        }
        synchronized (LOCK2) {
            System.out.println(Thread.currentThread().getName() + "After second LOCK2");
            Thread.sleep(100);
            synchronized (LOCK1) {
                System.out.println(Thread.currentThread().getName() + "After second LOCK1");
            }
        }
    }
}
