package org.ajisun.coding.concurrency.lock.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量的简单使用
 * 代码中new Semaphore(2)创建了许可数量为2的信号量，每个线程获取1个许可，同时允许两个线程获取许可
 * ，从输出中也可以看出，同时有两个线程可以获取许可，其他线程需要等待已获取许可的线程释放许可之后才能运行。
 * 为获取到许可的线程会阻塞在acquire()方法上，直到获取到许可才能继续。
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.semaphore
 * @Date: 2020/7/21
 * @author: ajisun
 *
 */
public class SimpleDemo1 {
    private static Semaphore semaphore = new Semaphore(2);

    public static class T extends Thread{
        public T(String name){
            super(name);
        }


        @Override
        public void run(){

            Thread thread =  Thread.currentThread();

            try {
                semaphore.acquire();
                System.out.println(System.currentTimeMillis() + "," + thread.getName() + ",获取许可!");
                TimeUnit.SECONDS.sleep(3);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
                System.out.println(System.currentTimeMillis() + "," + thread.getName() + ",释放许可!");
            }

        }

    }


    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            new T("线程"+i).start();
        }
    }

}
