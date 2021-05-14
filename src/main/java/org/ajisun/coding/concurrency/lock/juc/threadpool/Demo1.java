package org.ajisun.coding.concurrency.lock.juc.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.threadpool
 * @Date: 2020/8/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo1 {

    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3,
            5,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) {

        for (int i=0;i<10;i++){

            int j = i;
            poolExecutor.execute(() ->{
                try {
                    TimeUnit.SECONDS.sleep(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "任务"+j + "处理完毕");
            });

        }

        poolExecutor.shutdown();

    }


}
