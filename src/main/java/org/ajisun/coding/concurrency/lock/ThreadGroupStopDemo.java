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
public class ThreadGroupStopDemo {


    public static class R1 implements Runnable{
        public void run() {
            Thread thread = Thread.currentThread();
            System.out.println("所属线程组:"+thread.getThreadGroup().getName()+",线程名称:"+thread.getName());

            while (!thread.isInterrupted()){
                ;
            }

            System.out.println("线程："+thread.getName()+"停止了");

        }
    }


    public static void main(String[] args) throws InterruptedException {

        ThreadGroup group1 = new ThreadGroup("thread-group-1");

        Thread t1 = new Thread(group1,new R1(),"t1");
        Thread t2 = new Thread(group1,new R1(),"t2");
        t1.start();
        t2.start();

        ThreadGroup group2 = new ThreadGroup(group1,"thread-group-1");

        Thread t3 = new Thread(group2,new R1(),"t3");
        Thread t4 = new Thread(group2,new R1(),"t4");
        t3.start();
        t4.start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("======================thread-group-1");
        group1.list();

        System.out.println("停止线程租");
        group1.interrupt();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("=====================线程停止之后");

        group1.list();




    }



}
