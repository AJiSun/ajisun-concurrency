package org.ajisun.coding.concurrency.lock.limit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.limit
 * @Date: 2020/9/23
 * @author: ajisun
 * @Email: Ajisun
 */
public class RateLimitDemo {

    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(5);//设置QPS为5
        for (int i = 0; i < 10; i++) {
            rateLimiter.acquire();
            System.out.println(System.currentTimeMillis());
        }
        System.out.println("----------");
        //可以随时调整速率，我们将qps调整为10
        rateLimiter.setRate(10);
        for (int i = 0; i < 10; i++) {
            rateLimiter.acquire();
            System.out.println(System.currentTimeMillis());
        }
    }
}
