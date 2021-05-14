package org.ajisun.coding.concurrency.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/8
 * @author: ajisun
 *
 */
public class ThreadGroupDemo {


    public static class R1 implements Runnable{

        public void run() {
            System.out.println("threadName: "+Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group = new ThreadGroup("thread-group-1");

        Thread t1 = new Thread(group,new R1(),"t1");
        Thread t2 = new Thread(group,new R1(),"t2");
        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("活动线程数:"+group.activeCount());
        System.out.println("活动线程组:"+group.activeGroupCount());
        System.out.println("线程组名称:"+group.getName());
        System.out.println("获取父线程组名称:"+group.getParent().getName());

        System.out.println("======================");

        ThreadGroup group2 = new ThreadGroup(group,"thread-group-2");

        Thread t3 = new Thread(group2,new R1(),"t3");
        Thread t4 = new Thread(group2,new R1(),"t4");
        t3.start();
        t4.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("thread-group-2活动线程数:"+group2.activeCount());
        System.out.println("thread-group-2活动线程组:"+group2.activeGroupCount());
        System.out.println("thread-group-2线程组名称:"+group2.getName());
        System.out.println("thread-group-2获取父线程组名称:"+group2.getParent().getName());


        System.out.println("======================");

        System.out.println("thread-group-1活动线程数:"+group.activeCount());
        System.out.println("thread-group-1活动线程组:"+group.activeGroupCount());
        System.out.println("======================");

        group.list();

        System.out.println("======================");

        group2.list();

    }


}
