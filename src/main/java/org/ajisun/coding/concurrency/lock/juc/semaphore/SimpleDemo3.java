package org.ajisun.coding.concurrency.lock.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.semaphore
 * @Date: 2020/7/26
 * @author: ajisun
 *
 */
public class SimpleDemo3 {

    private static Semaphore semaphore = new Semaphore(3);

    public static class T extends Thread{

        public T(String name){
            super(name);
        }

        @Override
        public void run(){
            Thread thread = Thread.currentThread();
            boolean acquireSuccess = false;

            try {
                //尝试在1秒内获取许可，获取成功返回true，否则返回false
                System.out.println(System.currentTimeMillis() + "," + thread.getName() + ",尝试获取许可,当前可用许可数量:" + semaphore.availablePermits());
                acquireSuccess = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                //获取成功执行业务代码
                if (acquireSuccess) {
                    System.out.println(System.currentTimeMillis() + "," + thread.getName() + ",获取许可成功,当前可用许可数量:" + semaphore.availablePermits());
                    //休眠5秒
                    TimeUnit.SECONDS.sleep(5);
                } else {
                    System.out.println(System.currentTimeMillis() + "," + thread.getName() + ",获取许可失败,当前可用许可数量:" + semaphore.availablePermits());
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                if (acquireSuccess) {
                    semaphore.release();
                }
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        T t1 = new T("t1");
        t1.start();
        //休眠1秒
        TimeUnit.SECONDS.sleep(1);
        T t2 = new T("t2");
        t2.start();
        //休眠1秒
        TimeUnit.SECONDS.sleep(1);
        T t3 = new T("t3");
        t3.start();
    }

}
