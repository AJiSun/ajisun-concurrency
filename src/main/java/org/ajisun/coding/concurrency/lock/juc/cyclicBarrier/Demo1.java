package org.ajisun.coding.concurrency.lock.juc.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.juc.cyclicBarrier
 * @Date: 2020/8/10
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo1 {
     public  static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    public static class T extends Thread{

        int sleep;

        public T(String name,int sleep){
            super(name);
            this.sleep = sleep;
        }

        @Override
        public void run(){
            try {
                TimeUnit.SECONDS.sleep(sleep);
                long startTime = System.currentTimeMillis();
                cyclicBarrier.await();
                long endTime = System.currentTimeMillis();
                System.out.println(this.getName() + ",sleep:" + this.sleep + " 等待了" + (endTime - startTime) + "(ms),开始吃饭了！");
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            new T("员工："+i,i).start();
        }


    }

}
