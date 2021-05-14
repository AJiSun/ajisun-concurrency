package org.ajisun.coding.concurrency.lock;

import java.util.concurrent.Exchanger;

/**
 * @Copyright (c) 2021. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2021/1/22
 * @author: ajisun
 * @Email: Ajisun
 */
public class ExchangerDemo {

    static Exchanger<String> exchanger = new Exchanger<>();


    public static void main(String[] args) {
        new Thread(()->{
            String a = "加加加";
            try {
                a = exchanger.exchange(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"======"+a);

        },"T1").start();

        new Thread(()->{
            String a = "减减减";
            try {
                a = exchanger.exchange(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"======"+a);

        },"T2").start();

    }

}
