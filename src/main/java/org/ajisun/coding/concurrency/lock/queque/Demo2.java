package org.ajisun.coding.concurrency.lock.queque;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.queque
 * @Date: 2020/9/19
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo2 {

    static class Msg implements Comparable<Msg>{

        //优先级，越小优先级越高
        private int priority;
        //推送的信息
        private String msg;

        public Msg(int priority,String mgs){
            this.msg = mgs;
            this.priority = priority;
        }

        @Override
        public int compareTo(Msg o) {
            return Integer.compare(this.priority,o.priority);
        }

        @Override
        public String toString() {
            return "Msg{" +
                    "priority=" + priority +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

    static PriorityBlockingQueue<Msg> priority = new PriorityBlockingQueue<Msg>();

    static {
        new Thread(()->{
            while (true){
               Msg msg;
               try {
                   long starTime = System.currentTimeMillis();
                   msg = priority.take();
                   TimeUnit.MILLISECONDS.sleep(100);
                   long endTime = System.currentTimeMillis();
                   System.out.println(String.format("[%s,%s,take耗时:%s],%s,发送消息:%s", starTime, endTime, (endTime - starTime),
                           Thread.currentThread().getName(), msg));
               }catch (Exception e){
                   e.printStackTrace();
               }
            }

        }).start();
    }

    public static void pushMsg(int pri,String msg){
        priority.put(new Msg(pri,msg));
    }

    public static void main(String[] args) {
        for (int i = 5; i >= 1; i--) {
            String msg = "学java高并发,第" + i + "天";
            Demo2.pushMsg(i, msg);
        }
    }

}
