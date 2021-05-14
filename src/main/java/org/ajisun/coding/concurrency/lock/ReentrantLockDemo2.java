package org.ajisun.coding.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁例子
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/12
 * @author: ajisun
 *
 */
public class ReentrantLockDemo2 {

    private static int num = 0;
    private static ReentrantLock lock = new ReentrantLock();

    private static void add() {
        lock.lock();
        lock.lock();
        try {
            num++;
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }


    public static class T extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                ReentrantLockDemo2.add();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        T t1 = new T();
        T t2 = new T();
        T t3 = new T();

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(ReentrantLockDemo2.num);

    }


}
