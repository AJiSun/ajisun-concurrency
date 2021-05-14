package org.ajisun.coding.concurrency.lock.juc.threadpool;

import java.util.concurrent.*;

/**
 * PriorityBlockingQueue优先级队列的线程池
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.threadpool
 * @Date: 2020/8/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo2 {

    static class Task implements Runnable,Comparable<Task>{

        private int i;
        private String name;

        public Task(int i, String name) {
            this.i = i;
            this.name = name;
        }

        @Override
        public int compareTo(Task o) {
            return Integer.compare(o.i,this.i);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "处理" + this.name);
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(1,1,60L,
                TimeUnit.SECONDS,new PriorityBlockingQueue<>());
        for (int i=0;i<10;i++){
            executorService.execute(new Task(i,"任务" + i));
        }

        for (int i=100;i>=90;i--){
            executorService.execute(new Task(i,"任务"+i));
        }

        executorService.shutdown();

    }

}
