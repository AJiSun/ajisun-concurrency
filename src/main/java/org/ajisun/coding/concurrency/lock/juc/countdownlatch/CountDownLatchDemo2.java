package org.ajisun.coding.concurrency.lock.juc.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 代码中创建了计数器为2的 CountDownLatch，主线程中调用 countDownLatch.await();
 * 会让主线程等待，t1、t2线程中模拟执行耗时操作，最终在finally中调用了 countDownLatch.countDown();
 * 此方法每调用一次，CountDownLatch内部计数器会减1，当计数器变为0的时候，主线程中的await()会返回，然后继续执行。
 * 注意：上面的 countDown()这个是必须要执行的方法，所以放在finally中执行
 *
 * countDownLatch.await(2,TimeUnit.SECONDS);
 * 表示最多等2秒，不管计数器是否为0，await方法都会返回，若等待时间内，计数器变为0了，立即返回true，否则超时后返回false。
 *
 * countDownLatch.await();
 * 表示一直等待，直到最后一个线程执行结束
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.countdownlatch
 * @Date: 2020/7/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class CountDownLatchDemo2 {

    public static class T extends Thread {

        CountDownLatch countDownLatch;
        int sleepSeconds;

        public T(String name, int sleepSeconds, CountDownLatch countDownLatch) {
            super(name);
            this.sleepSeconds = sleepSeconds;
            this.countDownLatch = countDownLatch;
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
            } finally {
                countDownLatch.countDown();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "," + thread.getName() + "处理完毕，耗时：" + (endTime - startTime));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        long startTime = System.currentTimeMillis();

        T t1 = new T("t1线程", 2, countDownLatch);
        t1.start();

        T t2 = new T("t2线程", 5, countDownLatch);
        t2.start();

//        countDownLatch.await();
        boolean flag = countDownLatch.await(2,TimeUnit.SECONDS);

        long endTime = System.currentTimeMillis();
        System.out.println("主线程结束，总耗时：" + (endTime - startTime)+"等待结果:"+flag);


    }

}
