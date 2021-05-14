package org.ajisun.coding.concurrency.lock.queque;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.queque
 * @Date: 2020/9/19
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo1 {

    static ArrayBlockingQueue<String> pushQueue = new ArrayBlockingQueue<>(2);

    static {
        new Thread(()->{
            while (true){
                String msg;
                try {
                    long starTime = System.currentTimeMillis();
                    //获取一条推送消息，此方法会进行阻塞，直到返回结果
                    msg = pushQueue.take();
                    long endTime = System.currentTimeMillis();
                    //模拟推送耗时
                    TimeUnit.MILLISECONDS.sleep(500);

                    System.out.println(String.format("[%s,%s,take耗时:%s],%s,发送消息:%s", starTime,
                            endTime, (endTime - starTime), Thread.currentThread().getName(), msg));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //推送消息，需要发送推送消息的调用该方法，会将推送信息先加入推送队列
    public static void pushMsg(String msg) throws InterruptedException {
        pushQueue.put(msg);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            String msg = "学java高并发,第" + i + "天";
            //模拟耗时
            TimeUnit.SECONDS.sleep(i);
            Demo1.pushMsg(msg);
        }
    }

}
