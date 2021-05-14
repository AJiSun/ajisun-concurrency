package org.ajisun.coding.concurrency.lock.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.unsafe
 * @Date: 2020/9/6
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo2 {

    static Unsafe unsafe;
    static int count;
    //count在Demo2.class对象中的地址偏移量
    static long countOffset;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            Field countField = Demo2.class.getDeclaredField("count");
            countOffset = unsafe.staticFieldOffset(countField);
        } catch (NoSuchFieldException e) { e.printStackTrace();
        } catch (IllegalAccessException e) { e.printStackTrace();
        }
    }

    public static void request() throws InterruptedException {

        TimeUnit.MICROSECONDS.sleep(10L);
        unsafe.getAndAddInt(Demo2.class, countOffset, 1);
    }

    public static void main(String[] args) throws InterruptedException {
        long starTime = System.currentTimeMillis();
        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(threadSize));
        for (int i = 0; i < threadSize; i++) {
            executor.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        request();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                countDownLatch.countDown();
            });
        }
        executor.shutdown();
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "，耗时：" + (endTime - starTime) + ",count=" + count);
    }

}
