package org.ajisun.coding.concurrency.lock.cas;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.cas
 * @Date: 2020/9/2
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo3 {

    static AtomicInteger count = new AtomicInteger();

    public static void request() throws InterruptedException {
        TimeUnit.MICROSECONDS.sleep(5L);
        count.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {

        long starTime = System.currentTimeMillis();
        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 2,
                1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(threadSize));

        for (int i = 0; i < threadSize; i++) {
            poolExecutor.execute(() -> {


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

        poolExecutor.shutdown();
        countDownLatch.await();

        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "，耗时：" + (endTime - starTime) + ",count=" + count);


    }


}
