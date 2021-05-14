package org.ajisun.coding.concurrency.lock;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/9
 * @author: ajisun
 *
 */
public class SafeThreadDemo {

    private static int num=0;

    public static void m1(){
//        System.out.println(Thread.currentThread().getName()+":"+num);
        for (int i=0;i<1000;i++){
//            System.out.println(Thread.currentThread().getName()+":"+num);
            num++;
        }
    }

    public static class T1 extends Thread{

        @Override
        public void run(){
            SafeThreadDemo.m1();
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
        System.out.println(SafeThreadDemo.num);

    }




}
