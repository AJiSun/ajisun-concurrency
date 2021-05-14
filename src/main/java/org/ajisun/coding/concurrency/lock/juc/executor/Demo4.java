package org.ajisun.coding.concurrency.lock.juc.executor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *测试定时任务出现异常
 *
 * 任务中有个10/0的操作，会触发异常，发生异常之后没有任何现象，被ScheduledExecutorService内部给吞掉了
 * 这个任务再也不会执行了，scheduledFuture.isDone()输出true，表示这个任务已经结束了，再也不会被执行了
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.executor
 * @Date: 2020/8/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo4 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.currentTimeMillis());
        //任务执行计数器
        AtomicInteger count = new AtomicInteger(1);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10);
        ScheduledFuture<?> future = executorService.scheduleWithFixedDelay(()->{

            int currCount = count.getAndIncrement();
            System.out.println(Thread.currentThread().getName());
            System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "开始执行");
            System.out.println(10 / 0);
            System.out.println(System.currentTimeMillis() + "第" + currCount + "次" + "执行结束");

        },1,1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(5);
        System.out.println(future.isCancelled());
        System.out.println(future.isDone());

    }


}
