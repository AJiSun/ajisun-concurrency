package org.ajisun.coding.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 基本实现
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/12
 * @author: ajisun
 *
 */
public class ReentrantLockDemo {

    private static int num=0;
    private static ReentrantLock lock = new ReentrantLock();

    private static void add(){
        lock.lock();
        try {
            num++;
        }finally {
            lock.unlock();
        }

    }


    private static class T1 extends Thread{

        @Override
        public void run(){
            for (int i=0;i<10000;i++){
                ReentrantLockDemo.add();
            }
        }

    }


    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        T1 t2 = new T1();
        T1 t3 = new T1();

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(ReentrantLockDemo.num);

    }


}
