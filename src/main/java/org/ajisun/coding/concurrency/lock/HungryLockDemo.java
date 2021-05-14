package org.ajisun.coding.concurrency.lock;

import java.util.concurrent.*;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/1
 * @author: ajisun
 *
 */
public class HungryLockDemo {


    private static ExecutorService single = Executors.newSingleThreadExecutor();

    public static class AnotherCallable implements Callable<String>{

        public String call() throws Exception {
            System.out.println("in AnotherCallable");
            return "annother success";
        }

    }

    public static class MyCallable implements Callable<String>{


        public String call() throws Exception {

            System.out.println("in MyCallable");

            Future<String> submit = single.submit(new AnotherCallable());
            return "success: ";

        }
    }

    public static class TestCallable implements Callable<String>{
        String threadName;

        public TestCallable(String threadName){
            this.threadName = threadName;

        }

        public String call() throws Exception {
            System.out.println(threadName);
            return threadName;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        MyCallable myCallable = new MyCallable();
//        Future<String> submit = single.submit(myCallable);
//        System.out.println(submit.get());
//        System.out.println("over");
//        single.shutdown();


        TestCallable testCallable = new TestCallable("测试test");
        FutureTask<String> futureTask = new FutureTask<String>(testCallable);
        Thread thread = new Thread(futureTask);
        thread.start();
        String rValue = futureTask.get();
        System.out.println("Thread1 return value is " + rValue);



    }





}
