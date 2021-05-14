package org.ajisun.coding.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/12
 * @author: ajisun
 *
 */
public class ReentrantLockInterruptDemo {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();


    public static class T extends Thread {
        int lock;

        public T(String name, int lock) {
            super(name);
            this.lock = lock;
        }

        /**
         * lockInterruptibly()  方法会循环获取锁，如果当前线程已经中断，则抛出异常InterruptedException
         */

        @Override
        public void run() {
            try {

                if (this.lock == 1) {
                    lock1.lockInterruptibly();
                    TimeUnit.SECONDS.sleep(1);
                    lock2.lockInterruptibly();
                } else {
                    lock2.lockInterruptibly();
                    TimeUnit.SECONDS.sleep(1);
                    lock1.lockInterruptibly();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("中断标志：" + this.isInterrupted());
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }

            }


        }

    }


    public static void main(String[] args) throws InterruptedException {
        T t1 = new T("t1", 1);
        T t2 = new T("t2", 2);

        t1.start();
        t2.start();

//
        TimeUnit.SECONDS.sleep(5);
        t2.interrupt();


    }


}
