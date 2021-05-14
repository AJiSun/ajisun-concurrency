package org.ajisun.coding.concurrency.lock.juc.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.countdownlatch
 * @Date: 2020/7/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class CountDownLatchDemo3 {

    public static class T extends Thread {

        int runCostSeconds;
        CountDownLatch commanderCd;
        CountDownLatch countDown;

        public T(String name, int runCostSeconds, CountDownLatch commanderCd, CountDownLatch countDown) {
            super(name);
            this.runCostSeconds = runCostSeconds;
            this.countDown = countDown;
            this.commanderCd = commanderCd;
        }

        @Override
        public void run() {
            try {
//                等待发枪指令
                commanderCd.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread thread = Thread.currentThread();
            long startTime = System.currentTimeMillis();
            System.out.println(startTime + "" + thread.getName() + "开始跑");

            try {
                TimeUnit.SECONDS.sleep(runCostSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDown.countDown();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "," + thread.getName() + " 跑步结束,耗时：" + (endTime - startTime));
        }
    }

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch command = new CountDownLatch(1);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        long startTime = System.currentTimeMillis();

        T t1 = new T("小李", 3, command, countDownLatch);
        t1.start();

        T t2 = new T("小汪", 7, command, countDownLatch);
        t2.start();

        T t3 = new T("小马", 10, command, countDownLatch);
        t3.start();

        //主线程休眠5秒,模拟指令员准备发枪耗时操作
        TimeUnit.SECONDS.sleep(5);
        System.out.println("枪已响，开跑");
        command.countDown();
        countDownLatch.await();

        long endTime = System.currentTimeMillis();
        System.out.println(endTime + "," + Thread.currentThread().getName() + " 所有人跑步结束,主线程耗时：" + (endTime - startTime));
    }

}
