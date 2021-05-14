package org.ajisun.coding.concurrency.lock.juc.countdownlatch;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.countdownlatch
 * @Date: 2020/7/28
 * @author: ajisun
 * @Email: Ajisun
 */
public class TaskDisposeUtils {

    //并行线程数
    public static final int POOL_SIZE;

    static {
        int cpu = Runtime.getRuntime().availableProcessors();
        System.out.println("cpu:" + cpu);
        POOL_SIZE = Integer.max(cpu, 5);
    }

    /**
     * 并行处理，并等待结束
     *
     * @param taskList 任务列表
     * @param consumer 消费者
     * @param <T>
     */
    public static <T> void dispose(List<T> taskList, Consumer<T> consumer) {
        dispose(true, POOL_SIZE, taskList, consumer);
    }

    /**
     * 并行处理，并等待结束
     *
     * @param moreThread 是否多线程执行
     * @param poolSize   线程池大小
     * @param taskList   任务列表
     * @param consumer   消费者
     * @param <T>
     */
    public static <T> void dispose(boolean moreThread, int poolSize, List<T> taskList, Consumer<T> consumer) {
        if (CollectionUtils.isEmpty(taskList)) {
            return;
        }

        if (moreThread && poolSize > 1) {
            poolSize = Math.max(poolSize, taskList.size());
            ExecutorService executorService = null;
            try {
                executorService = Executors.newFixedThreadPool(poolSize);
                CountDownLatch countDownLatch = new CountDownLatch(taskList.size());
                for (T item : taskList) {
                    executorService.execute(() -> {
                        try {
                            consumer.accept(item);
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (executorService != null) {
                    executorService.shutdown();
                }
            }
        } else {
            for (T item : taskList) {
                consumer.accept(item);
            }
        }
    }


    public static void main(String[] args) {
        //生成1-10的10个数字，放在list中，相当于10个任务
        List<Integer> list = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());
        //启动多线程处理list中的数据，每个任务休眠时间为list中的数值
//        TaskDisposeUtils.dispose(list, item -> {
//            try {
//                long startTime = System.currentTimeMillis();
//                TimeUnit.SECONDS.sleep(item);
//                long endTime = System.currentTimeMillis();
//
//                System.out.println(System.currentTimeMillis() + ",任务" + item + "执行完毕，耗时：" + (endTime - startTime));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        });

        TaskDisposeUtils.dispose(list, new Consumer<Integer>() {

            @Override
            public void accept(Integer integer) {
                try {
                    long startTime = System.currentTimeMillis();
                    TimeUnit.SECONDS.sleep(integer);
                    long endTime = System.currentTimeMillis();

                    System.out.println(System.currentTimeMillis() + ",任务" + integer + "执行完毕，耗时：" + (endTime - startTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //上面所有任务处理完毕完毕之后，程序才能继续
        System.out.println(list + "中的任务处理完毕");
    }


}
