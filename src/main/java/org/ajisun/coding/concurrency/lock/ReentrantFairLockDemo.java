package org.ajisun.coding.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/12
 * @author: ajisun
 *
 */
public class ReentrantFairLockDemo {


    private static ReentrantLock failLock = new ReentrantLock(true);


    private static class T1 extends Thread{

        public T1(String name){
            super(name);
        }

        @Override
        public void run(){
            for (int i=0;i<5;i++){
                failLock.lock();
                try {
                    System.out.println(this.getName()+"获得锁");
                }finally {
                    failLock.unlock();
                }


            }
        }

    }


    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1("t1");
        T1 t2 = new T1("t2");
        T1 t3 = new T1("t3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

//        System.out.println(ReentrantFairLockDemo.num);

    }

}
