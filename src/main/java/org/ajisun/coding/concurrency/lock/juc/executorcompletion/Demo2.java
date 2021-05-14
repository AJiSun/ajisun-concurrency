package org.ajisun.coding.concurrency.lock.juc.executorcompletion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.executorcompletion
 * @Date: 2020/8/30
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5,
                5,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4));

        List<Callable<Integer>> list = new ArrayList<>();
        for (int i = 5; i > 0; i--) {
            int j = i * 2;
            list.add(() -> {
                TimeUnit.SECONDS.sleep(j);
                return j;
            });
        }
        solve(executorService, list, a -> {
            System.out.println(System.currentTimeMillis() + ":" + a);
        });
        executorService.shutdown();

    }

    public static <T> void solve(Executor e, Collection<Callable<T>> solvers, Consumer<T> use) throws InterruptedException, ExecutionException {
        CompletionService<T> completionService = new ExecutorCompletionService<T>(e);
        for (Callable<T> s : solvers) {
            completionService.submit(s);
        }
        int n = solvers.size();
        for (int i = 0; i < n; ++i) {
            T r = completionService.take().get();
            if (r != null) {
                use.accept(r);
            }
        }
    }


}
