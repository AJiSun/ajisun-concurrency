package org.ajisun.coding.concurrency.lock.juc.future;

import java.util.concurrent.*;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.future
 * @Date: 2020/8/29
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = new ThreadPoolExecutor(1,
                1,
                20L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1)
                );

        Future<Integer> result = executorService.submit(()->{
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName()+",start!");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName()+",end!");
            return 10;
        });

        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName());
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",结果：" + result.get());


    }

}
