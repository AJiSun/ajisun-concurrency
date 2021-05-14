package org.ajisun.coding.concurrency.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 给lists添加volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，该怎么做呢？
 *
 * 这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方法，必须要保证t2先执行，也就是首先让t2监听才可以
 *
 * 阅读下面的程序，并分析输出结果
 * 可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接收到通知而退出
 * 想想这是为什么？
 *
 * notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行
 * 整个通信过程比较繁琐
 * @Copyright (c) 2021. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2021/1/22
 * @author: ajisun
 * @Email: Ajisun
 */
public class NotifyFreeLockDemo {


    static  List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }



    public static void main(String[] args) {

        NotifyFreeLockDemo demo = new NotifyFreeLockDemo();
        final Object object = new Object();

        new Thread(() -> {
            System.out.println("t2启动");
            synchronized (object) {
                if (lists.size() != 5) {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");
                object.notify();
            }

        }, "T2").start();

        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(()->{
            System.out.println("t1启动");
            synchronized (object){
                for (int i = 0; i < 10; i++) {
                    lists.add(i);
                    System.out.println("t1:"+lists.size());
                    if (lists.size()==5){
                        object.notify();
                        try {
                            //释放锁，让t2得以执行
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        },"T1").start();

    }

}
