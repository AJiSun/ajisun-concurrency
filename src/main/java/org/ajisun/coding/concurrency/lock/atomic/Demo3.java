package org.ajisun.coding.concurrency.lock.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 解决多线程ABA问题，使用AtomicStampedReference
 * 有一家蛋糕店，为了挽留客户，决定为贵宾卡客户一次性赠送20元，刺激客户充值和消费，但条件是，每一位客户只能被赠送一次，现在我们用AtomicReference来实现这个功能
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.atomic
 * @Date: 2020/9/16
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo3 {
    //初始金额
    static int accountMoney = 19;
    //用于对账户余额做原子操作
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(accountMoney, 0);

    // 模拟两个线程充值
    static void recharge() {
        for (int i = 0; i < 2; i++) {
            int stamp = money.getStamp();
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    Integer m = money.getReference();
                    if (m == accountMoney) {
                        if (money.compareAndSet(m, m + 20, stamp, stamp + 1)) {
                            System.out.println("当前时间戳：" + money.getStamp() + ",当前余额：" + m + "，小于20，充值20元成功，余额：" + money.getReference() + "元");
                        }
                    }
                    //休眠100ms
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }).start();
        }
    }

    // 用户消费
    static void consume() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            Integer m = money.getReference();
            int stamp = money.getStamp();
            if (m > 20) {
                if (money.compareAndSet(m, m - 20, stamp, stamp + 1)) {
                    System.out.println("当前时间戳：" + money.getStamp() + ",当前余额：" + m + "，大于10，成功消费10元，余额：" + money.getReference() + "元");
                }
            }
            //休眠50ms
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        recharge();
        consume();
    }


}
