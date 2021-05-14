package org.ajisun.coding.concurrency.lock.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 需要正确释放锁才可以
 *主线程中对t2、t3发送中断信号之后，acquire()方法会触发InterruptedException异常，t2、t3最终没有获取到许可，
 * 但是他们都执行了finally中的释放许可的操作，最后导致许可数量变为了2，导致许可数量增加了。
 * 所以程序中释放许可的方式有问题。需要改进一下，获取许可成功才去释放锁。
 *
 * 程序中增加了一个变量acquireSuccess用来标记获取许可是否成功，在finally中根据这个变量是否为true，来确定是否释放许可。
 *
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.semaphore
 * @Date: 2020/7/23
 * @author: ajisun
 *
 */
public class SimpleDemo2 {
    private static Semaphore semaphore = new Semaphore(1);

    public static class T extends Thread{

        public T(String name){
            super(name);
        }

        @Override
        public void run(){
            Thread thread = Thread.currentThread();
            //获取许可是否成功
            boolean acquireSuccess = false;
            try {
                semaphore.acquire();
                acquireSuccess = true;
                System.out.println(System.currentTimeMillis() + "," + thread.getName() + ",获取许可,当前可用许可数量:" + semaphore.availablePermits());
                //休眠100秒
                TimeUnit.SECONDS.sleep(10);
                System.out.println(System.currentTimeMillis() + "," + thread.getName() + ",运行结束!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if (acquireSuccess){
                    semaphore.release();
                }
            }
            System.out.println(System.currentTimeMillis() + "," + thread.getName() + ",当前可用许可数量:" + semaphore.availablePermits());
        }
    }
    public static void main(String[] args) throws InterruptedException {
        T t1 = new T("线程t1");
        t1.start();
        //休眠1秒
        TimeUnit.SECONDS.sleep(1);
        T t2 = new T("线程t2");
        t2.start();
        //休眠1秒
        TimeUnit.SECONDS.sleep(1);
        T t3 = new T("线程t3");
        t3.start();

        //给t2和t3发送中断信号
        t2.interrupt();
        t3.interrupt();

    }

}
