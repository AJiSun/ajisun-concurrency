package org.ajisun.coding.concurrency.lock.juc.countdownlatch;

import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.countdownlatch
 * @Date: 2020/7/27
 * @author: ajisun
 */
public class DemoJoin1 {

    public static class T extends Thread {
        int sleepSeconds;

        public T(String name, int sleepSeconds) {
            super(name);
            this.sleepSeconds = sleepSeconds;
        }
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            long startTime = System.currentTimeMillis();
            System.out.println(startTime + "," + thread.getName() + "开始处理");
            try {
                TimeUnit.SECONDS.sleep(sleepSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "," + thread.getName() + "处理结束，耗时：" + (endTime - startTime));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        T t1 = new T("t1线程", 2);
        t1.start();

        T t2 = new T("t2线程", 5);
        t2.start();

        t1.join();
        t2.join();
        long endTime = System.currentTimeMillis();
        System.out.println("主线程结束，总耗时：" + (endTime - startTime));
    }


}
