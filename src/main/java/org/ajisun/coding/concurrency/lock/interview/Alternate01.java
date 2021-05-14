package org.ajisun.coding.concurrency.lock.interview;

import java.util.concurrent.locks.LockSupport;

/**
 * 交替输出1A2B3C4D
 *
 * @Copyright (c) 2021. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.interview
 * @Date: 2021/1/25
 * @author: ajisun
 * @Email: Ajisun
 */
public class Alternate01 {

    static Thread t1 = null;
    static Thread t2 = null;

    public static void main(String[] args) {
        char[] nums = "123456789".toCharArray();
        char[] ch = "ABCDEFgh".toCharArray();

        t1 = new Thread(() -> {
            for (char c : nums) {
                System.out.print(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (char c : ch) {
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(t1);
            }
        }, "t2");

        t1.start();
        t2.start();
    }


}
