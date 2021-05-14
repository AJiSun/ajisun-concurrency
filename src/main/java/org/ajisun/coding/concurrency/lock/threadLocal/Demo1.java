package org.ajisun.coding.concurrency.lock.threadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 如果存在子线程，是不能traceIdKD.get()的
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.threadLocal
 * @Date: 2020/9/19
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo1 {

    //创建一个操作Thread中存放请求任务追踪id口袋的对象
    static ThreadLocal<String> traceIdKD = new ThreadLocal<>();
    static AtomicInteger threadIndex = new AtomicInteger(1);


    static ThreadPoolExecutor disposeRequestExecutor = new ThreadPoolExecutor(3, 3, 60,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>(), r -> {
        Thread thread = new Thread(r);
        thread.setName("disposeRequestThread-" + threadIndex.getAndIncrement());
        return thread;
    });

    public static void log(String msg) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        String traceId = traceIdKD.get();
        System.out.println("****" + System.currentTimeMillis() + "[traceId:" + traceId + "],[线程:" +
                Thread.currentThread().getName() + "]," + stack[1] + ":" + msg);
    }

    //模拟controller
    public static void controller(List<String> dataList) {
        log("接受请求");
        service(dataList);
    }

    //模拟service
    public static void service(List<String> dataList) {
        log("执行业务");
        dao(dataList);
    }

    //模拟dao
    public static void dao(List<String> dataList) {
        log("执行数据库操作");
        //模拟插入数据
        for (String s : dataList) {
            log("插入数据" + s + "成功");
        }
    }

    public static void main(String[] args) {
        //需要插入的数据
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dataList.add("数据" + i);
        }

        for (int i = 0; i < 5; i++) {
            String traceId = String.valueOf(i);
            disposeRequestExecutor.execute(() -> {
                traceIdKD.set(traceId);
                try {
                    controller(dataList);
                } finally {
                    traceIdKD.remove();
                }
            });
        }
        disposeRequestExecutor.shutdown();

    }

}
