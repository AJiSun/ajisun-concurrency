package org.ajisun.coding.concurrency.lock.threadLocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 使用InheritableThreadLocal解决Demo2子线程中无法输出traceId的问题，只需要将上一个示例代码中的ThreadLocal替换成InheritableThreadLocal即可，代码如下：
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.threadLocal
 * @Date: 2020/9/19
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo3 {

    // 创建一个操作Thread中存放请求任务追踪id口袋的对象,子线程可以继承父线程中内容

    static InheritableThreadLocal<String> traceIdKD = new InheritableThreadLocal<>();
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
        CountDownLatch countDownLatch = new CountDownLatch(dataList.size());
        log("执行数据库操作");
        String threadName = Thread.currentThread().getName();
        //模拟插入数据
        for (String s : dataList) {
            new Thread(() -> {
                try {
                    //模拟数据库操作耗时100毫秒
                    TimeUnit.MILLISECONDS.sleep(100);
                    log("插入数据" + s + "成功,主线程：" + threadName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }

            }).start();

            log("插入数据" + s + "成功");
        }
        //等待上面的dataList处理完毕
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
