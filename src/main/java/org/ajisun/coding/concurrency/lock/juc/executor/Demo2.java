package org.ajisun.coding.concurrency.lock.juc.executor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * scheduleAtFixedRate 固定频率执行任务
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.executor
 * @Date: 2020/8/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo2 {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        AtomicInteger count = new AtomicInteger(1);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10);
        executorService.scheduleAtFixedRate(()->{

            int currCount = count.getAndIncrement();
            System.out.println(Thread.currentThread().getName());
            System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "开始执行");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "执行结束");

        },1,2, TimeUnit.SECONDS);


//        executorService.shutdown();

    }


}
