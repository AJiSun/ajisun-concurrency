package org.ajisun.coding.concurrency.lock.count;

import java.util.concurrent.CountDownLatch;

/**
 * 来模拟50个线程，每个线程对计数器递增100万次，最终结果应该是5000万。
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.count
 * @Date: 2020/9/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class SyncCount {

    static int count =0 ;
    public static synchronized  void incr(){
        count ++;
    }

    private static void m1() throws InterruptedException{
        long t1 = System.currentTimeMillis();
        int threadCount = 50;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i=0;i<threadCount;i++){
            new Thread(()->{
                try {
                    for (int j = 0; j < 1000000; j++) {
                        incr();
                    }
                }finally {
                    countDownLatch.countDown();
                }

            }).start();
        }
        countDownLatch.await();
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("结果：%s,耗时(ms)：%s", count, (t2 - t1)));
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            count = 0;
            m1();
        }
    }


}
