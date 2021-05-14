package org.ajisun.coding.concurrency.lock.juc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc
 * @Date: 2020/7/13
 * @author: ajisun
 *
 */
public class ConditionBlockingQueueDemo<E> {
    int size;
    ReentrantLock lock = new ReentrantLock();

    /**
     * 队列底层实现
     */
    LinkedList<E> list = new LinkedList<E>();

    /**
     * 队列满时的等待条件
     */
    Condition notFull = lock.newCondition();
    /**
     * 队列空时的等待条件
     */
    Condition notEmpty = lock.newCondition();

    public ConditionBlockingQueueDemo(int size){
        this.size = size;
    }


    public void enqueue(E e) throws InterruptedException {
        lock.lock();
        try {
           //队列已满,在notFull条件上等待
            while (list.size() == size){
                notFull.await();
            }
            //入队:加入链表末尾
            list.add(e);
            System.out.println("入队：" + e);

            //通知在notEmpty条件上等待的线程
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }


    public E dequeue() throws InterruptedException {
        E e;
        lock.lock();
        try {
            //队列为空,在notEmpty条件上等待
            while (list.size() == 0){
                notEmpty.await();
            }
            //出队:移除链表首元素
            e = list.removeFirst();
            System.out.println("出队：" + e);
            notFull.signal();//通知在notFull条件上等待的线程
            return e;
        } finally {
            lock.unlock();
        }
    }



    public static void main(String[] args) {
        final ConditionBlockingQueueDemo<Integer> queue = new ConditionBlockingQueueDemo<Integer>(2);
        for (int i = 0; i < 10; i++) {
            final int data = i;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        queue.enqueue(data);
                    } catch (InterruptedException e) {
                    }
                }
            }).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Integer data = queue.dequeue();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


}
