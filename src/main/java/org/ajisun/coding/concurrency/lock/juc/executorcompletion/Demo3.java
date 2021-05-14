package org.ajisun.coding.concurrency.lock.juc.executorcompletion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.executorcompletion
 * @Date: 2020/8/30
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startime = System.currentTimeMillis();
        ExecutorService executorService = new ThreadPoolExecutor(1,
                5,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1));

        List<Callable<Integer>> list = new ArrayList<>();
        int taskCount = 5;
        for (int i = 5; i > 0; i--) {
            int j = i * 2;
            String taskName = "任务" + i;
            list.add(() -> {
                TimeUnit.SECONDS.sleep(j);
                System.out.println(taskName + "执行完毕!");
                return j;
            });
        }

        Integer integer = invokeAny(executorService, list);
        System.out.println("耗时:" + (System.currentTimeMillis() - startime) + ",执行结果:" + integer);
        executorService.shutdown();
    }

    public static <T> T invokeAny(Executor e, Collection<Callable<T>> solvers) throws InterruptedException, ExecutionException {
        CompletionService<T> ecs = new ExecutorCompletionService<T>(e);
        List<Future<T>> futureList = new ArrayList<>();
        for (Callable<T> s : solvers) {
            futureList.add(ecs.submit(s));
        }
        int n = solvers.size();
        try {
            for (int i = 0; i < n; ++i) {
                T r = ecs.take().get();
                if (r != null) {
                    return r;
                }
            }
        } finally {
//            在finally中对所有任务发送取消操作（future.cancel(true);）
            for (Future<T> future : futureList) {
                future.cancel(true);
            }
        }
        return null;
    }

}
