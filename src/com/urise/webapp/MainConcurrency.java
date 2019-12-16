package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {

    private static int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();

    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    private static final Lock LOCK3 = new ReentrantLock();
    private static final ReentrantReadWriteLock REENTRANT_READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();
    private static final Lock READE_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private static final int THREAD_NUMBER = 10_000;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
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

        //  List<Thread> threads = new ArrayList<>(THREAD_NUMBER);
        CountDownLatch latch = new CountDownLatch(THREAD_NUMBER);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);


        for (int i = 0; i < THREAD_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    // System.out.println(THREAD_LOCAL.get().format(new Date()));
                }
                latch.countDown();
                System.out.println(dtf.format(LocalDateTime.now()));
                return 5;
            });
//            System.out.println(future.isDone());
//            System.out.println(future.get());//return when future.isDone()
        }


//            Thread thread = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//                latch.countDown();
//            });
//
//            thread.start();
        //   threads.add(thread);
        //
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
// Thread.sleep(500);
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        System.out.println(counter);
        System.out.println(mainConcurrency.atomicCounter.get()); //можно и без .get()

        // HW11 version02 DeadLock
        new Thread(() -> deadLock(LOCK1, LOCK2)).start();
        new Thread(() -> deadLock(LOCK2, LOCK1)).start();
    }

    private void inc() { // private static synchronized void inc() = synchronized (MainConcurrency.class){}
//      LOCK3.lock(); // without atomicCounter
//        synchronized (LOCK) { -  synchronize on static final Object
        //      synchronized (this) { // this =  MainConcurrency mainConcurrency =  private synchronized void inc()
//        try {
        atomicCounter.incrementAndGet();
//            counter++;
//        } finally {
//            LOCK3.unlock();
//        }
        //       }
    }

    // HW11 version02 DeadLock
    private static void deadLock(Object object1, Object object2) {
        synchronized (object1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object2) {
            }
        }
    }
}
