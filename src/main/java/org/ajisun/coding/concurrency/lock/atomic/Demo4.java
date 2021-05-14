package org.ajisun.coding.concurrency.lock.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，要求只能初始化一次
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.atomic
 * @Date: 2020/9/17
 * @author: ajisun
 * @Email: ajisun
 */
public class Demo4 {

    static Demo4 demo4 = new Demo4();
    /**
     *isInit用来标注是否被初始化过
     */
    volatile Boolean isInit = Boolean.FALSE;
    AtomicReferenceFieldUpdater<Demo4, Boolean> updater = AtomicReferenceFieldUpdater.newUpdater(Demo4.class, Boolean.class, "isInit");

    /**
     * 模拟初始化工作
     *
     * @throws InterruptedException
     */
    public void init() throws InterruptedException {
        //isInit为false的时候，才进行初始化，并将isInit采用原子操作置为true
        if (updater.compareAndSet(demo4, Boolean.FALSE, Boolean.TRUE)) {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，开始初始化!");
            //模拟休眠3秒
            TimeUnit.SECONDS.sleep(3);
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，初始化完毕!");
        } else {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，有其他线程已经执行了初始化!");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    demo4.init();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
