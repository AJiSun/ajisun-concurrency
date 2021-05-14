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
public class Demo4 {

    static class Task implements Runnable {
        String name;
        public Task(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "处理" + this.name);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        @Override
        public String toString() {
            return "Task{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                Executors.defaultThreadFactory(),
                (r, executor1) -> {
                    //自定义饱和策略
                    //记录一下无法处理的任务
                    System.out.println("无法处理的任务：" + r.toString());
                }) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(System.currentTimeMillis() + "," + t.getName() + ",开始执行任务:" + r.toString());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",任务:" + r.toString() + "，执行完毕!");
            }

            @Override
            protected void terminated() {
                System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，关闭线程池!");
            }

        };
        for (int i = 0; i < 10; i++) {
            executor.execute(new Task("hips任务-" + i));
        }
        TimeUnit.SECONDS.sleep(1);
        executor.shutdown();
    }


}
