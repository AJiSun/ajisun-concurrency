package org.ajisun.coding.concurrency.lock;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/5
 * @author: ajisun
 *
 */
public class VolatileDemo {

    public volatile static boolean flag = true;

    /**
     * 问：为什么while循环中加一条打印语句，即使不用volatile修饰，程序也结束了
     * 答：print内部有synchronized，进入synchronized时会清空工作内存中所有数据副本
     */

    public static class T1 extends Thread {

        public  T1(String name){
            super(name);
        }

        @Override
        public void run(){
            System.out.println("线程 "+this.getName()+" in");
            while (flag){
//                System.out.println("====");
                ;
            }
            System.out.println("线程 "+this.getName()+" 停止了");
        }


    }

    public static void main(String[] args) throws InterruptedException {

        new T1("t1").start();
        System.out.println("开始睡觉");
        Thread.sleep(1000);
        System.out.println("醒来了");
        flag = false;
        System.out.println("结束while");

    }


}
