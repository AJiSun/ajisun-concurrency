package org.ajisun.coding.concurrency.lock.juc;

import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc
 * @Date: 2020/7/13
 * @author: ajisun
 *
 */
public class synchronizedWaitNotify13 {

    static Object object = new Object();

    public static class T1 extends Thread{

        @Override
        public void run(){
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "准备获取锁!");
            synchronized (object){
                System.out.println(System.currentTimeMillis() + "," + this.getName() + "获取锁成功!");

                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "释放锁成功!");

        }

    }


    public static class T2 extends Thread{

        @Override
        public void run(){
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "准备获取锁!");

            synchronized (object){
                object.notify();
                System.out.println(System.currentTimeMillis() + "," + this.getName() + " notify!");

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + "," + this.getName() + "准备释放锁!");
            }
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "释放锁成功!");
        }

    }

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        t1.setName("t1");
        t1.start();

        TimeUnit.SECONDS.sleep(5);

        T2 t2 = new T2();
        t2.setName("t2");
        t2.start();



    }



}
