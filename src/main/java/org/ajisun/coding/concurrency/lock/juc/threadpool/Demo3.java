package org.ajisun.coding.concurrency.lock.juc.threadpool;

import java.util.concurrent.*;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.threadpool
 * @Date: 2020/8/27
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo3 {


    static class Task implements Runnable{
        String name;
        int time;
        public Task(String name,int time){
            this.name = name;
            this.time = time;
        }

        @Override
        public void run() {
//            System.out.println(Thread.currentThread().getName() + "开始处理" + this.name);
            try {
//                Long ti = (time*2L);
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "结束处理" + this.name);
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


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8,9,
                10L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                r->{
                    return new Thread(r, "hipsplat-initEmployee-elastic-data");
                });

        for (int i = 0; i < 20; i++) {
            executor.execute(new Task("任务-" + i,i));
        }
        executor.shutdown();

    }


}
