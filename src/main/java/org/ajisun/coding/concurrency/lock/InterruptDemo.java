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
public class InterruptDemo {

    /**
     * 1.字段控制线程中断
     * 2. 线程自带的中断标志控制
     */
    public volatile static boolean exit = false;


    public static class T1 extends Thread {
        @Override
        public void run() {
            while (true) {

//                1
                if (exit) {
                    break;
                }

//                2
                if (this.isInterrupted()){
                    break;
                }


            }

        }
    }


    public static void setExit() {
        exit = true;
    }

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        t1.start();
        TimeUnit.SECONDS.sleep(3);

//        1
        setExit();
//        2
        t1.interrupt();

    }


}
