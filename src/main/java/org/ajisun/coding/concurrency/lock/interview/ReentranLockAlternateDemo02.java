package org.ajisun.coding.concurrency.lock.interview;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Copyright (c) 2021. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.interview
 * @Date: 2021/4/13
 * @author: ajisun
 * @Email: Ajisun
 */
public class ReentranLockAlternateDemo02 {

    private static volatile Boolean  flag=true;
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition1 = lock.newCondition();
    static Condition condition2 = lock.newCondition();


    public static void main(String[] args) throws InterruptedException {
        char[] chars = "abcd".toCharArray();
        char[] nums = "1234".toCharArray();

        Thread t1= new Thread(()->{

            lock.lock();
                try {
                    for (char ch:chars){
                        System.out.print(ch +" ");
                        condition2.signal();
                        condition1.await();
                    }
                    condition2.signal();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

        });


        Thread t2= new Thread(()->{

            lock.lock();

            try {
                for (char ch:nums){
                    System.out.print(ch +" ");
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        });

        t1.start();
        t2.start();


    }





}
