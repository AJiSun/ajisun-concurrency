package org.ajisun.coding.concurrency.lock.cas;

import java.util.concurrent.*;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.cas
 * @Date: 2020/8/31
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo1 {

    static int count = 0;

    //    模拟用户请求
    public static void request() throws InterruptedException {

        TimeUnit.MICROSECONDS.sleep(5L);
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        long starTime = System.currentTimeMillis();
        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
                2,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));

        for (int i = 0; i < threadSize; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        request();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        threadPoolExecutor.shutdown();
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "，耗时：" + (endTime - starTime) + ",count=" + count);
    }

}
