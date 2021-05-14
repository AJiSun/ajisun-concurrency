package org.ajisun.coding.concurrency.lock.atomic;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.atomic
 * @Date: 2020/9/13
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo1 {

    static AtomicIntegerArray pageRequest = new AtomicIntegerArray(new int[10]);

    public static void request(int page) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(5);
        //pageCountIndex为pageCount数组的下标，表示页面对应数组中的位置
        int pageCountIndex = page - 1;
        pageRequest.incrementAndGet(pageCountIndex);
    }
    public static void main(String[] args) throws InterruptedException {
        long starTime = System.currentTimeMillis();
        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 11,
                1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
        for (int i = 0; i < threadSize; i++) {
            executor.execute(() -> {
                try {
                    for (int page = 1; page <= 10; page++) {
                        for (int j = 0; j < 10; j++) {
                            request(page);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        executor.shutdown();
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "，耗时：" + (endTime - starTime));
        for (int pageIndex = 0; pageIndex < 10; pageIndex++) {
            System.out.println("第" + (pageIndex + 1) + "个页面访问次数为" + pageRequest.get(pageIndex));
        }
    }
}
