package org.ajisun.coding.concurrency.lock.count;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * AtomicLong内部采用CAS的方式实现，并发量大的情况下，CAS失败率比较高，导致性能比synchronized还低一些。
 * 并发量不是太大的情况下，CAS性能还是可以的。
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.count
 * @Date: 2020/9/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class AtomicCount {

    static AtomicLong atomicLong = new AtomicLong(0);

    public static void incr() {
        atomicLong.incrementAndGet();
    }

    private static void m1() throws InterruptedException {
        long t1 = System.currentTimeMillis();
        int threadCount = 50;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000000; j++) {
                        incr();
                    }

                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("结果：%s,耗时(ms)：%s", atomicLong, (t2 - t1)));
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            atomicLong.set(0);
            m1();
        }
    }


}
