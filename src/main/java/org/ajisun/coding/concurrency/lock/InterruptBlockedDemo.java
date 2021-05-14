package org.ajisun.coding.concurrency.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/11
 * @author: ajisun
 *
 */
public class InterruptBlockedDemo {

    /**
     * 阻塞状态下的线程中断
     * 1。main方法中调用了t.interrupt()方法，此时线程t内部的中断标志会置为true
     * 2。 然后会触发run()方法内部的InterruptedException异常，所以运行结果中有异常输出，上面说了，
     *  当触发InterruptedException异常时候，线程内部的中断标志又会被清除（变为false），
     *  所以在catch中又调用了this.interrupt();一次，将中断标志置为false
     * 3。 run()方法中通过this.isInterrupted()来获取线程的中断标志，退出循环（break）
     *
     */

    public static class T1 extends Thread{

        @Override
        public void run(){

            while(true){

//循环处理业务
//下面模拟阻塞代码
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.interrupt();
                }

                if (this.isInterrupted()){
                    break;
                }

            }


        }


    }


    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        t1.start();

        TimeUnit.SECONDS.sleep(10);
        t1.interrupt();


    }

}
