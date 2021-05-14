package org.ajisun.coding.concurrency.lock.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc
 * @Date: 2020/7/16
 * @author: ajisun
 *
 */
public class LockSupportDemo1 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " start!");
            //线程等待
            LockSupport.park();
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " 被唤醒!");
        });

        t1.setName("t1");
        t1.start();
        TimeUnit.SECONDS.sleep(5);
        //唤醒
        LockSupport.unpark(t1);
        System.out.println(System.currentTimeMillis() + ",LockSupport.unpark();执行完毕");


    }


}
