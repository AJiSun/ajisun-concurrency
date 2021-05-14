package org.ajisun.coding.concurrency.lock.queque;

import java.util.Calendar;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue是一个支持延时获取元素的无界阻塞队列，里面的元素全部都是“可延期”的元素，列头的元素是最先“到期”的元素，
 * 如果队列里面没有元素到期，是不能从列头获取元素的，哪怕有元素也不行，也就是说只有在延迟期到时才能够从队列中取元素。
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.queque
 * @Date: 2020/9/20
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo4 {

    static DelayQueue<Msg> delayQueue = new DelayQueue<>();

    static class Msg implements Delayed {

        //优先级，越小优先级越高
        private int priority;
        private String msg;
        //定时发送时间，毫秒格式
        private long sendTimeMs;

        public Msg(int priority, String msg, long sendTimeMs) {
            this.priority = priority;
            this.msg = msg;
            this.sendTimeMs = sendTimeMs;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(sendTimeMs - Calendar.getInstance().getTimeInMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o instanceof Msg) {
                Msg c2 = (Msg) o;
                return Integer.compare(this.priority, c2.priority);
            }
            return 0;
        }

        @Override
        public String toString() {
            return "Msg{" +
                    "priority=" + priority +
                    ", msg='" + msg + '\'' +
                    ", sendTimeMs=" + sendTimeMs +
                    '}';
        }
    }

    static {
        new Thread(() -> {
            while (true) {
                Msg msg;
                try {
                    //获取一条推送消息，此方法会进行阻塞，直到返回结果
                    msg = delayQueue.take();
                    //此处可以做真实推送
                    long endTime = System.currentTimeMillis();
                    System.out.println(String.format("定时发送时间：%s,实际发送时间：%s,发送消息:%s", msg.sendTimeMs, endTime, msg));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //推送消息，需要发送推送消息的调用该方法，会将推送信息先加入推送队列
    public static void pushMsg(int priority, String msg, long time) {
        delayQueue.put(new Msg(priority, msg, time));
    }

    /**
     * 可以看出时间发送时间，和定时发送时间基本一致，代码中Msg需要实现Delayed接口，重点在于getDelay方法，
     * 这个方法返回剩余的延迟时间，代码中使用this.sendTimeMs减去当前时间的毫秒格式时间，得到剩余延迟时间。
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 5; i >= 1; i--) {
            String msg = "学习java高并发,第" + i + "天";
            Demo4.pushMsg(i, msg, Calendar.getInstance().getTimeInMillis() + i * 2000);
        }
    }


}
