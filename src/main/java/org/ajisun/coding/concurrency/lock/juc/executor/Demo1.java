package org.ajisun.coding.concurrency.lock.juc.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * schedule 定时任务
 * 延迟执行
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.executor
 * @Date: 2020/8/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo1 {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10);
        scheduledExecutorService.schedule(()->{
            System.out.println(System.currentTimeMillis() + "开始执行");
            //模拟任务耗时
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() + "执行结束");

        },2, TimeUnit.SECONDS);

        scheduledExecutorService.shutdown();
    }

}
