package org.ajisun.coding.concurrency.lock.juc.executor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.executor
 * @Date: 2020/8/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo5 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.currentTimeMillis());
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        //任务执行计数器
        AtomicInteger count = new AtomicInteger(1);
        ScheduledFuture<?> future = executorService.scheduleWithFixedDelay(()->{
            int currCount = count.getAndIncrement();
            System.out.println(Thread.currentThread().getName());
            System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "开始执行");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "执行结束");

        },1,1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(5);
//        是否给任务发送中断信号
        future.cancel(false);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("任务是否被取消："+future.isCancelled());
        System.out.println("任务是否已完成："+future.isDone());
    }

}
