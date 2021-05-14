package org.ajisun.coding.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Copyright (c) 2021. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2021/1/22
 * @author: ajisun
 * @Email: Ajisun
 */
public class ReadWriteLockDemo {

    public static ReentrantLock lock = new ReentrantLock();
    public static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public static  Lock writeLock = readWriteLock.writeLock();
    public static  Lock readLock = readWriteLock.readLock();



//    public

    public static void main(String[] args) {

        lock.lock();

        for (int i=0;i<10;i++){
            new Thread(()->{

                try {
//                    lock.lock();
                    readLock.lock();
                    TimeUnit.SECONDS.sleep(1L);
                    System.out.println("read lock");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
//                    lock.unlock();
                    readLock.unlock();
                }


            }).start();
        }

        for (int i=0;i<5;i++){
            new Thread(()->{

                try {
//                    lock.lock();
                    writeLock.lock();
                    TimeUnit.SECONDS.sleep(1L);
                    System.out.println("write lock");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
//                    lock.unlock();
                    writeLock.unlock();
                }
            }).start();
        }



    }

}
