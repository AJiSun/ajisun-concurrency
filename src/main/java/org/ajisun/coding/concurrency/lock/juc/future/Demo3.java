package org.ajisun.coding.concurrency.lock.juc.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.future
 * @Date: 2020/8/30
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> futureTask = new FutureTask<Integer>(()->{
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName()+",start!");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName()+",end!");
            return 10;
        });

        System.out.println(System.currentTimeMillis()+","+Thread.currentThread().getName());
        new Thread(futureTask).start();
        System.out.println(System.currentTimeMillis()+","+Thread.currentThread().getName());
        System.out.println(System.currentTimeMillis()+","+Thread.currentThread().getName()+",结果:"+futureTask.get());

       System.out.println("=====");

    }



}
